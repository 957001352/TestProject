package com.dhlk.light.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.domain.Result;
import com.dhlk.entity.light.*;
import com.dhlk.enums.ResultEnum;
import com.dhlk.light.service.dao.*;
import com.dhlk.light.service.mqtt.MqttSendServer;
import com.dhlk.light.service.service.DataSyncService;
import com.dhlk.light.service.service.LedParamInfoService;
import com.dhlk.light.service.service.LedService;
import com.dhlk.light.service.util.HeaderUtil;
import com.dhlk.light.service.util.LedConst;
import com.dhlk.light.service.util.LightDeviceUtil;
import com.dhlk.service.RedisService;
import com.dhlk.systemconst.Const;
import com.dhlk.util.AuthUserUtil;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.Convert;
import com.dhlk.utils.ResultUtils;
import com.dhlk.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/6/4
 */
@Service
public class LedServiceImpl implements LedService {

    @Autowired
    private LedDao ledDao;
    @Autowired
    private AreaDao areaDao;
    @Autowired
    private LedFaultDao ledFaultDao;
    @Autowired
    private AuthUserUtil authUserUtil;
    @Autowired
    private LedSwitchDao ledSwitchDao;
    @Autowired
    private LightDeviceUtil lightDeviceUtil;
    @Autowired
    private HeaderUtil headerUtil;
    @Autowired
    private LedParamInfoDao ledParamInfoDao;

    @Value("${attachment.path}")
    private String attachmentPath;

    @Autowired
    private MqttSendServer mqttSendServer;
    @Autowired
    private LedParamInfoService ledParamInfoService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private LedRecordDao ledRecordDao;
    @Autowired
    private OriginalPowerDao originalPowerDao;
    @Autowired
    private DataSyncService dataSyncService;

