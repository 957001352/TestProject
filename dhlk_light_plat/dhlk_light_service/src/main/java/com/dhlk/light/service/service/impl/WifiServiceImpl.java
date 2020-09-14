package com.dhlk.light.service.service.impl;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.*;
import com.dhlk.light.service.dao.WifiDao;
import com.dhlk.light.service.service.LedParamInfoService;
import com.dhlk.light.service.service.WifiService;
import com.dhlk.light.service.util.HeaderUtil;
import com.dhlk.light.service.util.LedConst;
import com.dhlk.light.service.util.LightDeviceUtil;
import com.dhlk.service.RedisService;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * @author xkliu
 * @date 2020/6/30
 */
@Service
@Transactional
public class WifiServiceImpl implements WifiService {

    @Autowired
    private WifiDao wifiDao;

    @Autowired
    private HeaderUtil headerUtil;

    @Autowired
    private LightDeviceUtil lightDeviceUtil;
    @Autowired
    private RedisService redisService;

    @Override
    public Result save(Wifi wifi) {
        Integer flag = 0;
        if (CheckUtils.isNull(wifi.getId())) {
            //先删除tenantId相同的 Wifi 对象，删除成功之后在进行保存
            wifiDao.deleteByTenantId(headerUtil.tenantId());
            wifi.setTenantId(headerUtil.tenantId());
            flag = wifiDao.insert(wifi);
        } else {
            Wifi wifis = wifiDao.selectTenantById(wifi.getId());
            if(wifis == null){
                return  ResultUtils.failure();
            }
            flag = wifiDao.update(wifi);
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result findOne() {
        Wifi wifi = wifiDao.selectByTenantId(headerUtil.tenantId());
        return ResultUtils.success(wifi);
    }

    @Override
    public Result delete(String ids) {
        if (!CheckUtils.isNull(ids)) {
            if (wifiDao.delete(Arrays.asList(ids.split(","))) > 0) {
                return ResultUtils.success();
            }
        }
        return ResultUtils.failure();
    }

    @Override
    public Result wifiContro(LedWifi ledWifi) {
        if(redisService.hasKey(LedConst.REDIS_RECORD_REFRESH_SETWIFI_TIME_+headerUtil.cloudToken())){
            return ResultUtils.error("操作间隔不得小于5秒");
        }
        Integer flag = 0;
        lightDeviceUtil.wifiContro(ledWifi.getSns(), ledWifi.getLedWifiInfo());
        redisService.set(LedConst.REDIS_RECORD_REFRESH_SETWIFI_TIME_+headerUtil.cloudToken(),ledWifi.getLedWifiInfo()+"",LedConst.BANTIME);
        //删除租户下的数据,在进行保存
        wifiDao.deleteByTenantId(headerUtil.tenantId());
        Wifi wifi = new Wifi();
        wifi.setTenantId(headerUtil.tenantId());
        wifi.setType("0");
        BeanUtils.copyProperties(ledWifi.getLedWifiInfo(),wifi);
        flag = wifiDao.insert(wifi);
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();

    }

    @Override
    public Result switchWifiContro(InfoBox<SwitchWifiInfo> infoBox) {
        Integer flag = 0;
        lightDeviceUtil.switchWifiContro(infoBox.getSns(), infoBox.getT());
        //删除租户下的数据,在进行保存
        wifiDao.deleteByTenantId(headerUtil.tenantId());
        Wifi wifi = new Wifi();
        wifi.setTenantId(headerUtil.tenantId());
        wifi.setType("1");
        BeanUtils.copyProperties(infoBox.getT(),wifi);
        flag = wifiDao.insert(wifi);
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }
}
