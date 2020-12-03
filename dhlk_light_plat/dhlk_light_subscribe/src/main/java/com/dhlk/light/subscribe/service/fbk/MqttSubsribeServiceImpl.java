package com.dhlk.light.subscribe.service.fbk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.entity.light.IntensityInfo;
import com.dhlk.entity.light.LedPower;
import com.dhlk.entity.light.LedWifiInfo;
import com.dhlk.entity.light.PeopleFeelInfo;
import com.dhlk.light.subscribe.enums.LedEnum;
import com.dhlk.light.subscribe.service.MqttSubsribeService;
import com.dhlk.light.subscribe.service.RedisService;
import com.dhlk.light.subscribe.util.LedConst;
import com.dhlk.utils.CheckUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import java.util.HashMap;

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
    @Value("${lighting.lineTime}")
    private Integer lineTime;
    @Override
    public void subsribe(String topic, String jsonStr) {
            JSONObject result = JSONObject.parseObject(jsonStr);
            if (result.getInteger("cmd_type").equals(LedEnum.LIGHTFELLRETURN.getState())) {//订阅设置光感返回数据
                mqttToRedis(LedConst.REDIS_LIGHTFELL_RETURN, LedEnum.LIGHTFELLRETURN, jsonStr);
            } else if (result.getInteger("cmd_type").equals(LedEnum.PEOPLEFELLRETURN.getState())) {//订阅设置人感返回数据
                mqttToRedis(LedConst.REDIS_PEOPLEFELL_RETURN, LedEnum.PEOPLEFELLRETURN, jsonStr);
                //更改人感开关状态
                updatePeopleFellOnOff(result);
            } else if (result.getInteger("cmd_type").equals(LedEnum.WIFIRETURN.getState())) {//订阅设置wifi返回数据
                if (result.get("dev_type").equals(LedEnum.DEVLIGHT.getState())) {
                    mqttToRedis(LedConst.REDIS_WIFI_RETURN, LedEnum.WIFIRETURN, jsonStr);
                } else if (result.getInteger("cmd_type").equals(LedEnum.DEVSWITCH.getState())) {
                    mqttToRedis(LedConst.REDIS_WIFI_RETURN, jsonStr);
                }
            } else if (result.getInteger("cmd_type").equals(LedEnum.POWER.getState())) {//订阅灯电能数据
                ledPower(result);
            } else if (result.getInteger("cmd_type").equals(LedEnum.PERSONFELL.getState())) {//订阅人感数据
                peopleFell(jsonStr);
            } else if (result.getInteger("cmd_type").equals(LedEnum.LIGHTFELL.getState())) {//订阅光感数据
                lightFell(jsonStr);
            } else if (result.getInteger("cmd_type").equals(LedEnum.FIRMWAREUPDATERETURN.getState())) {//订阅固件升级
                mqttToRedis(LedConst.REDIS_FIRMWARE_UPDATE_RETURN, jsonStr);
            } else if (result.getInteger("cmd_type").equals(LedEnum.VERSIONRETURN.getState())) {//订阅获取版本回复
                mqttToRedis(LedConst.REDIS_VERSION_RETURN, LedEnum.VERSIONRETURN, jsonStr);
            } else if (result.getInteger("cmd_type").equals(LedEnum.BACKRESULT.getState())) {//返回发送命令执行结果
                mqttCommandBackToRedis(jsonStr);
            }
    }

    /**
     * 返回发送命令执行结果
     *
     * @param jsonStr
     * @return
     */
    public void mqttCommandBackToRedis(String jsonStr) {
        JSONObject result = JSONObject.parseObject(jsonStr);
        if (result != null) {
            JSONObject data = JSONObject.parseObject(result.get("data").toString());
            if (data != null) {
                //灯重启返回值中没有时间戳
                if (data.get("cmd_type").toString().equals(LedEnum.RESTART.getState().toString())) {
                    redisService.set(result.get("SN") + "_" + result.get("cmd_type"), data.get("cmd_result"), 5);
                } else {
                    redisService.set(result.get("SN") + "_" + result.get("ts"), data.get("cmd_result"), 5);
                }
            }
        }
    }
    /**
     * 设置人感开关状态
     * @param result
     * @return
     */
    private void updatePeopleFellOnOff(JSONObject result){
        if(result!=null){
            JSONObject data = JSONObject.parseObject(result.get("data").toString());
            if (data != null) {
                redisService.set(LedConst.REDIS_PEOPLEONOFF+result.get("SN"),data.get("on_off").toString());  //增加30秒
            }
        }
    }

    @Transactional
    public void mqttToRedis(String rediskey, LedEnum ledEnum, String jsonStr) {
        if (!CheckUtils.isNull(jsonStr)) {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            if (jsonObject.get("data") != null) {
                redisService.set(rediskey+jsonObject.get("SN") + "_" + jsonObject.get("ts"), JSON.toJSONString(jsonObject.get("data")),LedConst.REDISTIME_RETURNDATA);
            }
        }
    }

    /**
     * 将订阅到的mqtt的数据一一对应的存入redis里
     *
     * @param jsonStr  订阅到的数据
     * @param rediskey redis的key值
     */
    public void mqttToRedis(String rediskey, String jsonStr) {
        if (!CheckUtils.isNull(jsonStr)) {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            if (jsonObject.get("data") != null) {
                redisService.set(rediskey + jsonObject.get("SN"), JSON.toJSONString(jsonObject.get("data")), LedConst.REDISTTIME);
            }
        }
    }



    /**
     * 将订阅辉的能耗数据存入redis
     * @return
     */
    public void ledPower(JSONObject result) {
        if (result != null) {
            Integer tenantId = result.getInteger("tenantId");
            LedPower lightDataModel = new LedPower(result);
            String sn=result.getString("SN");
            redisService.set(LedConst.REDIS_POWER + sn, JSONObject.toJSONString(lightDataModel),lineTime);
            //记录灯首次上线的sn
            if (!CheckUtils.isNull(tenantId)) {
                if (!redisService.hasKeyAndItem(LedConst.REDIS_LED_PARAM_INFO, sn)) {
                    HashMap map = new HashMap();
                    map.put("status", 0);
                    map.put("tenantId", tenantId);
                    changeParamStatus(sn, JSON.toJSONString(map));
                }
            }
        }
    }

    /**
     * 将参数队列状态存入redis
     */
    public void changeParamStatus(String sn, String status) {
        redisService.hset(LedConst.REDIS_LED_PARAM_INFO, sn, status);
    }

    /**
     * 订阅人感状态
     *
     * @param jsonStr
     * @return
     */
    public void peopleFell(String jsonStr) {
        // 格式化时间输出
        JSONObject result = JSONObject.parseObject(jsonStr);
        if (result != null) {
            JSONObject data = JSONObject.parseObject(result.get("data").toString());
            if (data != null) {
                redisService.set(LedConst.REDIS_PEOPLEFELL + result.get("SN"), data.get("CM").toString(), LedConst.REDISTTIME);  //增加30秒
            }
        }
    }

    /**
     * 订阅光感
     *
     * @param jsonStr
     * @return
     */
    public void lightFell(String jsonStr) {
        // 格式化时间输出
        JSONObject result = JSONObject.parseObject(jsonStr);
        if (result != null) {
            JSONObject data = JSONObject.parseObject(result.get("data").toString());
            if (data != null) {
                redisService.set(LedConst.REDIS_LIGHTFELL + result.get("SN"), JSON.toJSONString(data.get("shine")), LedConst.REDISTTIME);  //增加30秒
            }
        }
    }
}