package com.dhlk.light.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.dhlk.domain.Result;
import com.dhlk.entity.light.OriginalPower;
import com.dhlk.entity.light.SyncDataResult;
import com.dhlk.light.service.dao.OriginalPowerDao;
import com.dhlk.light.service.mqtt.MqttSendServer;
import com.dhlk.light.service.service.DataSyncService;
import com.dhlk.light.service.service.OriginalPowerService;
import com.dhlk.light.service.util.HeaderUtil;
import com.dhlk.light.service.util.LedConst;
import com.dhlk.systemconst.Const;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * @author xkliu
 * @date 2020/6/10
 * <p>
 * 企业历史照明功率维护service实现类
 */
@Service
@Transactional
public class OriginalPowerServiceImpl implements OriginalPowerService {

    @Autowired
    private OriginalPowerDao originalPowerDao;

    @Autowired
    private HeaderUtil headerUtil;

    @Autowired
    private MqttSendServer mqttSendServer;

    @Autowired
    private DataSyncService dataSyncService;

    @Override
    public Result save(OriginalPower originalPower) {
        Integer flag = 0;
        Integer tenantId = headerUtil.tenantId();
        OriginalPower power = originalPowerDao.selectOriginalPowerByTenantId(tenantId);
        if (power == null) {
            originalPower.setTenantId(tenantId);
            flag = originalPowerDao.insert(originalPower);
            if (flag > 0) {
                //给本地发送数据,新增
                String jsonStrOrig = JSON.toJSONString(originalPower);
                SyncDataResult syncData = new SyncDataResult(Const.ORIGINALPOWER_TABLE_NAME, jsonStrOrig, Const.SYNC_DATA_OPERATE_INSERT, tenantId);
                dataSyncService.syncDataToLocal(syncData);
            }
        } else {
            flag = originalPowerDao.update(originalPower);
            if (flag > 0) {
                //给本地发送数据,修改
                originalPower.setTenantId(tenantId);
                String jsonStrOrig = JSON.toJSONString(originalPower);
                SyncDataResult syncData = new SyncDataResult(Const.ORIGINALPOWER_TABLE_NAME, jsonStrOrig, Const.SYNC_DATA_OPERATE_UPDATE, tenantId);
                dataSyncService.syncDataToLocal(syncData);
            }
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result findOne() {
        OriginalPower originalPowerInfo = originalPowerDao.selectOpByTenantId(headerUtil.tenantId());
        return ResultUtils.success(originalPowerInfo);
    }

    @Override
    public Result delete(String ids) {
        if (!CheckUtils.isNull(ids)) {
            if (originalPowerDao.delete(Arrays.asList(ids.split(","))) > 0) {
                return ResultUtils.success();
            }
        }
        return ResultUtils.failure();
    }

    @Override
    public Result preBrightness(Integer preBrightness) {
        Integer flag = 0;
        if (!CheckUtils.isNull(preBrightness)) {
            OriginalPower power = originalPowerDao.selectOriginalPowerByTenantId(headerUtil.tenantId());
            if (power == null) {
                return ResultUtils.error("请先设置系统配置");
            }
            flag = originalPowerDao.updateOriginalPower(preBrightness, headerUtil.tenantId());
        }
        if (flag > 0) {
            //给本地发送数据,修改预设亮度
            mqttSendServer.sendMQTTMessage(LedConst.LOCAL_TOPIC_ORIPOWER_UPDATE, JSON.toJSONString(preBrightness));
            OriginalPower originalPower = originalPowerDao.selectOriginalPowerByTenantId(headerUtil.tenantId());
            return ResultUtils.success(originalPower.getPreBrightness());
        }
        return ResultUtils.failure();
    }
}
