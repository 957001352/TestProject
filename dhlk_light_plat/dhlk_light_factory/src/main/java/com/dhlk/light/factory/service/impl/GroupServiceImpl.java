package com.dhlk.light.factory.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.domain.Result;
import com.dhlk.entity.light.Area;
import com.dhlk.entity.light.Led;
import com.dhlk.entity.light.LedPower;
import com.dhlk.entity.light.Switch;
import com.dhlk.light.factory.dao.LedDao;
import com.dhlk.light.factory.dao.SwitchDao;
import com.dhlk.light.factory.service.GroupService;
import com.dhlk.light.factory.util.LedConst;
import com.dhlk.service.RedisService;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private SwitchDao switchDao;
    @Autowired
    private LedDao ledDao;
    @Autowired
    private RedisService redisService;
    @Override
    public Result findList() {
        List<Switch> swichs = switchDao.findList();

        List<LinkedHashMap> groupLedSns = ledDao.findGroupLedSns();

        HashMap map = new HashMap();
        if(!CheckUtils.isNull(groupLedSns)){
            for (LinkedHashMap groupLedSn:groupLedSns) {
                map.put(groupLedSn.get("switchId"),groupLedSn.get("sns"));
            }
        }

        //查询灯开关状态
        if(swichs != null && swichs.size() > 0){
            for (Switch swich:swichs) {
               if(map.containsKey(swich.getId())){
                   String sns = map.get(swich.getId())+"";
                   swich.setLedSns(sns);
                   if(!CheckUtils.isNull(sns)){
                       String[] split = sns.split(",");
                       for (String sn:split) {
                           Object power = redisService.get(LedConst.REDIS_POWER + sn);
                           if (power == null) {
                               swich.setOnLineCount(swich.getOnLineCount()+1);
                           } else {
                               JSONObject obj = JSONObject.parseObject(power.toString());
                               if (obj != null && obj.get("status") != null) {
                                   if("0".equals(obj.get("status"))){
                                       swich.setOffCount(swich.getOffCount()+1);
                                   }else if("1".equals(obj.get("status"))){
                                       swich.setOnCount(swich.getOnCount()+1);
                                   }
                               }
                           }
                       }
                       swich.setOnLineCount(split.length-swich.getOnLineCount());
                   }
               }
            }
        }
        return ResultUtils.success(swichs);
    }

    @Override
    public Result ledStatusIsChange(String sns,String status) {
        if(CheckUtils.isNull(status) || CheckUtils.isNull(sns)){
            return ResultUtils.error("参数错误");
        }
        boolean flag = true;
        if(CheckUtils.isNull(sns)){
            return ResultUtils.success(flag);
        }
        for (String sn:sns.split(",")) {
            if(redisService.hasKey(LedConst.REDIS_POWER+sn)){
                LedPower ledPower = JSON.parseObject(redisService.get(LedConst.REDIS_POWER + sn) + "", LedPower.class);
                if(!status.equals(ledPower.getStatus())){
                    flag = false;
                }
            }
        }
        return ResultUtils.success(flag);
    }

    @Override
    public Result findGroupList(String areaId) {
        return ResultUtils.success(switchDao.findListByAreaId(areaId));
    }
}
