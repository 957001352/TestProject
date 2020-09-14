package com.dhlk.light.factory.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dhlk.domain.Result;
import com.dhlk.entity.light.OriginalPower;
import com.dhlk.enums.ResultEnum;
import com.dhlk.light.factory.dao.OriginalPowerDao;
import com.dhlk.light.factory.dao.TenantDao;
import com.dhlk.light.factory.mqtt.MqttCloudSender;
import com.dhlk.light.factory.service.OriginalPowerService;
import com.dhlk.light.factory.util.LedConst;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xkliu
 * @date 2020/8/10
 */
@Service
@Transactional
public class OriginalPowerServiceImpl implements OriginalPowerService {


    @Autowired
    private OriginalPowerDao originalPowerDao;

    @Autowired
    private TenantDao tenantDao;

    @Autowired
    private MqttCloudSender mqttCloudSender;

    @Override
    public Result preBrightness(Integer preBrightness) {
        if(CheckUtils.isNull(preBrightness) || !CheckUtils.isNumeric(preBrightness)){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        Integer flag = 0;
        Integer tenantId = tenantDao.findTenantId();
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
                JSONObject json = new JSONObject();
                json.put("tenantId",tenantId);
                json.put("preBrightness",preBrightness);
                mqttCloudSender.sendMQTTMessage(LedConst.CLOUD_TOPIC_ORIPOWER_UPDATE, JSONObject.toJSONString(json));
                return ResultUtils.success(preBrightness);
            }
        }
       return ResultUtils.success();
    }


}
