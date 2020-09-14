package com.dhlk.task;

import com.alibaba.fastjson.JSON;
import com.dhlk.domain.AlarmInfo;
import com.dhlk.domain.RedisAlarmRule;
import com.dhlk.domain.SystemStatus;
import com.dhlk.service.RedisService;
import com.dhlk.service.SystemStatusService;
import com.dhlk.systemconst.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.rmi.RemoteException;

/**
 * 定时往redis里缓存cou/磁盘和内存信息
 */
@Component
@EnableScheduling
public class InfoToRedis {
    @Autowired
    private RedisService redisService;
    @Autowired
    private SystemStatusService systemStatusService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void saveInfoToRedis(){

        try {
            SystemStatus runtime = systemStatusService.getRuntime();
            //判断CPU是否使用报警策略
            if(redisService.hasKeyAndItem(Const.REDIS_ALARM_RULE_KEY,Const.REDIS_CPU_KEY)){
                RedisAlarmRule cpuRule = JSON.parseObject(redisService.hget(Const.REDIS_ALARM_RULE_KEY,Const.REDIS_CPU_KEY).toString(), RedisAlarmRule.class);

                long size = redisService.lGetListSize(Const.REDIS_CPU_KEY);
                long count = cpuRule.getCycle()*60/5;
                if(size >= count){
                    for (int i = 0; i <= (size-count); i++) {
                        redisService.lLeftPop(Const.REDIS_CPU_KEY);
                    }
                }
                redisService.lSet(Const.REDIS_CPU_KEY,runtime.getCpu_combined().toString());

            }
            //判断内存是否使用报警策略
            if(redisService.hasKeyAndItem(Const.REDIS_ALARM_RULE_KEY,Const.REDIS_MEMORY_KEY)){
                RedisAlarmRule memoryRule = JSON.parseObject(redisService.hget(Const.REDIS_ALARM_RULE_KEY,Const.REDIS_MEMORY_KEY).toString(), RedisAlarmRule.class);

                long size = redisService.lGetListSize(Const.REDIS_MEMORY_KEY);
                long count = memoryRule.getCycle()*60/5;
                if(size >= count){
                    for (int i = 0; i <= (size-count); i++) {
                        redisService.lLeftPop(Const.REDIS_MEMORY_KEY);
                    }
                }
                redisService.lSet(Const.REDIS_MEMORY_KEY,runtime.getMemory_combined().toString());
            }
            //判断CPU是否使用报警策略
            if(redisService.hasKeyAndItem(Const.REDIS_ALARM_RULE_KEY,Const.REDIS_DISK_KEY)){
                RedisAlarmRule diskRule = JSON.parseObject(redisService.hget(Const.REDIS_ALARM_RULE_KEY,Const.REDIS_DISK_KEY).toString(), RedisAlarmRule.class);
                checkAlarm(diskRule,runtime.getDisk().getUsePercent());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据条件进行比较 条件 1:大于 2:大于等于 3:等于 4:小于等于 5:小于
     * @param diskRule
     */
    private void checkAlarm(RedisAlarmRule diskRule, String value) {
        switch (diskRule.getCondition()) {
            case 1:
                if(Double.parseDouble(value) > diskRule.getThreshold()){
                    mapToRedis(diskRule,"大于");
                }
                break;
            case 2:
                if(Double.parseDouble(value) >= diskRule.getThreshold()){
                    mapToRedis(diskRule,"大于等于");
                }
                break;
            case 3:
                if(Double.parseDouble(value) == diskRule.getThreshold()){
                    mapToRedis(diskRule,"等于");
                }
                break;
            case 4:
                if(Double.parseDouble(value) <= diskRule.getThreshold()){
                    mapToRedis(diskRule,"小于等于");
                }
                break;
            case 5:
                if(Double.parseDouble(value) < diskRule.getThreshold()){
                    mapToRedis(diskRule,"小于");
                }
                break;
        }
    }

    private void mapToRedis(RedisAlarmRule diskRule, String item) {
        AlarmInfo alarmInfo = new AlarmInfo("磁盘","磁盘使用率"+item+diskRule.getThreshold()+"%",System.currentTimeMillis());
        redisService.lSet(Const.REDIS_ALARM_INFO_KEY, JSON.toJSONString(alarmInfo));
    }
}
