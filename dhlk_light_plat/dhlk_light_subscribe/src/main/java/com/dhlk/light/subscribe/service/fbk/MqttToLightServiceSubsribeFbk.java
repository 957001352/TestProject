package com.dhlk.light.subscribe.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import com.dhlk.light.subscribe.service.MqttToLightServiceSubsribe;
import com.dhlk.utils.ResultUtils;
import org.springframework.stereotype.Service;

@Service
public class MqttToLightServiceSubsribeFbk implements MqttToLightServiceSubsribe {
    @Override
    public Result subsribe(String topic, String mess) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
