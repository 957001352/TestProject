package com.dhlk.light.service.controller;

import com.dhlk.domain.Result;
import com.dhlk.light.service.service.WebSocketService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/websocket")
@Api(description = "websocket工具", value = "websocket")
public class WebSocketController {
    @Autowired
    private WebSocketService webSocketService;
    @GetMapping("/getIpPort")
    @RequiresAuthentication
    public Result getIpPort(){
        return webSocketService.getIpPort();
    }
}
