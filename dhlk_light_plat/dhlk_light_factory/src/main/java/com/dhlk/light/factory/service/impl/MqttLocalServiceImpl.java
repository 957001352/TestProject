package com.dhlk.light.factory.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.entity.light.LedPower;
import com.dhlk.entity.light.LocalPeopleFeelStatistics;
import com.dhlk.light.factory.dao.TenantDao;
import com.dhlk.light.factory.enums.LedEnum;
import com.dhlk.light.factory.mqtt.MqttCloudSender;
import com.dhlk.light.factory.service.LedPowerService;
import com.dhlk.light.factory.service.MqttLocalService;
import com.dhlk.light.factory.util.LedConst;
import com.dhlk.light.factory.util.SchedulerResult;
import com.dhlk.service.RedisService;
import com.dhlk.systemconst.Const;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.HttpClientUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/6/9
 */
@Service
public class MqttLocalServiceImpl implements MqttLocalService {
    private Logger log = LoggerFactory.getLogger(MqttCloudSender.class);
    @Autowired
    private RedisService redisService;

    @Autowired
    private LedPowerService ledPowerService;

    @Autowired
    private MqttCloudSender mqttCloudSender;

    @Autowired
    private TenantDao tenantDao;


    @Value("${cloud.baseUrl}")
    private String cloudBaseUrl;

    @Value("${lighting.lineTime}")
    private Integer lineTime;

