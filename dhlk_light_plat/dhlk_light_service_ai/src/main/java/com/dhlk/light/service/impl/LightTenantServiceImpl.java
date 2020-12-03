package com.dhlk.light.service.impl;

import com.dhlk.light.dao.LightTenantDao;
import com.dhlk.light.entity.LightTenant;
import com.dhlk.light.service.LightTenantService;
import com.dhlk.light.util.Result;
import com.dhlk.light.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/25
 * Time:14:24
 * @Description:
 */
@Service
public class LightTenantServiceImpl implements LightTenantService {

    @Autowired
    private LightTenantDao dhlkLightTenantDao;

    /**
     * 查询所有的租户
     *
     * @return
     */
    @Override
    public Result findAll() {
        List<LightTenant> tenantList = dhlkLightTenantDao.findAll();
        return ResultUtils.success(tenantList);
    }
}
