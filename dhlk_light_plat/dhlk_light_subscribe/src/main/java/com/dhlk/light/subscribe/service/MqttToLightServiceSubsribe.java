package com.dhlk.light.subscribe.service;

import com.dhlk.domain.Result;
import com.dhlk.light.subscribe.service.fbk.MqttToLightServiceSubsribeFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description mqtt消息订阅接口
 * @Author lpsong
 * @Date 2020/6/1
 */
@FeignClient(value = "light-service/mqttSubsribe", fallback = MqttToLightServiceSubsribeFbk.class)
public interface MqttToLightServiceSubsribe {

    @PostMapping(value = "/subsribe")
    Result subsribe(@RequestParam(value = "topic") String topic,
                    @RequestParam(value = "mess") String mess);
}
