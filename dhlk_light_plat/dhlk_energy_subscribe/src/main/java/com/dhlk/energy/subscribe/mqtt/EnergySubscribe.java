package com.dhlk.energy.subscribe.mqtt;


import com.alibaba.fastjson.JSONObject;
import com.dhlk.energy.subscribe.entity.Result;
import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/4/7
 */
@Component
public class EnergySubscribe {
    private Logger log = LoggerFactory.getLogger(EnergySubscribe.class);
    //config
    @Value("${mqtt.host}")
    private  String HOST;//hdfs 访问地址
    @Value("${mqtt.topic}")
    private  String topic;//默认主题
    private MqttClient client;
    private MqttConnectOptions options;
    @Value("${mqtt.username}")
    private String userName;
    @Value("${mqtt.password}")
    private String passWord;
    @Value("${dhlk_energy.url}")
    private String energyUrl;
    private CloseableHttpClient httpClient;//httpClint客户端
    private String deviceCode=null;
    private Long createTime=null;
    @Bean
    public void start() {
        startSubsribe();
    }
    public void startSubsribe() {
        try {
            if(client==null){
                client = new MqttClient(HOST, UUID.randomUUID().toString(), new MemoryPersistence());
            }
            // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存

            // MQTT的连接设置
            options = new MqttConnectOptions();

            options.setAutomaticReconnect(false);
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(false);
            // 设置连接的用户名
            options.setUserName(userName);
            // 设置连接的密码
            options.setPassword(passWord.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);

            httpClient = HttpClients.createDefault();
            // 设置回调
            client.setCallback(new MqttCallback(){
                @SneakyThrows
                public void connectionLost(Throwable cause) {
                    cause.printStackTrace();
                    // 连接丢失后，一般在这里面进行重连
                    log.info("mqtt 订阅连接断开...."+System.currentTimeMillis());
                    while (true){
                        try {
                            if(!client.isConnected()){
                                client.reconnect();
                            }
                            if(client.isConnected()){
                                break;
                            }
                            Thread.sleep(5000);
                            System.out.println("mqtt 订阅尝试重连...."+System.currentTimeMillis());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("mqtt 订阅重连成功...."+System.currentTimeMillis());
                    client.subscribe(topic.split(","), new int[]{2});
                }
                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete---------" + token.isComplete());
                }
                public void messageArrived(String topic, MqttMessage message){
                    try {
                            JSONObject object=JSONObject.parseObject(message.toString());
                            JSONObject data=object.getJSONObject("after");
                            deviceCode=data.getString("deviceCode");
                            createTime=data.getLong("energy_ts");
                            data.remove("deviceCode");
                            data.remove("energy_ts");
                            doPost(httpClient,energyUrl,JSONObject.toJSONString(new Result(deviceCode,createTime,data)));
                            //doPost(httpClient,"http://192.168.1.53:8060/mqtt/messages",JSONObject.toJSONString(new Result(deviceCode,createTime,data)));
                            log.info("能源发送数据--->"+JSONObject.toJSONString(new Result(deviceCode,createTime,data)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            if(!client.isConnected()){
                client.connect(options);
            }
            //订阅消息
            client.subscribe(topic.split(","), new int[]{2});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doPost(CloseableHttpClient client, String url, String data) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(data, Charset.forName("UTF-8"));
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json;charset=UTF-8");
        CloseableHttpResponse response = client.execute(httpPost);
        if (httpPost != null) {
            httpPost.releaseConnection();
        }
    }
}