    @Override
    public Result save(Led led) {
        //新增
        Integer flag = 0;
        if (ledDao.findSnIsRepart(led.getSn()) > 0) {
            return ResultUtils.error("灯SN重复！");
        }
        if (CheckUtils.isNull(led.getId())) {
            //设置租户id
            led.setTenantId(authUserUtil.tenantId());
            //查询该租户下是否存在相同sn被删除的灯，如果存在则修改，如果不存在则新增
            Led entity=  ledDao.findLed(led);
            if (entity == null) {
                flag = ledDao.insert(led);
            } else {
                led.setId(entity.getId());
                led.setStatus(0);
                flag = ledDao.update(led);
            }
            if (flag > 0) {
                //添加成功就给灯参数列表添加一条数据
                if (ledParamInfoService.isExistSn(led.getSn()) == null) {
                    LedParamInfo ledParamInfo = new LedParamInfo();
                    ledParamInfo.setSn(led.getSn());
                    ledParamInfoService.save(ledParamInfo);
                }
                //保存成功给本地MQTT发送消息
                mqttSendServer.sendMQTTMessage(LedConst.LOCAL_TOPIC_LED_SAVE,JSON.toJSONString(led));

               /* String jsonStrLed = JSON.toJSONString(led);
                SyncDataResult syncData = new SyncDataResult(Const.LED_TABLE_NAME, jsonStrLed, Const.SYNC_DATA_OPERATE_INSERT, headerUtil.tenantId());
                dataSyncService.syncDataToLocal(syncData);*/
            }
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }
    @Override
    public Result update(Led led) {
        Integer flag = 0;
        if (ledDao.findSnRepart(led.getSn(),led.getId()) > 0) {
            return ResultUtils.error("灯SN重复！");
        }
        led.setTenantId(authUserUtil.tenantId());
        //查询该租户下是否存在相同sn被删除的灯，如果存在则原来的灯物理删除，然后进行修改，如果不存在则直接修改
        Led entity=  ledDao.findLed(led);
        if (entity == null) {
            flag = ledDao.update(led);
        } else {
            ledDao.deleteLed(entity.getId());
            flag = ledDao.update(led);
        }
        if (flag > 0) {
            //修改成功给本地MQTT发送消息
            mqttSendServer.sendMQTTMessage(LedConst.LOCAL_TOPIC_LED_UPDATE,JSON.toJSONString(led));

           /* String jsonStrLed = JSON.toJSONString(led);
            SyncDataResult syncData = new SyncDataResult(Const.LED_TABLE_NAME, jsonStrLed, Const.SYNC_DATA_OPERATE_UPDATE, led.getTenantId());
            dataSyncService.syncDataToLocal(syncData);*/
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }
    @Override
    public Result flashingLed(String sns) {
        if (CheckUtils.isNull(sns)) return ResultUtils.error(ResultEnum.PARAM_ERR);
        lightDeviceUtil.flashingLed(sns);
        return ResultUtils.success("命令已发送");
    }

    @Override
    public Result delete(String id) {
        if (CheckUtils.isNull(id)) {
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        Integer key = Convert.stringToInteger(id);
        if (CheckUtils.isNumeric(id)) {
            if (ledDao.delete(Integer.parseInt(id)) > 0) {
                //逻辑删除成功,给本地MQTT发送消息,修改status = 1
                mqttSendServer.sendMQTTMessage(LedConst.LOCAL_TOPIC_LED_DELETE,id);
             /* String jsonStrLed = JSON.toJSONString(id);
                SyncDataResult syncData = new SyncDataResult(Const.LED_TABLE_NAME, jsonStrLed, Const.SYNC_DATA_OPERATE_DELETE, headerUtil.tenantId());
                dataSyncService.syncDataToLocal(syncData);*/
                return ResultUtils.success();
            }
        }
        return ResultUtils.failure();
    }

    @Override
    public Result findPageList(String sn, String areaId, String switchId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Led> pageInfo = new PageInfo<Led>(ledDao.findList(sn, areaId, switchId, authUserUtil.tenantId()));
        return ResultUtils.success(pageInfo);
    }

    @Override
    public Result findList(String sn, String areaId, String switchId) {
        return ResultUtils.success(ledDao.findList(sn, areaId, switchId, authUserUtil.tenantId()));
    }


    @Override
    public Result savePower(List<LedPower> list) {
        if (ledDao.insertPower(list) > 0) {
            return ResultUtils.success();
        }
        return ResultUtils.failure();
    }

    @Override
    public Result saveOnline(LedOnline ledOnline) {
        if (ledDao.insertOnline(ledOnline) > 0) {
            return ResultUtils.success();
        }
        return ResultUtils.failure();
    }

    @Override
    public Result saveOnlineList(List<LedOnline> list) {
        if (ledDao.insertOnlineList(list) > 0) {
            return ResultUtils.success();
        }
        ;
        return ResultUtils.failure();
    }

    @Override
    @Transactional
    public Result saveSwitchBoundLed(Switch swich) {
        if (CheckUtils.isNull(swich.getId())) {
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        List<Switch> resList = new ArrayList<>();
        if (swich.getLeds() != null && swich.getLeds().size() > 0) {
            List<Switch> diffSns = ledSwitchDao.findGroupIdIsDiffrentBySn(swich.getGroupNo(), swich.getLeds());
            if (diffSns != null && diffSns.size() > 0) {
                for (Switch sn : diffSns) {
                    if (!sn.getGroupNo().equals(swich.getGroupNo())) {
                        resList.add(sn);
                    }
                }
                if (resList != null && resList.size() > 0) {
//                    StringBuffer res=new StringBuffer();
//                    //按灯分组，获取灯sn
//                    Map<String, List<Switch>> ledGroup = resList.stream().collect(Collectors.groupingBy(Switch::getSn));
//                    ledGroup.forEach((k, v) ->{
//                        res.append("灯"+k+"已被组ID是");
//                        //按通道分组，获取开关
//                        Map<Integer, List<Switch>> switchGroup =v.stream().collect(Collectors.groupingBy(Switch::getGroupNo));
//                        switchGroup.forEach((g, s)->{
//                            res.append(g+"的开关"+v.stream().map(Switch::getName).collect(Collectors.joining(","))+"绑定</br>");
//                        });
//                    });
                    return ResultUtils.error("灯" + resList.stream().map(Switch::getSn).collect(Collectors.joining(",")) + "已被不同通道绑定");
                }
            }
        }
        ledSwitchDao.deleteBySwitchId(swich.getId());
        if (swich.getLeds() != null && swich.getLeds().size() > 0) {
            if (ledSwitchDao.saveSwitchLeds(swich.getId(), swich.getGroupId(), swich.getLeds()) > 0) {
                List<String> sns = swich.getLeds().stream().map(t -> t.getSn()).distinct().collect(Collectors.toList());
                //设置等组id
                lightDeviceUtil.setLedGrpId(String.join(",", sns), swich.getGroupNo());
            }
        }
        ;
        return ResultUtils.success();
    }

    @Override
    public Result setLedBrightness(String sns, String brightness) {
        if (CheckUtils.isNull(sns) || !CheckUtils.isNumeric(brightness)) return ResultUtils.error(ResultEnum.PARAM_ERR);
        if (redisService.hasKey(LedConst.REDIS_RECORD_REFRESH_SETLIGHT_TIME_ + headerUtil.cloudToken())) {
            return ResultUtils.error("操作间隔不得小于5秒");
        }
        //增加给系统配置表添加亮度值的逻辑
        Integer flag = 0;
        Integer tenantId = headerUtil.tenantId();
        OriginalPower power = originalPowerDao.selectOriginalPowerByTenantId(tenantId);
        if (power == null) {
            OriginalPower originalPower = new OriginalPower();
            originalPower.setTenantId(tenantId);
            originalPower.setPreBrightness(Integer.valueOf(brightness));
            flag = originalPowerDao.insert(originalPower);
            //给本地发送数据,保存预设亮度
            if (flag > 0) {
                String jsonStrOrig = JSON.toJSONString(originalPower);
                SyncDataResult syncData = new SyncDataResult(Const.ORIGINALPOWER_TABLE_NAME, jsonStrOrig, Const.SYNC_DATA_OPERATE_INSERT, tenantId);
                dataSyncService.syncDataToLocal(syncData);
            }
        } else {
            flag = originalPowerDao.updateOriginalPower(Integer.valueOf(brightness), tenantId);
            if (flag > 0) {
                //给本地发送数据,修改预设亮度
                power.setPreBrightness(Integer.valueOf(brightness));
                power.setTenantId(tenantId);
                String jsonStrPower = JSON.toJSONString(power);
                SyncDataResult syncData = new SyncDataResult(Const.ORIGINALPOWER_TABLE_NAME, jsonStrPower, Const.SYNC_DATA_OPERATE_UPDATE, tenantId);
                dataSyncService.syncDataToLocal(syncData);
            }
        }

        if (CheckUtils.isIntNumeric(brightness) && Integer.parseInt(brightness) >= 0) {
            lightDeviceUtil.ledBrightness(sns, Integer.parseInt(brightness));
            redisService.set(LedConst.REDIS_RECORD_REFRESH_SETLIGHT_TIME_ + headerUtil.cloudToken(), brightness, LedConst.BANTIME);
            return ResultUtils.success("命令已发送");
        }
        return ResultUtils.error(ResultEnum.PARAM_ERR);
    }

    @Override
    public Result findAreasByLed() {
        List<Area> list = areaDao.findAreasByLed(headerUtil.tenantId(), attachmentPath);
        return ResultUtils.success(list);
    }

    @Override
    public Result openOrCloseLed(String sns, String status) {
        if (CheckUtils.isNull(sns) || !CheckUtils.isNumeric(status)) return ResultUtils.error(ResultEnum.PARAM_ERR);
        if (redisService.hasKey(LedConst.REDIS_RECORD_REFRESH_ONOFF_TIME_ + headerUtil.cloudToken())) {
            return ResultUtils.error("操作间隔不得小于5秒");
        }
        lightDeviceUtil.ledOnOrOff(sns, status);
        redisService.set(LedConst.REDIS_RECORD_REFRESH_ONOFF_TIME_ + headerUtil.cloudToken(), status, LedConst.BANTIME);
        return ResultUtils.success("命令已发送");
    }

    @Override
    public Result ledRestart(String sns) {
        if (CheckUtils.isNull(sns)) return ResultUtils.error(ResultEnum.PARAM_ERR);
        lightDeviceUtil.ledRestart(sns);
        return ResultUtils.success("命令已发送");
    }

    @Override
    public Result switchRestart(String sns) {
        if (CheckUtils.isNull(sns)) return ResultUtils.error(ResultEnum.PARAM_ERR);
        lightDeviceUtil.switchRestart(sns);
        return ResultUtils.success("命令已发送");
    }

    @Override
    public Result findLedFault(String ledSn) {
        return ResultUtils.success(ledFaultDao.findList(ledSn));
    }

    @Override
    public Result findExportList(String ledSn) {
        return ResultUtils.success(ledFaultDao.findExportList(ledSn));
    }

    @Override
    public Result findListByTenantId(Integer tenantId) {
        List<Led> leds = ledDao.findListByTenantId(tenantId);
        return ResultUtils.success(leds);
    }



    @Override
    public Result findLedParamInfos(String sns) {
        if (CheckUtils.isNull(sns)) return ResultUtils.error(ResultEnum.PARAM_ERR);
        if (CheckUtils.isNull(sns)) {
            return ResultUtils.success(ledParamInfoDao.findList(null));
        }
        String[] split = sns.split(",");
        return ResultUtils.success(ledParamInfoDao.findList(split));
    }

    @Override
    public Result findLedRecord(String sn, String commond, String sendResult, String backResult, String startTime, String endTime, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return ResultUtils.success(new PageInfo<LedRecord>(ledRecordDao.findList(headerUtil.tenantId(), sn, commond, sendResult, backResult, startTime, endTime)));
    }


    @Override
    public Result notAddLed() {
        //从redis中获取的数据集合
        List<String> lists = new ArrayList<>();
        Set<String> keys = redisService.keys(LedConst.REDIS_POWER + "*");
        if (CollectionUtils.isEmpty(keys)) {
            return ResultUtils.error("redis key 为null");
        }
        for (String key : keys) {
            String str = (String) redisService.get(key);
            JSONObject json = JSONObject.parseObject(str);
            LedPower ledPower = JSONObject.toJavaObject(json, LedPower.class);
            //把有sn的数据才放到list集合中
            if (!StringUtils.isEmpty(ledPower.getLedSn())) {
                lists.add(ledPower.getLedSn());
            }
        }
        //获取所有灯的sn号
        List<String> leds = ledDao.findSnAll();
        if (CollectionUtils.isEmpty(leds)) {
            return ResultUtils.success(lists);
        }
        if (CollectionUtils.isEmpty(lists)) {
            return ResultUtils.success(lists);
        }
        lists.removeAll(leds);
        return ResultUtils.success(lists);
    }

    @Override
    public Result brightnessShow() {
        OriginalPower originalPower = originalPowerDao.selectOriginalPowerByTenantId(headerUtil.tenantId());
        if (originalPower != null) {
            return ResultUtils.success(originalPower.getPreBrightness());
        }
        return ResultUtils.success(null);
    }

    @Override
    public Result findLedsByArea(String areaId) {
        List<Led> leds = ledDao.findLedsByArea(areaId);
        //查询灯开关状态
        if (leds != null && leds.size() > 0) {
            for (Led led : leds) {
                Object power = redisService.get(LedConst.REDIS_POWER + led.getSn());
                if (power == null) {
                    led.setStatus(-1);//没有上报能耗信息就认为是离线状态
                } else {
                    JSONObject obj = JSONObject.parseObject(power.toString());
                    if (obj != null && obj.get("status") != null) {
                        led.setStatus(Integer.parseInt(obj.get("status").toString()));
                    }
                }
            }
        }
        return ResultUtils.success(leds);
    }


}