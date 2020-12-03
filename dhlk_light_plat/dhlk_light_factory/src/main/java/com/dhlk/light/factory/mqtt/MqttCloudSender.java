package com.dhlk.light.factory.mqtt;

import com.dhlk.light.factory.util.LedConst;
import com.dhlk.service.RedisService;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
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

    public MqttClient client;

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
    @Bean
    public void startSenderCloud() {
        redisService.set(LedConst.REDIS_MQTTISRIGHT, 0);
    }

    public synchronized boolean connectCloud(){
        try {
            //防止重复创建MQTTClient实例
            if (client == null) {
                //就是这里的clientId，服务器用来区分用户的，不能重复
                client = new MqttClient(HOST, UUID.randomUUID().toString(), new MemoryPersistence());// MemoryPersistence设置clientid的保存形式，默认为以内存保存
                options = mqttConnect.getOptions();
                options.setUserName(username);
                options.setPassword(password.toCharArray());
                options.setMaxInflight(1000);
            }
            if (!client.isConnected()) {
                client.connect(options);
                redisService.set(LedConst.REDIS_MQTTISRIGHT,0);
                log.info(System.currentTimeMillis() + "云端发送连接完成...");
            }
        } catch (Exception e) {
            log.info(System.currentTimeMillis() + "云端发送连接断开...");
            redisService.set(LedConst.REDIS_MQTTISRIGHT,-1);
        }
        return client.isConnected();
    }

    public synchronized void sendMQTTMessage(String topic,String msg){
        //log.info("云端发送状态->"+redisService.get(LedConst.REDIS_MQTTISRIGHT));
        //如果云端mqtt判断状态为空，设置0
        System.out.println("client是否为空=>"+client);
            try {
               boolean temp= connectCloud();
                System.out.println("mqtt连接状态..."+temp);
                if (temp) {
                    MqttMessage message = new MqttMessage(msg.getBytes());
                    message.setQos(0);
                    message.setRetained(false);
                    message.setPayload(msg.getBytes());
                    client.publish(topic, message);
                } else {
                    redisService.set(LedConst.REDIS_MQTTISRIGHT,-1);
                    client.close();
                    client = null;
                    //开启定时重连机制
                    reConcatMqtt();
                    System.out.println("云端发送mqtt断开....");
                }
            } catch (MqttException e) {
                e.printStackTrace();
            }
    }
    public synchronized void reConcatMqtt(){
//        while (true){
//              try {
//                   boolean isTemp= connectCloud();
//                   if(isTemp){
//                       log.info("云端mqtt发送重连成功===>"+System.currentTimeMillis());
//                       redisService.set(LedConst.REDIS_MQTTISRIGHT,0);
//                       break;
//                   }
//                   Thread.sleep(60*1000);
//                } catch (Exception e) {
//                    log.info(System.currentTimeMillis() + "云端mqtt发送重连报错...");
//                }
//        }
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        Future future = executorService.scheduleAtFixedRate(new  Runnable() {
            @Override
            public void run() {
                try {
                   boolean isTemp= connectCloud();
                   log.info(System.currentTimeMillis() + "云端sender连接断开，正在尝试重新连接...");
                   if(isTemp){
                       log.info("云端sender重连成功....."+System.currentTimeMillis());
                       redisService.set(LedConst.REDIS_MQTTISRIGHT,0);
                       executorService.shutdown();
                   }
                } catch (Exception e) {
                    log.info(System.currentTimeMillis() + "云端mqtt发送重连报错...");
                    e.printStackTrace();
                }
            }
        }, 0, 1, TimeUnit.MINUTES);
    }
}