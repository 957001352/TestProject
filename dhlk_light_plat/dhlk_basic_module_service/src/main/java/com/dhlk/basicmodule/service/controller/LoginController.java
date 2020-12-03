package com.dhlk.basicmodule.service.controller;


import com.alibaba.fastjson.JSONObject;
import com.dhlk.basicmodule.service.service.LoginService;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@Api(value = "LoginController", description = "登录登出")
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

   /* @ApiOperation(value = "登录")
    @PostMapping("/login")
    @CrossOrigin
    public Result login(@RequestParam("loginName") String loginName,
                        @RequestParam("password") String password,
                        @RequestParam("kaptcha") String kaptcha) {
        Result result = loginService.login(loginName,password,kaptcha);
        return result;
    }*/

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    @CrossOrigin
    public Result login(@RequestParam("loginName") String loginName,
                        @RequestParam("password") String password,
                        @RequestParam("redisKey") String redisKey,
                        @RequestParam("x") String x,
                        @RequestParam("y") String y
    ) {
        Result result = loginService.login(loginName,password,redisKey,x,y);
        return result;
    }
    @ApiOperation(value = "登出")
    @GetMapping("/logout")
    public Result logout() {
        return loginService.logout();
    }

    @ApiOperation(value = "获取验证码")
    @GetMapping("/kaptcha")
    public Result kaptcha() {
        return loginService.kaptcha();
    }

    @ApiOperation(value = "获取tb登录token")
    @GetMapping("/getTbToken")
    @RequiresAuthentication
    public Result getTbToken() {
        return loginService.getTbToken();
    }

    @ApiOperation(value = "获取E2C登录token")
    @GetMapping("/getToken")
    @RequiresAuthentication
    public Result getToken() {
        return loginService.getToken();
    }

    @ApiOperation(value = "校验token是否正确")
    @PostMapping("/checkToken")
    public Result checkToken() {
        return loginService.checkToken();
    }
}

