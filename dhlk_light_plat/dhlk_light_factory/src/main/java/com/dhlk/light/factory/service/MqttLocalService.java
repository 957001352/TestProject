package com.dhlk.light.factory.service;

/**
 * @Description mqtt本地消息订阅接口
 * @Author lpsong
 * @Date 2020/6/1
 */
public interface MqttLocalService {
    void subsribe(String topic, String mess);
}
