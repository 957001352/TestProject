package com.dhlk.light.service.service;

import com.dhlk.domain.Result;

public interface AppLoginService {
    /**
     * app登录
     * @param loginName
     * @param password
     * @return
     */
    Result appLogin(String loginName, String password,String tenantCode,String systemRunTime);


    /**
     * app登出
     */
    Result appLogout();

    /**
     * token同步
     */
    Result sycToken(String token);
}
