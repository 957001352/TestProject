package com.dhlk.light.service;

import com.dhlk.light.util.Result;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/26
 * Time:15:04
 * @Description:
 */
public interface LightAreaService {

    /**
     * 根据租户id查询区域信息
     *
     * @param tenantId
     * @return
     */
    Result selectAreaByTenantId(Integer tenantId);

}
