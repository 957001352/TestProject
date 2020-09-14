package com.dhlk.flume.interceptor.interceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDrivenSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.source.AbstractSource;
import org.eclipse.paho.client.mqttv3.*;

import java.util.HashMap;
import java.util.Map;


public class MQTTSource extends AbstractSource implements EventDrivenSource,
        Configurable {


    //topic
    private String topic;


    /**
     * 加载flume的配置文件信息
     */
    @Override
    public void configure(Context context) {
        topic = context.getString("topic");
    }

    SimpleMqttClient client = null;


    @Override
    public void start() {
        // TODO Auto-generated method stub
        // super.start();
        client = new SimpleMqttClient();
        client.runClient();
    }


    @Override
    public void stop() {
        // TODO Auto-generated method stub
        // super.stop();
        if (client != null) {
            client.closeConn();
        }
    }


    public class SimpleMqttClient implements MqttCallback {

        MqttClient myClient;
        MqttConnectOptions connOpt;

        /**
         * 主要修改这个地址    BROKER_URL
         */
        String BROKER_URL = "tcp://192.168.2.161:1883";
        String M2MIO_THING = "flume";
        // String M2MIO_USERNAME = "<m2m.io username>";
        // String M2MIO_PASSWORD_MD5 =
        // "<m2m.io password (MD5 sum of password)>";

        Boolean subscriber = true;
        Boolean publisher = false;

        /**
         * 连接丢失
         */
        @Override
        public void connectionLost(Throwable t) {
            System.out.println("Connection lost!");
            // code to reconnect to the broker would go here if desired
        }

        public void closeConn() {
            if (myClient != null) {
                if (myClient.isConnected()) {
                    try {
                        myClient.disconnect();
                    } catch (MqttException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }

        /**
         * 订阅主题
         */
        @Override
        public void messageArrived(String topic, MqttMessage message)
                throws Exception {

            Map<String, String> headers = new HashMap<String, String>();

            Event flumeEvent = EventBuilder.withBody(message.getPayload(),
                    headers);
            try {
                getChannelProcessor().processEvent(flumeEvent);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }

        /**
         * 创建MQTT客户端,发送订阅消息
         */
        public void runClient() {
            // setup MQTT Client
            String clientID = M2MIO_THING;
            connOpt = new MqttConnectOptions();

            connOpt.setCleanSession(true);
            connOpt.setKeepAliveInterval(3000);
            // connOpt.setUserName(M2MIO_USERNAME);
            // connOpt.setPassword(M2MIO_PASSWORD_MD5.toCharArray());

            // Connect to Broker
            try {
                myClient = new MqttClient(BROKER_URL, clientID);
                myClient.setCallback(this);
                myClient.connect(connOpt);
            } catch (MqttException e) {
                e.printStackTrace();
                System.exit(-1);
            }

            System.out.println("Connected to " + BROKER_URL);


            System.out.println("myTopic:" + topic);
            MqttTopic topics = myClient.getTopic(topic);

            // subscribe to topic if subscriber
            if (subscriber) {
                try {
                    int subQoS = 0;
                    myClient.subscribe(String.valueOf(topics), subQoS);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // publish messages if publisher
            if (publisher) {
                for (int i = 1; i <= 10; i++) {
                    String pubMsg = "{\"pubmsg\":" + i + "}";
                    int pubQoS = 0;
                    MqttMessage message = new MqttMessage(pubMsg.getBytes());
                    message.setQos(pubQoS);
                    message.setRetained(false);

                    // Publish the message
                    System.out.println("Publishing to topic \"" + topics
                            + "\" qos " + pubQoS);
                    MqttDeliveryToken token = null;
                    try {
                        // publish message to broker
                        token = topics.publish(message);
                        // Wait until the message has been delivered to the
                        // broker
                        token.waitForCompletion();
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // disconnect
            try {
                // wait to ensure subscribed messages are delivered
                if (subscriber) {
                    while (true) {
                        Thread.sleep(5000);
                    }
                }
                // myClient.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }

    }

}