package com.dhlk.light.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.domain.Result;
import com.dhlk.entity.light.*;
import com.dhlk.light.service.dao.*;
import com.dhlk.light.service.enums.LedEnum;
import com.dhlk.light.service.service.LedParamInfoService;
import com.dhlk.light.service.service.MqttSubsribeService;
import com.dhlk.light.service.util.LedConst;
import com.dhlk.light.service.util.LightDeviceUtil;
import com.dhlk.light.service.websocket.SpringContextHolder;
import com.dhlk.service.RedisService;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.DateUtils;
import com.dhlk.utils.ResultUtils;
import com.dhlk.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/6/9
 */
@Service
@Slf4j
public class MqttSubsribeServiceImpl implements MqttSubsribeService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private LedParamInfoService ledParamInfoService;
    @Autowired
    private LedFaultDao ledFaultDao;
    @Autowired
    private LedDao ledDao;
    @Autowired
    private OriginalPowerDao originalPowerDao;
    @Autowired
    private SyncDataDao syncDataDao;

    @Autowired
    private LightDeviceUtil lightDeviceUtil;

    @Override
    public Result subsribe(String topic, String jsonStr) {
        if (LedConst.CLOUD_TOPIC_FAULT.equals(topic)) {//灯故障信息订阅
            ledFault(jsonStr);
        }else if (LedConst.LOCAL_TOPIC_POWER_DATASYNC.equals(topic)) {//保存能耗汇总结果
            savePowerList(jsonStr);
        } else if (LedConst.LOCAL_TOPIC_ONLINE_DATASYNC.equals(topic)) {//保存灯在线时长汇总结果
            saveOnLineList(jsonStr);
        } else if (LedConst.LOCAL_TOPIC_PEOPLE_FEEL_DATASYNC.equals(topic)) {//保存人感汇总结果
            savePeopleList(jsonStr);
        } else if (LedConst.CLOUD_TOPIC_ORIPOWER_SAVE.equals(topic)) {
            saveOriginalPower(jsonStr);
        } else if (LedConst.CLOUD_TOPIC_ORIPOWER_UPDATE.equals(topic)) {
            updateOriginalPower(jsonStr);
        } else if (LedConst.CLOUD_TOPIC_SYNC_DATA.equals(topic)) {
            //删除失败数据表中的数据
            deleteSyncData(jsonStr);
        } else if (LedConst.CLOUD_TOPIC_LED_VERSION.equals(topic)) {
            //获取灯版本信息同步到数据库
            acquireVersionUpdateParam(jsonStr);

        }
        return ResultUtils.success("mqtt调用feign成功");
    }

    private void acquireVersionUpdateParam(String jsonStr) {
        if(jsonStr != null){
            InfoBox infoBox = JSON.parseObject(jsonStr, InfoBox.class);
            lightDeviceUtil.acquireVersion(infoBox.getSns(),new Version(0),infoBox.getTenantId(),1);
        }

    }

    private void deleteSyncData(String jsonStr) {
        SyncDataResult syncDataResult = JSON.parseObject(jsonStr, SyncDataResult.class);
        syncDataDao.delete(syncDataResult.getId());
    }

    /**
     * 修改订阅的本地亮度设置的数据
     *
     * @param jsonStr
     */
    public void updateOriginalPower(String jsonStr) {
        if (!CheckUtils.isNull(jsonStr)) {
            JSONObject json = JSONObject.parseObject(jsonStr);
            OriginalPower originalPower = JSONObject.toJavaObject(json, OriginalPower.class);
            if(originalPower.getTenantId() != null){
                OriginalPower power = originalPowerDao.selectOriginalPowerByTenantId(originalPower.getTenantId());
                if(power != null){
                    originalPowerDao.setValues(originalPower.getLedCount(),originalPower.getLedOpentime(),originalPower.getLedPower(),originalPower.getTenantId(),originalPower.getPreBrightness(),originalPower.getSystemRunTime());
                }else {
                    originalPowerDao.insert(originalPower);
                }
            }

        }
    }

    /**
     * 保存订阅的的本地亮度设置的数据
     *
     * @param jsonStr
     */
    public void saveOriginalPower(String jsonStr) {
        if (!CheckUtils.isNull(jsonStr)) {
            JSONObject json = JSONObject.parseObject(jsonStr);
            OriginalPower originalPower = JSONObject.toJavaObject(json, OriginalPower.class);
            //本地发送过来OriginalPower数据,根据租户ID先去查询云端有没有这条数据
            if(originalPower.getTenantId() != null){
                OriginalPower power = originalPowerDao.selectOriginalPowerByTenantId(originalPower.getTenantId());
                if(power != null){
                    originalPowerDao.setValues(originalPower.getLedCount(),originalPower.getLedOpentime(),originalPower.getLedPower(),originalPower.getTenantId(),originalPower.getPreBrightness(),originalPower.getSystemRunTime());
                } else {
                    originalPowerDao.insert(originalPower);
                }
            }
        }
    }

    /**
     * 保存能耗汇总结果
     *
     * @param jsonStr
     * @return
     */
    public void savePowerList(String jsonStr) {
        List<LedPower> list = JSONObject.parseArray(jsonStr, LedPower.class);
        if (list != null && list.size() > 0) {
            ledDao.insertPower(list);
        }
    }

    /**
     * 保存灯在线时长汇总结果
     *
     * @param jsonStr
     * @return
     */
    public void saveOnLineList(String jsonStr) {
        List<LedOnline> list = JSONObject.parseArray(jsonStr, LedOnline.class);
        if (list != null && list.size() > 0) {
            ledDao.insertOnlineList(list);
        }
    }

    public void savePeopleList(String jsonStr) {
        List<CloudPeopleFeelStatistics> list = JSONObject.parseArray(jsonStr, CloudPeopleFeelStatistics.class);
        if (list != null && list.size() > 0) {
            ledDao.insertPeopleFell(list);
        }
    }


    /**
     * 订阅故障信息存入redis
     *
     * @param jsonStr
     * @return
     */
    public void ledFault(String jsonStr) {
        JSONObject result = JSONObject.parseObject(jsonStr);
        if (result.get("code").equals("0")) {
            JSONObject data = JSONObject.parseObject(result.get("data").toString());
            /*
             * 先根据灯sn从redis取数据，如果为空，说明是第一次上报异常，则将故障信息插入数据库
             * 如果不为空，可能不是第一次上报，则比对故障代码是否一致，如果不一致说明是新故障，如果一致说明是重复上报
             */
            Object faultCode = redisService.get(LedConst.REDIS_FAULT + data.get("SN"));
            boolean isInsert = false;
            if (faultCode == null) {
                isInsert = true;
            } else {
                //获取redis中已存在灯的故障码，对上报的进行比对，不一致则插入
                if (!data.get("code").toString().equals(faultCode.toString())) {
                    isInsert = true;
                }
            }
            if (isInsert) {
                ledFaultDao.insert(new LedFault(data.get("SN").toString(), data.get("code").toString(), 1));//数据插入故障列表
            }
            redisService.set(LedConst.REDIS_FAULT + data.get("SN"), data.get("code"), LedConst.REDISTTIME);
        }
    }
}