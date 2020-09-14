package com.dhlk.mqtt.subscribe.utils;


import com.alibaba.fastjson.JSONObject;
import com.dhlk.mqtt.subscribe.entity.Result;
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
import org.springframework.util.ResourceUtils;
import org.springframework.util.SocketUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/4/7
 */
@Component
public class HttpClientMqtt {
    private Logger log = LoggerFactory.getLogger(HttpClientMqtt.class);
    //config
    @Value("${mqtt.host}")
    private  String HOST;//hdfs 访问地址
    @Value("${mqtt.topic}")
    private  String TOPIC;//默认主题
    private String clientid = UUID.randomUUID().toString();//客户端id
    private Map<String,String> apiList=null;//请求接口集合
    private MqttClient client;
    private MqttConnectOptions options;
    @Value("${mqtt.username}")
    private String userName;
    @Value("${mqtt.password}")
    private String passWord;
    private CloseableHttpClient httpClient;//httpClint客户端
    @Bean
    public void start() {
        try {
            // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttClient(HOST, clientid, new MemoryPersistence());
            // MQTT的连接设置
            options = new MqttConnectOptions();
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
            //httpClient=new  SSLClient();
            //读取转发请求接口
            apiList=this.readApiList();
            // 设置回调
            client.setCallback(new MqttCallback(){
                @SneakyThrows
                public void connectionLost(Throwable cause) {
                    // 连接丢失后，一般在这里面进行重连
                    String result="{\"code\": 0,\"msg\": \"连接断开，请联系据提供方\",\"data\":\"\"}";
                }
                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete---------" + token.isComplete());
                }
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    try {
                        for(Map.Entry<String, String> entry : apiList.entrySet()){
                            String url = entry.getKey().trim();
                            List<String> tables = Arrays.asList(entry.getValue().split(","));
                            JSONObject object=JSONObject.parseObject(message.toString());
                            if(tables.contains(object.get("table").toString())){
                                String jsonStr=JSONObject.toJSONString(new Result<>(object.get("table").toString(),200,"数据发送成功",object.get("after")));
                                doPost(httpClient,url,jsonStr);
                                log.info("发送数据--->"+jsonStr);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
//            MqttTopic topic = client.getTopic(TOPIC);
//            //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
//            options.setWill(topic, "close".getBytes(), 2, true);

            client.connect(options);
            //订阅消息
            int[] Qos  = {2};
            String[] topic1 = {TOPIC};
            client.subscribe(topic1, Qos);

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
    public  Map<String,String> readApiList() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("ApiList.txt")));
        String s="";
        Map<String,String> res=new HashMap<String,String>();
        while ((s = br.readLine()) != null) {
            String[] strs=  s.split("topic=");
            if(strs!=null&&strs.length>1){
                String url=strs[0].replace("url=","");
                String topic=strs[1].replace("topic=","");
                res.put(url,topic);
            }
        }
        return res;
    }
//    public static void main(String[] args) throws Exception {
//       HttpClientMqtt client = new HttpClientMqtt();
//       client.start();
//    }


}