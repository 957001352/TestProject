package com.dhlk.light.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.BiProxyServerInfo;
import com.dhlk.entity.basicmodule.Tenant;
import com.dhlk.entity.light.Computer;
import com.dhlk.enums.ResultEnum;
import com.dhlk.light.service.dao.ComputerDao;
import com.dhlk.light.service.dao.SwitchDao;
import com.dhlk.light.service.dao.TenantDao;
import com.dhlk.light.service.service.ComputerService;
import com.dhlk.light.service.util.HeaderUtil;
import com.dhlk.light.service.util.LightDeviceUtil;
import com.dhlk.systemconst.Const;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ComputerServiceImpl implements ComputerService {
    @Autowired
    private ComputerDao computerDao;
    @Autowired
    private SwitchDao switchDao;
    @Autowired
    private HeaderUtil headerUtil;
    @Autowired
    private LightDeviceUtil lightDeviceUtil;
    @Autowired
    private TenantDao tenantDao;

    @Override
    @Transactional
    public Result save(Computer computer) {
        if(computer == null){
            return ResultUtils.error("参数错误");
        }
        if(CheckUtils.isNull(computer.getTenantId())){
            computer.setTenantId(headerUtil.tenantId());
        }
        int flag = -1;
        if (computerDao.isRepeatName(computer) > 0) {
            return ResultUtils.error("名称已存在");
        }
        if (computerDao.isRepeatSn(computer) > 0) {
            return ResultUtils.error("sn已存在");
        }
        if (computerDao.isRepeatIp(computer) > 0) {
            return ResultUtils.error("IP已存在");
        }
        if (CheckUtils.isNull(computer.getId())) {
            flag = computerDao.insert(computer);
        } else {
            if(computerDao.isExist(computer.getId()) <= 0){
                return ResultUtils.error("该一体机已被删除，请刷新页面");
            }
            flag = computerDao.update(computer);
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result delete(String ids) {
        if (!CheckUtils.isNull(ids)) {
            boolean flag = false;
            for (String id:ids.split(",")) {
                if(computerDao.isExist(Integer.parseInt(id)) <= 0){
                    flag = true;
                }
            }
            if(flag){
                return ResultUtils.error("该一体机已被删除，请刷新页面");
            }
//            if(switchDao.findListByComputerId(ids.split(",")) > 0){
//                return ResultUtils.error("一体机被绑定");
//            }
            if (computerDao.delete(ids.split(",")) > 0) {
                return ResultUtils.success();
            }
        }
        return ResultUtils.failure();
    }

    @Override
    public Result findList(String name, Integer pageNum, Integer pageSize) {
        if (CheckUtils.isIntNumeric(pageNum) && CheckUtils.isIntNumeric(pageSize)) {
            PageHelper.startPage(pageNum, pageSize);
            PageInfo<Computer> computerInfo = new PageInfo<>(computerDao.findList(name, headerUtil.tenantId()));
            return ResultUtils.success(computerInfo);
        } else return ResultUtils.success(computerDao.findList(name, headerUtil.tenantId()));
    }

    @Override
    public Result addReseller(String biProxyServerInfo,String mac) {
        if(CheckUtils.isNull(biProxyServerInfo) || CheckUtils.isNull(mac)){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        //根据租户id查询租户code插入mqtt信息中
        BiProxyServerInfo biProxy = JSON.parseObject(biProxyServerInfo, BiProxyServerInfo.class);
        biProxy.setCode(mac);
        try {
            lightDeviceUtil.addReseller(biProxy);
        } catch (MqttException e) {
            e.printStackTrace();
            return ResultUtils.failure();
        }
        return ResultUtils.success();
    }
}
