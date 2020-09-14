package com.dhlk.light.factory.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dhlk.domain.Result;
import com.dhlk.entity.light.*;
import com.dhlk.light.factory.dao.AreaDao;
import com.dhlk.light.factory.dao.LedDao;
import com.dhlk.light.factory.dao.LedRecordDao;
import com.dhlk.light.factory.dao.OriginalPowerDao;
import com.dhlk.light.factory.mqtt.MqttCloudSender;
import com.dhlk.light.factory.service.LedService;
import com.dhlk.light.factory.util.HeaderUtil;
import com.dhlk.light.factory.util.LedConst;
import com.dhlk.light.factory.util.LightDeviceUtil;
import com.dhlk.service.RedisService;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.DateUtils;
import com.dhlk.utils.ResultUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/6/17
 */
@Service
public class LedServiceImpl implements LedService {
    @Autowired
    private LightDeviceUtil lightDeviceUtil;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AreaDao areaDao;
    @Value("${task.date}")
    private Integer dayNum;

    @Value("${attachment.path}")
    private String attachmentPath;


    @Autowired
    private LedDao ledDao;


    @Autowired
    private LedRecordDao ledRecordDao;
    @Autowired
    private HeaderUtil headerUtil;

    @Autowired
    private OriginalPowerDao originalPowerDao;
    @Autowired
    private MqttCloudSender mqttCloudSender;

    @Override
    public Result openOrCloseLed(String sns, Integer status) {
        if(CheckUtils.isNull(sns) || !CheckUtils.isNumeric(status)) return ResultUtils.error("参数错误");
        if (redisService.hasKey(LedConst.REDIS_RECORD_REFRESH_ONOFF_TIME_+headerUtil.cloudToken())) {
            return ResultUtils.error("操作间隔不得小于5秒");
        }
        lightDeviceUtil.ledOnOrOff(sns,status,true);//开关灯
        redisService.set(LedConst.REDIS_RECORD_REFRESH_ONOFF_TIME_+headerUtil.cloudToken(), status, LedConst.BANTIME);
        return ResultUtils.success("命令已发送");
    }
    @Override
    public Result setLedBrightness(String sns, String brightness) {
        if(CheckUtils.isNull(sns) || !CheckUtils.isNumeric(brightness)) return ResultUtils.error("参数错误");
        lightDeviceUtil.ledBrightness(sns,Integer.parseInt(brightness),true);
        //增加本地设置亮度值给云端发数据
        Integer flag = 0;
        Integer tenantId = headerUtil.tenantId();
        Integer preBrightness = Integer.valueOf(brightness);
        OriginalPower originalPower = originalPowerDao.selectOriginalPowerByTenantId(tenantId);
        if(originalPower == null){
            OriginalPower power = new OriginalPower();
            power.setPreBrightness(preBrightness);
            power.setTenantId(tenantId);
            flag = originalPowerDao.insert(power);
            //给云端发送保存的主题
            if(flag > 0){
                mqttCloudSender.sendMQTTMessage(LedConst.CLOUD_TOPIC_ORIPOWER_SAVE, JSONObject.toJSONString(power));
                return ResultUtils.success(power.getPreBrightness());
            }
        }else{
            flag = originalPowerDao.updateOriginalPower(preBrightness,tenantId,null);
            //给云端发送修改的主题
            if(flag > 0){
                originalPower.setPreBrightness(preBrightness);
                mqttCloudSender.sendMQTTMessage(LedConst.CLOUD_TOPIC_ORIPOWER_UPDATE, JSONObject.toJSONString(originalPower));
                return ResultUtils.success(preBrightness);
            }
        }
        return ResultUtils.success("命令已发送");
    }

    @Override
    public Result ledRestart(String sns) {
        if(CheckUtils.isNull(sns)) return ResultUtils.error("参数错误");
        lightDeviceUtil.ledRestart(sns);
        return ResultUtils.success("命令已发送");
    }

    @Override
    public Result setLedGrpId(String sns, Integer grpId) {
        if(CheckUtils.isNull(sns) || !CheckUtils.isNumeric(grpId)) return ResultUtils.error("参数错误");
        lightDeviceUtil.setLedGrpId(sns,grpId);
        return ResultUtils.success("命令已发送");
    }

    @Override
    public Result flashingLed(String sns) {
        if(CheckUtils.isNull(sns)) return ResultUtils.error("参数错误");
        lightDeviceUtil.flashingLed(sns);
        return ResultUtils.success("命令已发送");
    }

    @Override
    public Result findAreasByLed() {
        List<Area> list = areaDao.findAreasByLed(null, attachmentPath);
        return ResultUtils.success(list);
    }

    @Override
    public Result findList(String sn, String areaId, String switchId) {
        return ResultUtils.success(ledDao.findList(sn, areaId, switchId, null));
    }


    @Override
    public Result lightSensationContro(String sns, IntensityInfo intensityInfo) {
        return ResultUtils.success("命令已发送");
    }

    @Override
    public Result peopleFeelContro(String sns, PeopleFeelInfo peopleFeelInfo) {
        return ResultUtils.success("命令已发送");
    }

    @Override
    public Result wifiContro(String sns, LedWifiInfo ledWifiInfo) {
        return ResultUtils.success("命令已发送");
    }

    @Override
    public Result loadWifiConfig(String sns) {
        return null;
    }

    @Override
    public Result findLedRecord(String sn, String commond, String sendResult, String backResult, String startTime, String endTime, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return ResultUtils.success(new PageInfo<LedRecord>(ledRecordDao.findList(sn, commond, sendResult, backResult, startTime, endTime)));
    }

    /**
     * 每天凌晨删除一周前数据  dayNum(当前时间往前推几天)
     */
    @Scheduled(cron = "0 0 0 */1 * ?")
    public void delete(){
        String date = DateUtils.getSubtractDate(dayNum,"yyyy-MM-dd");
        ledRecordDao.delete(date);
    }

    @Override
    public Result brightnessShow() {
        OriginalPower originalPower = originalPowerDao.selectOriginalPowerByTenantId(headerUtil.tenantId());
        if(originalPower != null){
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