package com.dhlk.light.service.service.impl;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.Intensity;
import com.dhlk.entity.light.IntensityInfo;
import com.dhlk.light.service.dao.IntensityDao;
import com.dhlk.light.service.enums.LedEnum;
import com.dhlk.light.service.service.IntensityService;
import com.dhlk.light.service.service.LedParamInfoService;
import com.dhlk.light.service.util.HeaderUtil;
import com.dhlk.light.service.util.LedConst;
import com.dhlk.light.service.util.LedResult;
import com.dhlk.light.service.util.LightDeviceUtil;
import com.dhlk.service.RedisService;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author xkliu
 * @date 2020/6/30
 */
@Service
@Transactional
public class IntensityServiceImpl implements IntensityService {

    @Autowired
    private IntensityDao intensityDao;

    @Autowired
    private HeaderUtil headerUtil;

    @Autowired
    private LightDeviceUtil lightDeviceUtil;

    @Autowired
    private LedParamInfoService ledParamInfoService;
    @Autowired
    private RedisService redisService;

    @Override
    public Result save(Intensity intensity) {
        Integer flag = 0;
        if (CheckUtils.isNull(intensity.getId())) {
            //先删除tenantId相同的 Intensity 对象，删除成功之后在进行保存
            intensityDao.deleteByTenantId(headerUtil.tenantId());
            intensity.setTenantId(headerUtil.tenantId());
            flag = intensityDao.insert(intensity);
        } else {
            Intensity intensitys = intensityDao.selectTenantById(intensity.getId());
            if(intensitys == null){
                return ResultUtils.failure();
            }
            flag = intensityDao.update(intensity);
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result findOne() {
        Intensity intensity = intensityDao.selectByTenantId(headerUtil.tenantId());
        return ResultUtils.success(intensity);
    }

    @Override
    public Result delete(String ids) {
        if (!CheckUtils.isNull(ids)) {
            if (intensityDao.delete(Arrays.asList(ids.split(","))) > 0) {
                return ResultUtils.success();
            }
        }
        return ResultUtils.failure();
    }

    @Override
    public Result intensityContro(InfoBox<IntensityInfo> intensityInfo) {
        if(redisService.hasKey(LedConst.REDIS_RECORD_REFRESH_SETLIGHTFEEL_TIME_+headerUtil.cloudToken())){
            return ResultUtils.error("操作间隔不得小于5秒");
        }

        Integer flag = 0;
        lightDeviceUtil.intensityContro(intensityInfo.getSns(), intensityInfo.getT());
        redisService.set(LedConst.REDIS_RECORD_REFRESH_SETLIGHTFEEL_TIME_+headerUtil.cloudToken(),intensityInfo.getT()+"",LedConst.BANTIME);
        //删除租户下的数据,在进行保存
        intensityDao.deleteByTenantId(headerUtil.tenantId());
        Intensity intensity = new Intensity();
        intensity.setTenantId(headerUtil.tenantId());
        intensity.setIllumi_flr(intensityInfo.getT().getIllumi_flr().floatValue());
        intensity.setIllumi_flr_max(intensityInfo.getT().getIllumi_flr_max().floatValue());
        intensity.setIllumi_top(intensityInfo.getT().getIllumi_top().floatValue());
        intensity.setIllumi_top_min(intensityInfo.getT().getIllumi_top_min().floatValue());
        intensity.setOn_off(intensityInfo.getT().getOn_off());
        intensity.setControlParam(intensityInfo.getControlParam());
        flag = intensityDao.insert(intensity);
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result memoryIntensity() {
        return ResultUtils.success(intensityDao.selectByTenantId(headerUtil.tenantId()));
    }
}
