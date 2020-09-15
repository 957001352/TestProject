package com.dhlk.light.subscribe.mqtt;

import com.dhlk.light.subscribe.service.MqttSubsribeService;
import com.dhlk.light.subscribe.service.MqttToLightServiceSubsribe;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.UUID;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/6/1
 */
@Configuration
public class MqttSubsribe {
    private Logger log = LoggerFactory.getLogger(MqttSubsribe.class);
    @Value("${mqtt.host}")
    public String HOST;
    @Value("${mqtt.username}")
    public String username;
    @Value("${mqtt.password}")
    public String password;
    @Autowired
    private MqttToLightServiceSubsribe mqttToLightServiceSubsribe;

    @Autowired
    private MqttSubsribeService mqttSubsribeService;

    /**
     * 主题
     */
    private String[] topic = {"localToCloud", "CLOUD_TOPIC_LED_VERSION", "CLOUD_TOPIC_ORIPOWER_UPDATE", "CLOUD_TOPIC_ORIPOWER_SAVE", "CLOUD_TOPIC_SYNC_DATA", "CLOUD_TOPIC_FAULT", "LOCAL_TOPIC_POWER_DATASYNC", "LOCAL_TOPIC_ONLINE_DATASYNC", "LOCAL_TOPIC_PEOPLE_FEEL_DATASYNC", "CLOUD_TOPIC_ORIPOWER_SAVE", "CLOUD_TOPIC_ORIPOWER_UPDATE"};

    public MqttClient client;

    private MqttConnectOptions options;

    private int[] Qos = {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2};//


    //方法实现说明 断线重连方法，如果是持久订阅，重连是不需要再次订阅，如果是非持久订阅，重连是需要重新订阅主题 取决于options.setCleanSession(true);
    // true为非持久订阅
    public MqttClient connect() {
        MqttClient client = null;
        //防止重复创建MQTTClient实例
        if (client == null) {
            try {
                client = new MqttClient(HOST, UUID.randomUUID().toString(), new MemoryPersistence());// MemoryPersistence设置clientid的保存形式，默认为以内存保存
            } catch (MqttException e) {
                e.printStackTrace();
                return null;
            }
        }
        return client;
    }

    public Boolean startSubsribe(MqttClient client) {
        if (client != null) {
            //如果是订阅者则添加回调类，发布不需要
            client.setCallback(new MqttCallback() {
                public void connectionLost(Throwable cause) {
                    log.info("云端订阅连接断开....." + System.currentTimeMillis());
                    MqttClient client = connect();
                    reConcatMqtt(client);
                }
                public void deliveryComplete(IMqttDeliveryToken token) {
                    log.info("云端订阅连接完成" + token.isComplete());
                }
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    if (topic.equals("localToCloud")) {
                        //灯上报数据，自己处理，将数据存入redis
                        mqttSubsribeService.subsribe(topic, message.toString());
                    } else {
                        //本地服务同步数据表，转发智慧照明服务
                        mqttToLightServiceSubsribe.subsribe(topic, message.toString());
                    }
                }
            });
        }
        options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setConnectionTimeout(0);
        //设置心跳
        options.setKeepAliveInterval(10);
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        try {
            client.connect(options);
            if (client.isConnected()) {
                client.subscribe(topic, Qos);
            }
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void reConcatMqtt(MqttClient client) {
        while (true) {
            try {
                if (startSubsribe(client)) {
                    log.info("云端订阅重连成功....." + System.currentTimeMillis());
                    break;
                }
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Bean
    public void start() {
        MqttClient client = connect();
        startSubsribe(client);
    }

}