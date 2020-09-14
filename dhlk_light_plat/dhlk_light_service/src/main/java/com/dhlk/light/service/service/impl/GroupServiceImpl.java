package com.dhlk.light.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.domain.Result;
import com.dhlk.entity.light.Led;
import com.dhlk.entity.light.LedSwitch;
import com.dhlk.entity.light.Switch;
import com.dhlk.entity.light.SyncDataResult;
import com.dhlk.enums.ResultEnum;
import com.dhlk.light.service.dao.LedSwitchDao;
import com.dhlk.light.service.dao.SwitchDao;
import com.dhlk.light.service.mqtt.MqttSendServer;
import com.dhlk.light.service.service.DataSyncService;
import com.dhlk.light.service.service.GroupService;
import com.dhlk.light.service.util.HeaderUtil;
import com.dhlk.light.service.util.LedConst;
import com.dhlk.light.service.util.LightDeviceUtil;
import com.dhlk.service.RedisService;
import com.dhlk.systemconst.Const;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GroupServiceImpl implements GroupService {
    @Autowired
    private SwitchDao switchDao;
    @Autowired
    private LedSwitchDao ledSwitchDao;
    @Autowired
    private HeaderUtil headerUtil;
    @Autowired
    private DataSyncService dataSyncService;

    @Override
    @Transactional
    public Result save(Switch swich) {
        if(CheckUtils.isNull(swich)){
            return ResultUtils.error("参数错误");
        }
        if(CheckUtils.isNull(swich.getTenantId())){
            swich.setTenantId(headerUtil.tenantId());
        }
        if (switchDao.isRepeatName(swich) > 0) {
            return ResultUtils.error("名称已存在");
        }
        Integer insert = switchDao.insert(swich);

        ledSwitchDao.deleteBySwitchId(swich.getId());
        if (swich.getLeds() != null && swich.getLeds().size() > 0) {
            List<Led> leds = swich.getLeds();
            ledSwitchDao.saveSwitchLeds(swich.getId(),0,leds);
            //设置硬件的灯的组id
            //lightDeviceUtil.setLedGrpId(groupLed.get(groupId),groupId);
        }
        //给本地发送数据,新增
        if(insert > 0){
            String jsonStrSwich = JSON.toJSONString(swich);
            SyncDataResult syncData = new SyncDataResult(Const.SWITCH_TABLE_NAME,jsonStrSwich, Const.SYNC_DATA_OPERATE_INSERT,headerUtil.tenantId());
            dataSyncService.syncDataToLocal(syncData);

            List<LedSwitch> ledSwitchs = ledSwitchDao.findListBySwitchId(swich.getId());
            for (LedSwitch ledSwitch:ledSwitchs) {
                String jsonStrLedSwitch = JSON.toJSONString(ledSwitch);
                SyncDataResult sync = new SyncDataResult(Const.LED_GROUP_TABLE_NAME,jsonStrLedSwitch, Const.SYNC_DATA_OPERATE_INSERT,headerUtil.tenantId());
                dataSyncService.syncDataToLocal(sync);
            }
        }
        return insert > 0?ResultUtils.success():ResultUtils.failure();
    }

    @Override
    public Result delete(String id) {
        if(CheckUtils.isNull(id)){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        if(CheckUtils.isIntNumeric(id)){
            ledSwitchDao.deleteBySwitchId(Integer.parseInt(id));
            //删除给本地发送消息
            String jsonStrSwitch = JSON.toJSONString(id);
            SyncDataResult sync = new SyncDataResult(Const.SWITCH_TABLE_NAME,jsonStrSwitch, Const.SYNC_DATA_OPERATE_DELETE,headerUtil.tenantId());
            dataSyncService.syncDataToLocal(sync);
        }
        Integer delete = switchDao.delete(new String[]{id});
        if(delete > 0){
            //删除给本地发送消息
            String jsonStrLedSwitch = JSON.toJSONString(id);
            SyncDataResult sync = new SyncDataResult(Const.LED_GROUP_TABLE_NAME,jsonStrLedSwitch, Const.SYNC_DATA_OPERATE_DELETE,headerUtil.tenantId());
            dataSyncService.syncDataToLocal(sync);
        }
        return delete > 0?ResultUtils.success():ResultUtils.failure();
    }

    @Override
    public Result findList(String areaId) {
        return ResultUtils.success(switchDao.findListByAreaId(areaId,headerUtil.tenantId()));
    }
}
