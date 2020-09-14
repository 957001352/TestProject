package com.dhlk.light.service.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnect;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/6/1
 */
@Component
public class MqttSendServer {
    private Logger log = LoggerFactory.getLogger(MqttSendServer.class);
    @Value("${mqtt.host}")
    public  String HOST;
    @Value("${mqtt.username}")
    public  String username;
    @Value("${mqtt.password}")
    public   String password;

    private static final String clientid = UUID.randomUUID().toString();

    public MqttClient client;

    public MqttTopic mqttTopic;

    public MqttMessage message;

    private  MqttConnectOptions options;


    public synchronized boolean connect() throws MqttException {
        try {
            //防止重复创建MQTTClient实例
            if (client==null) {
                //就是这里的clientId，服务器用来区分用户的，不能重复
                client = new MqttClient(HOST, clientid, new MemoryPersistence());// MemoryPersistence设置clientid的保存形式，默认为以内存保存
                options = new MqttConnectOptions();
                options.setCleanSession(false);
                options.setConnectionTimeout(10);
                //设置心跳
                options.setKeepAliveInterval(20);
                options.setUserName(username);
                options.setPassword(password.toCharArray());
                options.setMaxInflight(1000);
            }
            //判断拦截状态，这里注意一下，如果没有这个判断，是非常坑的
            if (!client.isConnected()) {
                client.connect(options);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("mqtt 连接异常");
        }

        return client.isConnected();
    }


    public synchronized boolean publish(MqttTopic topic , MqttMessage message) throws MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
        return token.isComplete();
    }

    /**
     * MQTT发送灯指令
     * @throws MqttException
     */
    public  void sendMQTTMessage(String topic,String data) {
        try {
            if(this.connect()){
                mqttTopic = client.getTopic(topic);
                message = new MqttMessage();
                //消息等级
                //Qos 0 : 这个消息最多发送一次，不能被持久化到磁盘，不能通过网络被传递，一般内部消息转换
                //Qos 1 : 这个消息至少发送一次，能被重传，能持久化，能通过网络传递，需要实现MqttConnectOptions中的持久化，否则，挂了以后，不能重传。
                //Qos 2：这个消息精准只发一次，能持久化，能通过网络传递，客户端和服务器都会收到消息确认
                message.setQos(0);
                //如果重复消费，则把值改为true,然后发送一条空的消息，之前的消息就会覆盖，然后在改为false
                message.setRetained(false);
                message.setPayload(data.getBytes());
                publish(mqttTopic, message);
            }else{
                log.info("mqtt 连接断开，发送失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * MQTT发送指令
     * @throws MqttException
     */
    public  void sendMQTTMessageToLocal(String topic,String data) throws MqttException {
            this.connect();
            mqttTopic = client.getTopic(topic);
            message = new MqttMessage();
            //消息等级
            //level 0：最多一次的传输
            //level 1：至少一次的传输，(鸡肋)
            //level 2： 只有一次的传输
            message.setQos(0);
            //如果重复消费，则把值改为true,然后发送一条空的消息，之前的消息就会覆盖，然后在改为false
            message.setRetained(false);

            message.setPayload(data.getBytes());
            boolean isSend=publish(mqttTopic, message);
    }
}