package com.dhlk.web.light.service;

import com.dhlk.domain.Result;
import com.dhlk.web.light.service.fbk.WebSocketServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "light-service/websocket", fallback = WebSocketServiceFbk.class)
public interface WebSocketService {
    @GetMapping("/getIpPort")
    Result getIpPort();
}
