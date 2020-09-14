package com.dhlk.light.factory.mqtt;

import com.dhlk.utils.CheckUtils;
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
        options.setConnectionTimeout(0);
        //设置心跳
        options.setKeepAliveInterval(5);
        options.setAutomaticReconnect(false);
        return options;
    }
}