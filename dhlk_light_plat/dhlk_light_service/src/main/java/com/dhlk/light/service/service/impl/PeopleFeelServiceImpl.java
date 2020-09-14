package com.dhlk.light.service.service.impl;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.PeopleFeel;
import com.dhlk.entity.light.PeopleFeelInfo;
import com.dhlk.light.service.dao.PeopleFeelDao;
import com.dhlk.light.service.service.LedParamInfoService;
import com.dhlk.light.service.service.PeopleFeelService;
import com.dhlk.light.service.util.HeaderUtil;
import com.dhlk.light.service.util.LedConst;
import com.dhlk.light.service.util.LightDeviceUtil;
import com.dhlk.service.RedisService;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * @author xkliu
 * @date 2020/6/30
 *
 * 人感service实现
 */
@Service
@Transactional
public class PeopleFeelServiceImpl implements PeopleFeelService {

    @Autowired
    private PeopleFeelDao peopleFeelDao;

    @Autowired
    private HeaderUtil headerUtil;

    @Autowired
    private LightDeviceUtil lightDeviceUtil;

    @Autowired
    private LedParamInfoService ledParamInfoService;

    @Autowired
    private RedisService redisService;

    @Override
    public Result save(PeopleFeel peopleFeel) {
        Integer flag = 0;

        if (CheckUtils.isNull(peopleFeel.getId())) {
            //先删除tenantId相同的 PeopleFeel 对象，删除成功之后在进行保存
            peopleFeelDao.deleteByTenantId(headerUtil.tenantId());
            peopleFeel.setTenantId(headerUtil.tenantId());
            flag = peopleFeelDao.insert(peopleFeel);
        } else {
            PeopleFeel peopleFeels = peopleFeelDao.selectTenantById(peopleFeel.getId());
            if(peopleFeels == null){
                return ResultUtils.failure();
            }
            flag = peopleFeelDao.update(peopleFeel);
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result findOne() {
        PeopleFeel peopleFeel = peopleFeelDao.selectByTenantId(headerUtil.tenantId());
        return ResultUtils.success(peopleFeel);
    }

    @Override
    public Result delete(String ids) {
        if (!CheckUtils.isNull(ids)) {
            if (peopleFeelDao.delete(Arrays.asList(ids.split(","))) > 0) {
                return ResultUtils.success();
            }
        }
        return ResultUtils.failure();
    }

    @Override
    public Result peopleFeelContro(InfoBox<PeopleFeelInfo> infoBox) {
        if(redisService.hasKey(LedConst.REDIS_RECORD_REFRESH_SETPEOPLEFEEL_TIME_+headerUtil.cloudToken())){
            return ResultUtils.error("操作间隔不得小于5秒");
        }

        Integer flag = 0;
        lightDeviceUtil.peopleFeelContro(infoBox.getSns(), infoBox.getT());
        redisService.set(LedConst.REDIS_RECORD_REFRESH_SETPEOPLEFEEL_TIME_+headerUtil.cloudToken(),infoBox.getT()+"",LedConst.BANTIME);
        //删除租户下的数据,在进行保存
        peopleFeelDao.deleteByTenantId(headerUtil.tenantId());
        PeopleFeel peopleFeel = new PeopleFeel();
        peopleFeel.setTenantId(headerUtil.tenantId());
        peopleFeel.setMaxvalue(infoBox.getT().getMaxvalue().floatValue());
        peopleFeel.setMinvalue(infoBox.getT().getMinvalue().floatValue());
        peopleFeel.setN_ramp_tm(infoBox.getT().getN_ramp_tm().floatValue());
        peopleFeel.setTrig_delay_tm(infoBox.getT().getTrig_delay_tm().floatValue());
        peopleFeel.setOn_off(infoBox.getT().getOn_off());
        flag = peopleFeelDao.insert(peopleFeel);
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result memoryPeopleFeel() {
        return ResultUtils.success(peopleFeelDao.selectByTenantId(headerUtil.tenantId()));
    }

}
