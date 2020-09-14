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
     * 测试和正式环境不要使用同样的clientId 和主题
     * 如果和正式环境一样，正式环境启动后，本地再次启动会频繁断开重连，订阅的主题一样的话，测试的数据正式环境也会消费这些数据
     */
    private static final String clientid = UUID.randomUUID().toString();//测试
    /**
     * 主题
     */
    private String[] topic = {"cloudToLocal","LOCAL_TOPIC_SYNC_DATA","LOCAL_TOPIC_SYS_VERSION","LOCAL_TOPIC_TASK_DATASYNC", "LOCAL_TOPIC_TASK_DELETE",
            "LOCAL_TOPIC_LED_SAVE", "LOCAL_TOPIC_LED_UPDATE", "LOCAL_TOPIC_LED_DELETE", "LOCAL_TOPIC_AREA_SAVE",
            "LOCAL_TOPIC_AREA_UPDATE", "LOCAL_TOPIC_AREA_DELETE", "LOCAL_TOPIC_SWITCH_SAVE", "LOCAL_TOPIC_LEDSWITCH_SAVE",
            "LOCAL_TOPIC_LEDSWITCH_DELETE", "LOCAL_TOPIC_SWITCH_DELETE", "LOCAL_TOPIC_ORIPOWER_SAVE","LOCAL_TOPIC_ORIPOWER_UPDATE"};

    private int[] Qos = {2,2,2,2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,2};
    public MqttClient client;

    private MqttConnect mqttConnect = new MqttConnect();

    private MqttConnectOptions options;

    @Autowired
    private RedisService redisService;
    //方法实现说明 断线重连方法，如果是持久订阅，重连是不需要再次订阅，如果是非持久订阅，重连是需要重新订阅主题 取决于options.setCleanSession(true);
    // true为非持久订阅
    public  MqttClient connect()  {
        MqttClient client=null;
        //防止重复创建MQTTClient实例
        if (client == null) {
            try {
                client = new MqttClient(HOST, clientid, new MemoryPersistence());// MemoryPersistence设置clientid的保存形式，默认为以内存保存
            } catch (MqttException e) {
                e.printStackTrace();
                return null;
            }
        }
        return client;
    }
    public Boolean startSubsribe(MqttClient client){
        if(client!=null){
            //如果是订阅者则添加回调类，发布不需要
            client.setCallback(new MqttCallback() {
                public void connectionLost(Throwable cause){
                    log.info("云端订阅连接断开....."+System.currentTimeMillis());
                    reConcatMqtt(client);
                }
                public void deliveryComplete(IMqttDeliveryToken token) {
                    log.info("云端连接完成" + token.isComplete());
                }
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println(topic+"==>"+message.toString()+System.currentTimeMillis());
                    mqttCloudService.subsribe(topic, message.toString());
                }
            });
        }
        options = mqttConnect.getOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        try {
            client.connect(options);
            if(client.isConnected()){
                client.subscribe(topic, Qos);
                redisService.set(LedConst.REDIS_MQTTISRIGHT,0);
            }
        } catch (MqttException e) {
            e.printStackTrace();
            redisService.set(LedConst.REDIS_MQTTISRIGHT,-1);
            return false;
        }
        return true;
    }

    public void reConcatMqtt(MqttClient client){
        while (true){
            try {
                if(startSubsribe(client)){
                    redisService.set(LedConst.REDIS_MQTTISRIGHT,0);
                    log.info("云端订阅重连成功....."+System.currentTimeMillis());
                    break;
                }
                Thread.sleep(5000);
                System.out.println("云端订阅连接尝试重连...."+System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Bean
    public void startCloud() {
        MqttClient client=connect();
        startSubsribe(client);
    }
}