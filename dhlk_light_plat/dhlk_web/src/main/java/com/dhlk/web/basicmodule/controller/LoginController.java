package com.dhlk.web.basicmodule.controller;

import com.dhlk.web.basicmodule.service.LoginService;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
* 登录
*/
@RestController
@Api(value = "LoginController",description = "登录登出")
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 登录
     */
  /*  @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result login(@ApiParam(name = "loginName",value = "用户登录名称",required = true) @RequestParam("loginName") String loginName,
                        @ApiParam(name = "password",value = "用户登录密码",required = true) @RequestParam("password") String password,
                        @ApiParam(name = "kaptcha",value = "验证码",required = true) @RequestParam("kaptcha") String kaptcha) {
        Result result = loginService.login(loginName,password,kaptcha);
        return result;
    }*/
    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result login(@ApiParam(name = "loginName",value = "用户登录名称",required = true) @RequestParam("loginName") String loginName,
                        @ApiParam(name = "password",value = "用户登录密码",required = true) @RequestParam("password") String password,
                        @ApiParam(name = "redisKey",value = "用户唯一标识",required = true) @RequestParam("redisKey") String redisKey,
                        @ApiParam(name = "x",value = "坐标",required = true) @RequestParam("x") String x,
                        @ApiParam(name = "y",value = "坐标",required = true) @RequestParam("y") String y
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
    public Result getTbToken() {
        return loginService.getTbToken();
    }

    @ApiOperation(value = "获取E2C登录token")
    @GetMapping("/getToken")
    public Result getToken() {
        return loginService.getToken();
    }


    @ApiOperation(value = "校验token是否正确")
    @PostMapping("/checkToken")
    public Result checkToken() {
        return loginService.checkToken();
    }

}
