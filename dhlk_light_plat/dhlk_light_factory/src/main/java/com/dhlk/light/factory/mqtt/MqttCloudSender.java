package com.dhlk.light.factory.mqtt;

import com.dhlk.light.factory.util.LedConst;
import com.dhlk.service.RedisService;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
@Component
public class MqttCloudSender {
    private Logger log = LoggerFactory.getLogger(MqttCloudSender.class);
    @Value("${mqttcloud.host}")
    public  String HOST="tcp://192.168.2.162:1883";
    @Value("${mqttcloud.username}")
    public  String username;
    @Value("${mqttcloud.password}")
    public   String password;
    private  String clientid=  UUID.randomUUID().toString();

    public MqttClient client;

    public MqttTopic mqttTopic;

    public MqttMessage message;

    private   MqttConnect mqttConnect = new MqttConnect();

    private   MqttConnectOptions options;

    @Autowired
    private RedisService redisService;
//    @Scheduled(cron = "0/15 * * * * ?")
//    public synchronized void mqttReConcat(){
//        if (redisService.get(LedConst.REDIS_MQTTISRIGHT).toString().equals("-1")) {
//            connectCloud();
//        }
//    }


    public synchronized boolean connectCloud(){
        try {
            //防止重复创建MQTTClient实例
            if (client == null) {
                //就是这里的clientId，服务器用来区分用户的，不能重复
                client = new MqttClient(HOST, clientid, new MemoryPersistence());// MemoryPersistence设置clientid的保存形式，默认为以内存保存
                options = mqttConnect.getOptions();
                options.setUserName(username);
                options.setPassword(password.toCharArray());
                options.setMaxInflight(1000);
            }
            //判断拦截状态，这里注意一下，如果没有这个判断，是非常坑的
            if (!client.isConnected()) {
                client.connect(options);
                redisService.set(LedConst.REDIS_MQTTISRIGHT,0);
                log.info(System.currentTimeMillis() + "云端发送连接完成...");
            }
        } catch (Exception e) {
            log.info(System.currentTimeMillis() + "云端发送连接断开..."+(System.currentTimeMillis()/1000));
            redisService.set(LedConst.REDIS_MQTTISRIGHT,-1);
            e.printStackTrace();
        }
        return client.isConnected();
    }

    public void sendMQTTMessage(String topic,String msg){
        if(redisService.get(LedConst.REDIS_MQTTISRIGHT).toString().equals("0")) {
            if (this.connectCloud()) {
                try {
                    MqttMessage message = new MqttMessage(msg.getBytes());
                    message.setQos(0);
                    client.publish(topic, message);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public synchronized boolean publish(MqttTopic topic, MqttMessage message) throws MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
        return token.isComplete();
    }

    /**
     * MQTT发送指令
     * @throws MqttException
     */

//    public void sendMQTTMessage(String topic, String data) {
//        try {
//            if(redisService.get(LedConst.REDIS_MQTTISRIGHT).toString().equals("0")){
//                if (this.connectCloud()) {
//                    mqttTopic = client.getTopic(topic);
//                    message = new MqttMessage();
//                    message.setQos(0);
//                    //如果重复消费，则把值改为true,然后发送一条空的消息，之前的消息就会覆盖，然后在改为false
//                    message.setRetained(false);
//                    message.setPayload(data.getBytes());
//                    publish(mqttTopic, message);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}