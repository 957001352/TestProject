package com.dhlk.light.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dhlk.domain.Result;
import com.dhlk.entity.light.Switch;
import com.dhlk.entity.light.SwitchGroup;
import com.dhlk.entity.light.Wifi;
import com.dhlk.light.service.dao.LedSwitchDao;
import com.dhlk.light.service.dao.SwitchDao;
import com.dhlk.light.service.dao.SwitchGroupDao;
import com.dhlk.light.service.dao.WifiDao;
import com.dhlk.light.service.service.SwitchService;
import com.dhlk.light.service.util.HeaderUtil;
import com.dhlk.light.service.util.LightDeviceUtil;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SwitchServiceImpl implements SwitchService {
    @Autowired
    private SwitchDao switchDao;
    @Autowired
    private LedSwitchDao lightSwitchDao;
    @Autowired
    private SwitchGroupDao switchGroupDao;
    @Autowired
    private WifiDao wifiDao;
    @Autowired
    private HeaderUtil headerUtil;
    @Autowired
    private LightDeviceUtil lightDeviceUtil;
    @Override
    @Transactional
    public Result save(Switch swich) {

       /* List<String> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(swich.getSwitchGroups())) {
            for (SwitchGroup group : swich.getSwitchGroups()) {
                list.add(String.valueOf(group.getGroupId()));
            }
        }
        //默认的res为false,提交-->确定时,再次调用接口res传过来值为true
        if(!swich.getFlag()){
            List<SwitchGroup> lists = switchGroupDao.findList(list);
            if (!CollectionUtils.isEmpty(lists)) {
                List<String> listGroupId = new ArrayList<>();
                for (SwitchGroup group : lists) {
                    listGroupId.add(String.valueOf(group.getGroupId()));
                }
                String mes = org.apache.commons.lang3.StringUtils.join(listGroupId.toArray(), ",");
                return ResultUtils.success(0,mes + "重复","");
            }
        }*/

        if (swich == null) {
            return ResultUtils.error("参数错误");
        }
        if(CheckUtils.isNull(swich.getTenantId())){
            swich.setTenantId(headerUtil.tenantId());
        }
        if (switchDao.isRepeatSn(swich) > 0) {
            return ResultUtils.error("sn已存在");
        }
        if (switchDao.isRepeatIp(swich) > 0) {
            return ResultUtils.error("ip已存在");
        }
        if (switchDao.isRepeatName(swich) > 0) {
            return ResultUtils.error("名称已存在");
        }

        int flag = -1;
        if (CheckUtils.isNull(swich.getId())) {
            flag = switchDao.insert(swich);
        } else {
            if (switchDao.isExist(swich.getId()) <= 0) {
                return ResultUtils.error("该开关已被删除，请刷新页面");
            }
            flag = switchDao.update(swich);
        }

        //组设置
        if (!CollectionUtils.isEmpty(swich.getSwitchGroups())) {
            swich.getSwitchGroups().forEach(item -> {
                item.setSwitchId(swich.getId());
                SwitchGroup switchGroup = switchGroupDao.selectSwitchGroupById(item.getId());
                if (switchGroup == null) {
                    switchGroupDao.insert(item);
                } else {
                    switchGroupDao.update(item);
                }
            });
            //给mqtt发消息，开关设置分组
            this.sentSwitchGroupToMqtt(swich.getSn(),swich.getSwitchGroups());
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }
    /**
    * 开关组配置 发送给mqtt
     * @param sn
     * @param list
    * @return
    */
    public void sentSwitchGroupToMqtt(String sn, List<SwitchGroup> list) {
        JSONObject object = new JSONObject(true);
        for (int i=0;i<list.size();i++) {
            SwitchGroup sg=list.get(i);
            object.put("on_off"+i,sg.getStatus());
            object.put("grp_id"+i,sg.getGroupId());
        }
        lightDeviceUtil.setSwitchGrpId(sn,object);
    }
    @Override
    public Result delete(String ids) {
        if (!CheckUtils.isNull(ids)) {
            String[] split = ids.split(",");
            boolean flag = false;
            for (String id:split) {
                if(switchDao.isExist(Integer.parseInt(id)) <= 0){
                    flag = true;
                }
            }
            if(flag){
                return ResultUtils.error("该开关已被删除，请刷新页面");
            }
            if (lightSwitchDao.checkSwitchBond(split) > 0) {
                return ResultUtils.error("开关被绑定");
            }
            if (switchDao.delete(split) > 0) {
                return ResultUtils.success();
            }
        }
        return ResultUtils.failure();
    }

    @Override
    public Result findList(String name, Integer pageNum, Integer pageSize) {
        if (CheckUtils.isIntNumeric(pageNum) && CheckUtils.isIntNumeric(pageSize)) {
            PageHelper.startPage(pageNum, pageSize);
            PageInfo<Switch> switchInfo = new PageInfo<>(switchDao.findList(name, headerUtil.tenantId()));
            return ResultUtils.success(switchInfo);
        } else return ResultUtils.success(switchDao.findList(name, headerUtil.tenantId()));
    }

    @Override
    public Result findGroupList(Integer switchId) {
        return ResultUtils.success(switchDao.findGroupList(switchId));
    }
}
