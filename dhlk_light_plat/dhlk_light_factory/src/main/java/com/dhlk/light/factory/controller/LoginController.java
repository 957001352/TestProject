package com.dhlk.light.factory.controller;

import com.dhlk.domain.Result;
import com.dhlk.light.factory.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "LoginController",description = "登录登出")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @ApiOperation(value = "app登录")
    @PostMapping("/login")
    public Result login(@RequestParam("loginName") String loginName,
                        @RequestParam("password") String password,
                        @RequestParam("tenantCode") String tenantCode) {
        Result result = loginService.login(loginName,password,tenantCode);
        return result;
    }

    @ApiOperation(value = "登出")
    @GetMapping("/logout")
    public Result logout() {
        return loginService.logout();
    }

    @ApiOperation(value = "获取客户编码")
    @GetMapping("/getTenantCode")
    public Result getTenantCode() {
        return loginService.getTenantCode();
    }
}
