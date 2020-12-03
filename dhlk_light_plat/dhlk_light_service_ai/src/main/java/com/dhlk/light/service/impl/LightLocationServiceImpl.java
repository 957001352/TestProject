package com.dhlk.light.service.impl;

import com.dhlk.light.dao.LightLocationDao;
import com.dhlk.light.entity.LightLocation;
import com.dhlk.light.service.LightLocationService;
import com.dhlk.light.util.Result;
import com.dhlk.light.util.ResultEnum;
import com.dhlk.light.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/26
 * Time:9:10
 * @Description: 灯位置信息service实现类
 */
@Service
public class LightLocationServiceImpl implements LightLocationService {

    @Autowired
    private LightLocationDao locationDao;

    /**
     * 所有灯位置信息
     *
     * @return
     */
    @Override
    public Result findAll(String tenantId,String areaId) {
        List<LightLocation> lightLocationList = locationDao.findAll(tenantId,areaId);
        return ResultUtils.success(lightLocationList);
    }

    /**
     * 通过sn查询灯位置信息
     *
     * @param sn
     * @return
     */
    @Override
    public Result selectBySn(String sn) {
        if (sn == null) {
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }

        LightLocation dhlkLightLocation = locationDao.selectBySn(sn);

        if (dhlkLightLocation != null) {
            return ResultUtils.success(dhlkLightLocation);
        } else {
            return ResultUtils.error("没有该灯信息");
        }
    }

    /**
     * 通过租户id查询灯位置信息
     *
     * @param tenantId
     * @return 返回 List<LightLocation>
     */
    @Override
    public Result selectByTenantId(Integer tenantId) {
        return ResultUtils.success(locationDao.selectLightByTenant(tenantId));
    }
}
