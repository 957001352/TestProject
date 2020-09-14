package com.dhlk.service;

/**
 * @Description mqtt云端消息订阅接口
 * @Author lpsong
 * @Date 2020/6/1
 */
public interface MqttCloudService {
    void subsribe(String topic, String mess);
}
