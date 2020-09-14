package com.dhlk.light.service.service;

import com.dhlk.domain.Result;

/**
 * @Description mqtt消息订阅接口
 * @Author lpsong
 * @Date 2020/6/1
 */
public interface MqttSubsribeService {
    Result subsribe(String topic, String mess);
}
