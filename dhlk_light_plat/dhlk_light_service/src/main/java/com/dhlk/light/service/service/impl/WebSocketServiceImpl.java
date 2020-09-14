package com.dhlk.light.service.service.impl;

import com.dhlk.domain.Result;
import com.dhlk.light.service.service.WebSocketService;
import com.dhlk.light.service.websocket.WebSocketGetServerIp;
import com.dhlk.utils.ResultUtils;
import org.springframework.stereotype.Service;

@Service
public class WebSocketServiceImpl implements WebSocketService {
    @Override
    public Result getIpPort() {
        return ResultUtils.success(WebSocketGetServerIp.getServiceInstanceUrl());
    }
}
