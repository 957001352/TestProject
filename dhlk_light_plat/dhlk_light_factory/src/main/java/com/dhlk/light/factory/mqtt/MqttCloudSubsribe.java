package com.dhlk.light.factory.mqtt;

import com.dhlk.light.factory.service.MqttCloudService;
import com.dhlk.light.factory.util.LedConst;
import com.dhlk.service.RedisService;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/6/1
 */
@Configuration
public class MqttCloudSubsribe {
    private Logger log = LoggerFactory.getLogger(MqttCloudSubsribe.class);

    @Value("${mqttcloud.host}")
    public String HOST = "tcp://192.168.2.161:1883";
    @Value("${mqttcloud.username}")
    public String username;
    @Value("${mqttcloud.password}")
    public String password;
    @Autowired
    private MqttCloudService mqttCloudService;
    /**
     * 主题
     */
    private String[] topic = {"cloudToLocal","LOCAL_TOPIC_SYNC_DATA","LOCAL_TOPIC_SYS_VERSION","LOCAL_TOPIC_TASK_DATASYNC", "LOCAL_TOPIC_TASK_DELETE",
            "LOCAL_TOPIC_LED_SAVE", "LOCAL_TOPIC_LED_UPDATE", "LOCAL_TOPIC_LED_DELETE", "LOCAL_TOPIC_AREA_SAVE",
            "LOCAL_TOPIC_AREA_UPDATE", "LOCAL_TOPIC_AREA_DELETE", "LOCAL_TOPIC_SWITCH_SAVE", "LOCAL_TOPIC_LEDSWITCH_SAVE",
            "LOCAL_TOPIC_LEDSWITCH_DELETE", "LOCAL_TOPIC_SWITCH_DELETE", "LOCAL_TOPIC_ORIPOWER_SAVE","LOCAL_TOPIC_ORIPOWER_UPDATE","LOCAL_TOPIC_LED_LOCATION"};

    private int[] Qos = {2,2,2,2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,2,2};

    private MqttConnect mqttConnect = new MqttConnect();

    private MqttConnectOptions options;


    private MqttClient mqttClient=null;
    //方法实现说明 断线重连方法，如果是持久订阅，重连是不需要再次订阅，如果是非持久订阅，重连是需要重新订阅主题 取决于options.setCleanSession(true);
    // true为非持久订阅
    public  MqttClient newConnect()  {
        //防止重复创建MQTTClient实例
        if (mqttClient == null) {
            try {
                mqttClient = new MqttClient(HOST, UUID.randomUUID().toString(), new MemoryPersistence());// MemoryPersistence设置clientid的保存形式，默认为以内存保存
                log.info("云端重新创建新的mqttClient....."+System.currentTimeMillis());
            } catch (MqttException e) {
                e.printStackTrace();
                return null;
            }
        }
        return mqttClient;
    }
    public  Boolean startSubsribe(){
        if(mqttClient!=null){
            //如果是订阅者则添加回调类，发布不需要
            mqttClient.setCallback(new MqttCallback() {
                public void connectionLost(Throwable cause){
                    cause.printStackTrace();
                    log.info("云端订阅连接断开....."+System.currentTimeMillis());
                    try {
                        mqttClient.close();
                        mqttClient=null;
                        newConnect();
                        reConcatMqtt();
                    } catch (MqttException e) {
                        log.info("云端订阅连接异常....." + System.currentTimeMillis());
                        e.printStackTrace();
                    }
                }
                public void deliveryComplete(IMqttDeliveryToken token) {
                    log.info("云端连接完成" + token.isComplete());
                }
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    mqttCloudService.subsribe(topic, message.toString());
                }
            });
        }
        options = mqttConnect.getOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        try {
            mqttClient.connect(options);
            if(mqttClient.isConnected()){
                mqttClient.subscribe(topic, Qos);
                log.info("云端订阅重连成功，开始重新订阅....."+System.currentTimeMillis());
            }
        } catch (MqttException e) {
            log.info(System.currentTimeMillis() + "云端mqtt订阅连接报错...");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public  void reConcatMqtt(){
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        Future future = executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info(System.currentTimeMillis() + "云端subsribe连接断开，正在尝试重新连接...");
                    if(startSubsribe()){
                        log.info("云端subsribe重连成功....."+System.currentTimeMillis());
                        executorService.shutdown();
                    }
                } catch (Exception e) {
                    log.info(System.currentTimeMillis() + "云端mqtt订阅重连报错...");
                }
            }
        }, 0, 1, TimeUnit.MINUTES);
    }
    @Bean
    public void startSubsribeCloud() {
        newConnect();
        if(!startSubsribe()){
            reConcatMqtt();
        }
    }
}