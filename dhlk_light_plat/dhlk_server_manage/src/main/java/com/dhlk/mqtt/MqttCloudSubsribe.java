package com.dhlk.mqtt;

import com.dhlk.service.MqttCloudService;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Value("${mqtt.host}")
    public  String HOST="tcp://192.168.2.161:1883";
    @Value("${mqtt.username}")
    public  String username;
    @Value("${mqtt.password}")
    public   String password;
    @Autowired
    private MqttCloudService mqttCloudService;

    private String[] topic = {"cloudToLocalProxyData"};

    private int[] Qos = {2};


    private MqttConnect mqttConnect = new MqttConnect();

    private MqttConnectOptions options;

    //方法实现说明 断线重连方法，如果是持久订阅，重连是不需要再次订阅，如果是非持久订阅，重连是需要重新订阅主题 取决于options.setCleanSession(true);
    // true为非持久订阅
    public  MqttClient connect()  {
        MqttClient client=null;
        //防止重复创建MQTTClient实例
        if (client == null) {
            try {
                client = new MqttClient(HOST,  UUID.randomUUID().toString(), new MemoryPersistence());// MemoryPersistence设置clientid的保存形式，默认为以内存保存
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
                public void connectionLost(Throwable cause) {
                    cause.printStackTrace();
                    log.info("本地订阅连接断开....." + System.currentTimeMillis());
                    try {
                        client.close();
                        MqttClient client1 = connect();
                        reConcatMqtt(client1);
                    } catch (MqttException e) {
                        log.info("本地订阅连接异常....." + System.currentTimeMillis());
                    }
                }
                public void deliveryComplete(IMqttDeliveryToken token) {
                    log.info("本地连接完成" + token.isComplete());
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
            client.connect(options);
            if(client.isConnected()){
                client.subscribe(topic, Qos);
            }
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void reConcatMqtt(MqttClient client){
//             while (true){
//                 try {
//                     if(startSubsribe(client)){
//                         log.info("本地订阅新开连接重连成功....."+System.currentTimeMillis());
//                         break;
//                     }
//                     Thread.sleep(5000);
//                 } catch (InterruptedException e) {
//                     e.printStackTrace();
//                 }
//             }
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        Future future = executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info(System.currentTimeMillis() + "本地订阅连接断开，正在尝试重新连接...");
                    if(startSubsribe(client)){
                        log.info("本地订阅新开连接重连成功....."+System.currentTimeMillis());
                        executorService.shutdown();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1, TimeUnit.MINUTES);
    }
    @Bean
    public void startLocal() {
        MqttClient client=connect();
        if(!startSubsribe(client)){
            reConcatMqtt(client);
        }
    }
}