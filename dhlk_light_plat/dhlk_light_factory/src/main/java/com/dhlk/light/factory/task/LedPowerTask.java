package com.dhlk.light.factory.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.entity.basicmodule.Tenant;
import com.dhlk.entity.light.LedPower;
import com.dhlk.light.factory.dao.TenantDao;
import com.dhlk.light.factory.mqtt.MqttCloudSender;
import com.dhlk.light.factory.service.LedPowerService;
import com.dhlk.light.factory.util.LedConst;
import com.dhlk.service.RedisService;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
@EnableScheduling
public class LedPowerTask {
    @Autowired
    private RedisService redisService;
    @Value("${mqttcloud.saveHdfs}")
    private boolean saveHdfs;
    @Autowired
    private TenantDao tenantDao;
    @Autowired
    private MqttCloudSender mqttCloudSender;

    //灯能耗
    private List<LedPower> ledPowerList = new ArrayList<>();


    //原时间戳
    private Long orTimestamp = System.currentTimeMillis();

    @Autowired
    private LedPowerService ledPowerService;

    @Scheduled(cron = "*/5 * * * * ? ")
    public void ledPowerToCloud() {
        if (redisService.hasKey(LedConst.REDIS_MQTT_LED_POWER)) {
            Set<Object> hkeys = redisService.hkeys(LedConst.REDIS_MQTT_LED_POWER);
            for (Object snObj : hkeys) {
                JSONObject result = JSON.parseObject(redisService.hget(LedConst.REDIS_MQTT_LED_POWER, snObj + "") + "");
                redisService.hdel(LedConst.REDIS_MQTT_LED_POWER, snObj);
                LedPower lightDataModel = new LedPower(result);
                //能耗是0的不存库
                if (lightDataModel != null && lightDataModel.getEnergy() != null && lightDataModel.getEnergy().compareTo(BigDecimal.ZERO) > 0) {
                    ledPowerList.add(lightDataModel);
                }
                if (!redisService.hasKeyAndItem(LedConst.REDIS_RECORDING_LOG, result.get("SN").toString())) {
                    redisService.hset(LedConst.REDIS_RECORDING_LOG, String.valueOf(result.get("SN")), "0");
                }
                //设置本地租户id
                if (redisService.get(LedConst.REDIS_TENANTID) == null) {
                    Integer tenantId = tenantDao.findTenantId();
                    if (CheckUtils.isNull(tenantId)) {
                        redisService.set(LedConst.REDIS_TENANTID, -1, 60);
                    } else {
                        redisService.set(LedConst.REDIS_TENANTID, tenantId);
                    }
                }
                //给云端mqtt发送能耗数据
                result.put("tenantId", redisService.get(LedConst.REDIS_TENANTID));
                log.info("-------------->"+redisService.get(LedConst.REDIS_MQTTISRIGHT));
                if(redisService.get(LedConst.REDIS_MQTTISRIGHT)!=null&&"0".equals(redisService.get(LedConst.REDIS_MQTTISRIGHT).toString())){
                    log.info("============>");
                    localMqttToCloudMqtt(JSONObject.toJSONString(result));
                    //给云端mqtt发送消息，存储hdfs
                    if (saveHdfs) {
                        if (redisService.get(LedConst.REDIS_MQTTISRIGHT) != null && "0".equals(redisService.get(LedConst.REDIS_MQTTISRIGHT).toString())) {
                            sentDataToHdfs(result);
                        }
                    }
                }
                log.info(">>>>>>>>>>>>>>");
            }
            //现时间减去原来时间等于1分钟
            //if (DateUtils.getTimeDifference(System.currentTimeMillis(), orTimestamp) >= 1) {
            if (((System.currentTimeMillis() - orTimestamp) / 1000 % 8) == 0) {
                if (ledPowerList != null && ledPowerList.size() > 0) {
                    //批量插入存入数据库
                    ledPowerService.saveLedPower(ledPowerList);
                    //清空已存入数据库的集合
                    ledPowerList.clear();
                    //时间复位
                    orTimestamp = System.currentTimeMillis();
                }
            }
        }
    }

    /**
     * 将订阅到的mqtt的数据发往云端的mqtt
     *
     * @param jsonStr 订阅到的数据
     */
    public void localMqttToCloudMqtt(String jsonStr) {
        mqttCloudSender.sendMQTTMessage(LedConst.TOPIC_LOCALTOCLOUD, jsonStr);
    }


    /**
     * 给云端hdfs发送数据
     *
     * @param
     * @return
     */
    private void sentDataToHdfs(JSONObject result) {
        if (result != null) {
            Object tenantCode = null;
            //从redis获取租户code，如果获取不到，从数据库查询
            if (redisService.get(LedConst.REDIS_TENANTCOUD) == null) {
                Tenant tenant = tenantDao.findTenantByCode(null);
                if (tenant != null) {
                    tenantCode = tenant.getCode();
                    redisService.set(LedConst.REDIS_TENANTCOUD, tenantCode);
                }
            } else {
                tenantCode = redisService.get(LedConst.REDIS_TENANTCOUD);
            }
            if(result.get("data")!=null){
                JSONObject data=JSONObject.parseObject(result.get("data").toString());
                data.put("SN",result.get("SN"));
                data.put("create_time", DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("factoryCode", tenantCode);
                jsonObject.put("after", data);
                mqttCloudSender.sendMQTTMessage(LedConst.TOPIC_LOCALTOHDFS, JSONObject.toJSONString(jsonObject));
            }
        }
    }
}
