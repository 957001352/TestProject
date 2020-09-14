package com.dhlk.web.light.controller;

import com.dhlk.domain.Result;
import com.dhlk.web.light.service.AppLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    public Result appLogin(@ApiParam(name = "loginName",value = "用户登录名称",required = true) @RequestParam("loginName") String loginName,
                           @ApiParam(name = "password",value = "用户登录密码",required = true) @RequestParam("password") String password,
                           @ApiParam(name = "tenantCode",value = "客户编码",required = true) @RequestParam("tenantCode") String tenantCode,
                           @ApiParam(name = "systemRunTime",value = "运行时长") @RequestParam(value = "systemRunTime",required = false) String systemRunTime
                           ) {
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
    public Result sycToken(@ApiParam(name = "token",value = "访问令牌",required = true) @RequestParam("token")String token) {
        return appLoginService.sycToken(token);
    }
}
