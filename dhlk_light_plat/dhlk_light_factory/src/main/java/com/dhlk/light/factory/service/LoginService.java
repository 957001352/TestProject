package com.dhlk.light.factory.service;

import com.dhlk.domain.Result;

public interface LoginService {
    /**
     * 登录
     * @param loginName
     * @param password
     * @return
     */
    Result login(String loginName, String password,String tenantCode);

    /**
     * 登出
     */
    Result logout();

    /**
     * 获取租户编码
     */
    Result getTenantCode();
}
