package com.dhlk.light.subscribe.service;


/**
 * @Description mqtt消息订阅接口
 * @Author lpsong
 * @Date 2020/6/1
 */
public interface MqttSubsribeService {

    void subsribe(String topic,String mess);
}
