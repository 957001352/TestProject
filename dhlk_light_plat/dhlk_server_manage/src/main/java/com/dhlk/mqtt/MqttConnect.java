package com.dhlk.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/6/1
 */
public class MqttConnect {


    //生成配置对象，用户名，密码等
    public MqttConnectOptions getOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setConnectionTimeout(10);
        //设置心跳
        options.setKeepAliveInterval(20);
        options.setAutomaticReconnect(true);
        return options;
    }
    public MqttConnectOptions getOptions(MqttConnectOptions options) {
        options.setCleanSession(false);
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(20);
        options.setAutomaticReconnect(true);
        return options;
    }
}