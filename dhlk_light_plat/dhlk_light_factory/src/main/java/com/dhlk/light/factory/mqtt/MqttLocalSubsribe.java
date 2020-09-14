package com.dhlk.light.factory.mqtt;

import com.dhlk.light.factory.service.MqttLocalService;
import com.dhlk.light.factory.util.LedConst;
import lombok.SneakyThrows;
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
public class MqttLocalSubsribe {
    private Logger log = LoggerFactory.getLogger(MqttLocalSubsribe.class);
    @Value("${mqttlocal.host}")
    public String HOST = "tcp://192.168.2.157:1883";
    @Value("${mqttlocal.username}")
    public String username;
    @Value("${mqttlocal.password}")
    public String password;
    @Autowired
    private MqttLocalService mqttLocalService;

    private String[] topic = {"dhlk_light/#"};

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
                        MqttClient client = connect();
                        reConcatMqtt(client);
                    } catch (MqttException e) {
                        log.info("本地订阅连接异常....." + System.currentTimeMillis());
                    }
                }
                public void deliveryComplete(IMqttDeliveryToken token) {
                    log.info("本地连接完成" + token.isComplete());
                }
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    mqttLocalService.subsribe(topic, message.toString());
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
        }
        return true;
     }

     public void reConcatMqtt(MqttClient client){
             while (true){
                 try {
                     if(startSubsribe(client)){
                         log.info("本地订阅新开连接重连成功....."+System.currentTimeMillis());
                         break;
                     }
                     Thread.sleep(5000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
     }
     @Bean
    public void startLocal() {
         MqttClient client=connect();
         if(!startSubsribe(client)){
             reConcatMqtt(client);
         }
     }
}