    @Override
    public void subsribe(String topic, String jsonStr) {
        Boolean bl=false;
        if (topic.contains(LedConst.HARDTOLOCAL_TOPIC_BILIGHT)) {
            //灯数据订阅
            JSONObject result=null;
            Integer comType= null;
            try {
                result = JSONObject.parseObject(jsonStr);
                comType = result.getInteger("cmd_type");
                bl=true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(bl&&CheckUtils.isNumeric(comType)){
                if (comType==LedEnum.POWER.getState()) {//订阅灯电能数据
                    ledPower(result);
                } else if (comType==LedEnum.PERSONFELL.getState()) {//订阅人感数据
                    peopleFell(jsonStr);
                } else if (comType==LedEnum.LIGHTFELL.getState()) {//订阅光感数据
                    lightFell(jsonStr);
                } else if (comType==LedEnum.LIGHTFELLRETURN.getState()) {//订阅设置光感返回数据
                    localMqttToCloudMqtt(jsonStr);
                    updateLightFellOnOff(result);//更改人感状态
                } else if (comType==LedEnum.PEOPLEFELLRETURN.getState()) {//订阅设置人感返回数据
                    localMqttToCloudMqtt(jsonStr);
                    updatePeopleFellOnOff(result);//更改人感状态
                } else if (comType==LedEnum.WIFIRETURN.getState()) {//订阅设置wifi返回数据
                    localMqttToCloudMqtt(jsonStr);
                } else if (comType==LedEnum.VERSIONRETURN.getState()) {//订阅获取版本返回数据
                    localMqttToCloudMqtt(jsonStr);
                } else if (comType==LedEnum.BACKRESULT.getState()) {//返回发送命令执行结果
                    mqttCommandBackToRedis(jsonStr);
                }
            }
        }
    }
    /**
     * 返回发送命令执行结果
     * @param jsonStr
     * @return
     */
    public void mqttCommandBackToRedis(String jsonStr) {
        JSONObject result = JSONObject.parseObject(jsonStr);
        if(result!=null){
            //发送给云端
            localMqttToCloudMqtt(jsonStr);
            JSONObject data=JSONObject.parseObject(result.get("data").toString());
            if(data!=null){
                //灯重启返回值中没有时间戳
                if(LedEnum.RESTART.getState().equals(data.getInteger("cmd_type"))){
                    redisService.set(LedConst.REDIS_SETVERSION_RETURN+result.get("SN")+"_",data.get("cmd_result"),60);
                    redisService.set(result.get("SN")+"_"+result.get("cmd_type"),data.get("cmd_result"),3);
                }else{
                    redisService.set(result.get("SN")+"_"+result.get("ts"),data.get("cmd_result"),3);
                }
            }
        }
    }

    /**
    * 设置人感开关状态
     * @param result
    * @return
    */
   private void updatePeopleFellOnOff( JSONObject result){
       if(result!=null){
           JSONObject data = JSONObject.parseObject(result.get("data").toString());
           if (data != null) {
               redisService.set(LedConst.REDIS_PEOPLEONOFF+result.get("SN"),data.get("on_off").toString());  //增加30秒
               //开人感，关光感
               if(redisService.hasKey(LedConst.REDIS_LIGHTFELL + result.get("SN"))){
                   redisService.del(LedConst.REDIS_LIGHTFELL + result.get("SN"));
               }
           }
       }
   }
    /**
     * 设置光感开关状态
     * @param result
     * @return
     */
    private void updateLightFellOnOff( JSONObject result){
        if(result!=null){
            String sn=result.getString("SN");
            //开光感，关人感
            if(redisService.hasKey(LedConst.REDIS_PEOPLEONOFF + sn)){
                redisService.del(LedConst.REDIS_PEOPLEONOFF + sn);//人感开关状态
            }
        }
    }
    /**
     * 将订阅到的mqtt的数据发往云端的mqtt
     *
     * @param jsonStr 订阅到的数据
     */
    public void localMqttToCloudMqtt(String jsonStr) {
        if(redisService.get(LedConst.REDIS_MQTTISRIGHT)!=null&&"0".equals(redisService.get(LedConst.REDIS_MQTTISRIGHT).toString())){
            mqttCloudSender.sendMQTTMessage(LedConst.TOPIC_LOCALTOCLOUD, jsonStr);
        }
    }


    /**
     * 订阅云端开关定时任务信息，达到本地定时任务开启/关闭
     *
     * @return
     */
/*    public void schedulerOnOff(String jsonStr) {
        SchedulerResult schedulerResult = JSON.parseObject(jsonStr, SchedulerResult.class);
        if (schedulerResult.getStatus() == 0) {
            taskSchedulerService.startTaskScheduler(schedulerResult.getSchedulerId());
        } else {
            taskSchedulerService.stopTaskScheduler(schedulerResult.getSchedulerId());
        }
        updateCloudLedStatus(schedulerResult);
    }*/

    //修改云端定时任务状态
    public void updateCloudLedStatus(SchedulerResult schedulerResult) {
        try {
            Map params = new HashMap();
            params.put("schedulerId", schedulerResult.getSchedulerId().toString());
            params.put("status", schedulerResult.getStatus().toString());
            Map headers = new HashMap();
            headers.put(Const.TOKEN_HEADER, schedulerResult.getToken());
            HttpClientUtils.doGet(cloudBaseUrl + "/scheduler/updateSchedulerStatus", headers, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 将订阅(本地MQTT)灯的能耗数据存入数据库，并将实时数据发送给云MQTT
     * @param result
     * @return
     */
    public void ledPower(JSONObject result) {
        redisService.hset(LedConst.REDIS_MQTT_LED_POWER, result.getString("SN"), JSON.toJSONString(result));
        LedPower lightDataModel = new LedPower(result);
        redisService.set(LedConst.REDIS_POWER + result.get("SN"), JSON.toJSONString(lightDataModel), lineTime);//增加30秒
    }





    /**
     * 订阅人感状态
     * @param jsonStr
     * @return
     */
    public void peopleFell(String jsonStr) {
        // 格式化时间输出
        JSONObject result = JSONObject.parseObject(jsonStr);
        JSONObject data = JSONObject.parseObject(result.get("data").toString());
        if (data != null) {
            //1表示人感有人
            if("1".equals(data.get("CM").toString())){
                LocalPeopleFeelStatistics localpfs = new LocalPeopleFeelStatistics();
                localpfs.setLedSn(result.get("SN").toString());
                localpfs.setStatus(Integer.parseInt(data.get("CM").toString()));
                Integer tenantId = tenantDao.findTenantId();
                if(tenantId == null || tenantId == 0){
                    tenantId = -1;
                }
                localpfs.setTenantId(tenantId);
                ledPowerService.saveLocalPeopleFeel(localpfs);
            }
            redisService.set(LedConst.REDIS_PEOPLEFELL + result.get("SN"), data.get("CM").toString(), LedConst.REDISTTIME);  //增加30秒
        }
        localMqttToCloudMqtt(jsonStr);
    }
    /**
     * 订阅光感
     * @param jsonStr
     * @return
     */
    public void lightFell(String jsonStr) {
        // 格式化时间输出
        JSONObject result = JSONObject.parseObject(jsonStr);
        if(result!=null){
            JSONObject data = JSONObject.parseObject(result.get("data").toString());
            if (data != null) {
                redisService.set(LedConst.REDIS_LIGHTFELL+result.get("SN"),JSON.toJSONString(data.get("shine")), LedConst.REDISTTIME);  //增加30秒
            }
        }
        localMqttToCloudMqtt(jsonStr);
    }
}