package com.dhlk.light.service;

import com.dhlk.light.util.Result;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/13
 * Time:11:04
 * @Description:
 */
public interface LightLocationService {

    Result findAll(String tenantId,String areaId);

    Result selectBySn(String sn);

    Result selectByTenantId(Integer tenantId);
}
