package com.dhlk.light.service.service.impl;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.LedParamInfo;
import com.dhlk.light.service.dao.LedParamInfoDao;
import com.dhlk.light.service.service.LedParamInfoService;
import com.dhlk.light.service.util.LedConst;
import com.dhlk.light.service.util.LedResult;
import com.dhlk.light.service.util.LightDeviceUtil;
import com.dhlk.service.RedisService;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class LedParamInfoServiceImpl implements LedParamInfoService {
    @Autowired
    private LedParamInfoDao ledParamInfoDao;
    @Autowired
    private LightDeviceUtil lightDeviceUtil;
    @Override
    public Result save(LedParamInfo ledParamInfo) {
        if(ledParamInfo == null){
            return ResultUtils.error("参数错误");
        }
        return ledParamInfoDao.insert(ledParamInfo) > 0?ResultUtils.success():ResultUtils.failure();
    }

    @Override
    public Result update(LedParamInfo ledParamInfo) {
        if(ledParamInfo == null){
            return ResultUtils.error("参数错误");
        }
        return ledParamInfoDao.update(ledParamInfo) > 0?ResultUtils.success():ResultUtils.failure();
    }

    @Override
    public Result findList(String sns) {
        if(CheckUtils.isNull(sns)){
            return ResultUtils.success(ledParamInfoDao.findList(null));
        }
        String[] split = sns.split(",");
        return ResultUtils.success(ledParamInfoDao.findList(split));
    }

    @Override
    public Integer isExistSn(String sn) {
        return ledParamInfoDao.isExistSn(sn);
    }

    @Override
    public Integer insertList(List<LedParamInfo> list) {
        return ledParamInfoDao.insertList(list);
    }

    @Override
    public Integer updateList(List<LedParamInfo> ledParamInfos) {
        return ledParamInfoDao.updateList(ledParamInfos);
    }

    @Override
    public Result refreshParam(InfoBox<String> infoBox) {
        if(CheckUtils.isNull(infoBox.getSns()) || CheckUtils.isNull(infoBox.getTenantId())){
            return ResultUtils.failure();
        }
        lightDeviceUtil.refreshParam(infoBox.getSns(),infoBox.getTenantId());
        return ResultUtils.success();
    }
}
