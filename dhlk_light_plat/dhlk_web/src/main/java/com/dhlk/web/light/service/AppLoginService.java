package com.dhlk.web.light.service;

import com.dhlk.domain.Result;
import com.dhlk.web.light.service.fbk.AppLoginServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description app用户登录
 * @Author gchen
 * @Date 2020/3/11
 */
@FeignClient(value = "light-service/app", fallback = AppLoginServiceFbk.class)
public interface AppLoginService {
    @PostMapping(value = "/appLogin")
    Result appLogin(@RequestParam("loginName") String loginName,
                    @RequestParam("password") String password,
                    @RequestParam("tenantCode") String tenantCode,
                    @RequestParam(value = "systemRunTime",required = false) String systemRunTime);

    @GetMapping("/appLogout")
    Result appLogout();

    @PostMapping("/sycToken")
    Result sycToken(@RequestParam("token")String token);
}
