package com.dhlk.light.service.controller;

import com.dhlk.domain.Result;
import com.dhlk.light.service.service.MqttSubsribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/8/21
 */
@RestController
@RequestMapping(value = "/mqttSubsribe")
public class MqttSubsribeController {
    @Autowired
    private MqttSubsribeService mqttSubsribeService;

    @PostMapping(value = "/subsribe")
    public Result subsribe(@RequestParam(value = "topic")  String topic,
                           @RequestParam(value = "mess") String mess){
        return mqttSubsribeService.subsribe(topic,mess);
    }
}