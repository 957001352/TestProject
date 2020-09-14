package com.dhlk.light.service.controller;

import com.dhlk.domain.Result;
import com.dhlk.light.service.service.AppLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
* @Description:    app登录接口
* @Author:         gchen
* @CreateDate:     2020/6/16 8:55
*/
@RestController
@Api(value = "AppLoginController", description = "app登录登出")
@RequestMapping("/app")
public class AppLoginController {
    @Autowired
    private AppLoginService appLoginService;
    @ApiOperation(value = "app登录")
    @PostMapping("/appLogin")
    public Result appLogin(@RequestParam("loginName") String loginName,
                           @RequestParam("password") String password,
                           @RequestParam("tenantCode") String tenantCode,
                           @RequestParam(value = "systemRunTime",required = false) String systemRunTime) {
        Result result = appLoginService.appLogin(loginName,password,tenantCode,systemRunTime);
        return result;
    }

    @ApiOperation(value = "app登出")
    @GetMapping("/appLogout")
    public Result appLogout() {
        return appLoginService.appLogout();
    }

    @ApiOperation(value = "本地token同步云端")
    @PostMapping("/sycToken")
    public Result sycToken(@RequestParam("token")String token) {
        return appLoginService.sycToken(token);
    }
}
