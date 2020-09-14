package com.dhlk.light.service.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.LedVersionInfo;
import com.dhlk.enums.ResultEnum;
import com.dhlk.light.service.dao.LedVersionInfoDao;
import com.dhlk.light.service.service.LedVersionInfoService;
import com.dhlk.light.service.util.HeaderUtil;
import com.dhlk.light.service.util.LedConst;
import com.dhlk.light.service.util.LightDeviceUtil;
import com.dhlk.service.RedisService;
import com.dhlk.util.AuthUserUtil;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @program: dhlk.light.plat
 *
 * @description: 灯版本信息service
 *
 * @author: wqiang
 *
 * @create: 2020-06-30 11:20
 **/

@Service
public class LedVersionInfoServiceImpl implements LedVersionInfoService {

    @Autowired
    private LedVersionInfoDao ledVersionInfoDao;
    @Autowired
    private LightDeviceUtil lightDeviceUtil;
    @Autowired
    private AuthUserUtil authUserUtil;
    @Autowired
    private RedisService redisService;
    @Autowired
    private HeaderUtil headerUtil;

    @Override
    public Result save(LedVersionInfo ledVersionInfo) {

        JSONObject jsonObject = authUserUtil.currentUser();
        ledVersionInfo.setCreator(jsonObject.get("name").toString());

        List<LedVersionInfo> list = null;
        int flag = 0;

        //id为空新增，存在更新
        if(CheckUtils.isNull(ledVersionInfo.getId())){
            list = ledVersionInfoDao.findList(ledVersionInfo.getName(), "", "", ledVersionInfo.getVersion(),0,ledVersionInfo.getId());
            //判断版本名称、版本号是否存在
            if(list != null && list.size()>0){
                return ResultUtils.error("版本名称："+ledVersionInfo.getName()+",版本号："+ledVersionInfo.getVersion()+",已存在！不能重复上传！");
            }
            flag = ledVersionInfoDao.insert(ledVersionInfo);
        }else{
            //判断别的ID中版本和名称是否存在
            list = ledVersionInfoDao.findList(ledVersionInfo.getName(), "", "", ledVersionInfo.getVersion(),1,ledVersionInfo.getId());
            if(list != null && list.size()>0){
                return ResultUtils.error("版本名称："+ledVersionInfo.getName()+",版本号："+ledVersionInfo.getVersion()+",已存在！不能更新！");
            }
            flag = ledVersionInfoDao.update(ledVersionInfo);
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result delete(String snList) {
        if(!CheckUtils.isNull(snList)){
            if(ledVersionInfoDao.delete(Arrays.asList(snList.split(","))) > 0){
                return ResultUtils.success();
            }

        }
        return ResultUtils.failure();
    }


    @Override
    public Result findList(String name, String creator, String createTime,Integer pageNum, Integer pageSize) {

        if (!CheckUtils.checkId(pageNum) || !CheckUtils.checkId(pageSize)) {
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<LedVersionInfo> LedVersionInfoList = ledVersionInfoDao.findList(name, creator,createTime,"",0,null);
        PageInfo<LedVersionInfo> faultCodePage = new PageInfo<>(LedVersionInfoList);
        return ResultUtils.success(faultCodePage);
    }

    /**
     * 固件升级
     * @param infoBox
     */
    @Override
    public Result firmwareUpgrade(InfoBox<LedVersionInfo> infoBox) {
        if(redisService.hasKey(LedConst.REDIS_RECORD_REFRESH_UPGRADE_TIME_+headerUtil.cloudToken())){
            return ResultUtils.error("操作间隔不得小于5秒");
        }

        //发送给本地MQTT
        try {
            redisService.set(LedConst.REDIS_RECORD_REFRESH_UPGRADE_TIME_+headerUtil.cloudToken(),infoBox.getT()+"",LedConst.BANTIME);
            lightDeviceUtil.ledFirmwareUpgrade(infoBox);
        } catch (MqttException e) {
           return ResultUtils.failure();
        }
        return ResultUtils.success();
    }
}
