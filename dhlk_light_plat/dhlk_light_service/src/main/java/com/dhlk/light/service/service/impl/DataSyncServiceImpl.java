package com.dhlk.light.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Tenant;
import com.dhlk.entity.basicmodule.User;
import com.dhlk.entity.light.*;
import com.dhlk.light.service.dao.*;
import com.dhlk.light.service.service.DataSyncService;
import com.dhlk.light.service.util.LightDeviceUtil;
import com.dhlk.systemconst.Const;
import com.dhlk.utils.ResultUtils;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xkliu
 * @date 2020/6/18
 */
@Service
@Transactional
public class DataSyncServiceImpl implements DataSyncService {

    @Autowired
    private TenantDao tenantDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private LedDao ledDao;

    @Value("${attachment.path}")
    private String attachmentPath;

    @Autowired
    private SwitchDao switchDao;

    @Autowired
    private LedSwitchDao ledSwitchDao;

    @Autowired
    private TaskSchedulerDao taskSchedulerDao;

    @Autowired
    private TaskSchedulerDetailDao taskSchedulerDetailDao;

    @Autowired
    private TaskSchedulerLogDao taskSchedulerLogDao;

    @Autowired
    private OriginalPowerDao originalPowerDao;

    @Autowired
    private LightDeviceUtil lightDeviceUtil;

    @Autowired
    private SyncDataDao syncDataDao;

    @Override
    public Result sync(String code) {
        Map listMap = new HashMap<>();
        Tenant tenant = tenantDao.findTenantByCode(code);
        List<TaskSchedulerDetail> details = new ArrayList<>();
        //List<TaskSchedulerLog> logs = new ArrayList<>();
        List<LedSwitch> swc = new ArrayList<>();
        List<OriginalPower> originalPowers = new ArrayList<>();
        if (tenant != null && tenant.getId() != null) {
            List<User> users = userDao.findListByTenantId(tenant.getId());
            List<Area> areas = areaDao.findListByTenantId(tenant.getId(), attachmentPath);
            List<Led> leds = ledDao.findListByTenantId(tenant.getId());
            List<Switch> switchs = switchDao.findListByTenantId(tenant.getId());
            OriginalPower power = originalPowerDao.selectOriginalPowerByTenantId(tenant.getId());
            originalPowers.add(power);
            if (switchs != null && switchs.size() > 0) {
                for (Switch sw : switchs) {
                    List<LedSwitch> ledSwitchs = ledSwitchDao.findListBySwitchId(sw.getId());
                    if (ledSwitchs.size() > 0) {
                        swc.addAll(ledSwitchs);
                    }
                }
            }
            List<TaskScheduler> taskSchedulers = taskSchedulerDao.findListByTenantId(tenant.getId());
            if (taskSchedulers != null && taskSchedulers.size() > 0) {
                for (TaskScheduler taskScheduler : taskSchedulers) {
                    List<TaskSchedulerDetail> taskSchedulerDetails = taskSchedulerDetailDao.findListByScheduleId(taskScheduler.getId());
                    if (taskSchedulerDetails.size() > 0) {
                        details.addAll(taskSchedulerDetails);
                    }
//                    List<TaskSchedulerLog> taskSchedulerLogs = taskSchedulerLogDao.findListByScheduleId(taskScheduler.getId());
//                    if (taskSchedulerLogs.size() > 0) {
//                        logs.addAll(taskSchedulerLogs);
//                    }
                }
            }
            listMap.put("tenant", tenant);
            listMap.put("user", users);
            listMap.put("area", areas);
            listMap.put("led", leds);
            listMap.put("switch", switchs);
            listMap.put("ledSwitch", swc);
            listMap.put("taskScheduler", taskSchedulers);
            listMap.put("detail", details);
            listMap.put("originalPower", originalPowers);
            //listMap.put("log", logs);
        }
        return ResultUtils.success(listMap);
    }

    @Override
    public Result syncDataToLocal(SyncDataResult syncDataResult) {
        if(!Const.SYNC_DATA_OPERATE_DELETE.equals(syncDataResult.getOperate())){
            JSONObject jsonObject = JSON.parseObject(syncDataResult.getData() + "");
            syncDataResult.setDataId(jsonObject.getString("id"));
        }
        syncDataDao.insert(syncDataResult);
        try {
            lightDeviceUtil.syncDataToLocal(syncDataResult);
        } catch (MqttException e) {
            return ResultUtils.failure();
        }
        return ResultUtils.success();
    }
}
