package com.dhlk.light.factory.mqtt;

import com.alibaba.fastjson.JSONObject;
import com.dhlk.entity.light.LedRecord;
import com.dhlk.light.factory.dao.LedRecordDao;
import com.dhlk.light.factory.util.LedResult;
import com.dhlk.util.AuthUserUtil;
import com.dhlk.utils.DateUtils;
import com.dhlk.utils.IPUtil;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/6/1
 */
@Component
public class MqttLocalSender {
    private Logger log = LoggerFactory.getLogger(MqttLocalSender.class);
    @Value("${mqttlocal.host}")
    public  String HOST="tcp://192.168.2.161:1883";
    @Value("${mqttlocal.username}")
    public   String username;
    @Value("${mqttlocal.password}")
    public   String password;

    private static final String clientid = UUID.randomUUID().toString();

    public MqttClient client;

    public MqttTopic mqttTopic;

    public MqttMessage message;

    private  MqttConnect mqttConnect = new MqttConnect();

    private MqttConnectOptions options;

    public synchronized boolean connectLocal() throws MqttException {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("本地连接异常....");
        }
        return client.isConnected();
    }

    public void sendMQTTMessage(String topic,String msg) {
        try {
            if (this.connectLocal()) {
                MqttMessage message = new MqttMessage(msg.getBytes());
                message.setQos(0);
                client.publish(topic, message);
            }
        } catch (MqttException e) {
            e.printStackTrace();
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
//            if(this.connectLocal()){
//                mqttTopic = client.getTopic(topic);
//                message = new MqttMessage();
//                //消息等级
//                //level 0：最多一次的传输
//                //level 1：至少一次的传输，(鸡肋)
//                //level 2： 只有一次的传输
//                message.setQos(0);
//                //如果重复消费，则把值改为true,然后发送一条空的消息，之前的消息就会覆盖，然后在改为false
//                message.setRetained(false);
//                message.setPayload(data.getBytes());
//                publish(mqttTopic, message);
//            }else{
//                log.info("本地连接断开，发送失败！");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}