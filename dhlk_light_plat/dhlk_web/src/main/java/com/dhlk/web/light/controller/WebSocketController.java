package com.dhlk.web.light.controller;

import com.dhlk.domain.Result;
import com.dhlk.web.light.service.WebSocketService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/websocked")
@Api(description = "websocked工具", value = "websocked")
public class WebSocketController {
    @Autowired
    private WebSocketService webSocketService;
    @GetMapping("/getIpPort")
    public Result getIpPort(){
        return webSocketService.getIpPort();
    }
}
