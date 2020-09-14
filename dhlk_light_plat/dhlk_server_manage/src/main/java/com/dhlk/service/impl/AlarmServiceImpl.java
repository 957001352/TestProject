package com.dhlk.service.impl;

import com.alibaba.fastjson.JSON;
import com.dhlk.domain.*;
import com.dhlk.service.AlarmService;
import com.dhlk.service.RedisService;
import com.dhlk.systemconst.Const;
import com.dhlk.task.AlarmInfoToRedis;
import com.dhlk.task.BuildTask;
import com.dhlk.task.job.CpuJob;
import com.dhlk.task.job.MemoryJob;
import com.dhlk.utils.ResultUtils;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlarmServiceImpl implements AlarmService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private BuildTask buildTask;
    @Autowired
    private AlarmInfoToRedis alarmInfoToRedis;
    @Override
    public Result setAlarmRule(AlarmRule alarmRule) {
        boolean cset = redisService.hset(Const.REDIS_ALARM_RULE_KEY, Const.REDIS_CPU_KEY, JSON.toJSONString(alarmRule.getRedisCpuRule()));
        boolean mset = redisService.hset(Const.REDIS_ALARM_RULE_KEY, Const.REDIS_MEMORY_KEY, JSON.toJSONString(alarmRule.getRedisMemoryRule()));
        boolean dset = redisService.hset(Const.REDIS_ALARM_RULE_KEY, Const.REDIS_DISK_KEY, JSON.toJSONString(alarmRule.getRedisDiskRule()));
        if(cset&&mset&&dset){
            try {
                Scheduler scheduler = buildTask.getScheduler();
                scheduler.deleteJob(new JobKey("dhlk","cpu"));
                scheduler.deleteJob(new JobKey("dhlk","memory"));
                JobDataMap jobDataMap = new JobDataMap();
                jobDataMap.put("alarmInfoToRedis",alarmInfoToRedis);

                buildTask.collectCpuAlarmInfo(getCycle(Const.REDIS_CPU_KEY)*60, CpuJob.class,"cpu","cpuTrigger",jobDataMap);
                buildTask.collectCpuAlarmInfo(getCycle(Const.REDIS_MEMORY_KEY)*60, MemoryJob.class,"memory","memoryTrigger",jobDataMap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
            return ResultUtils.success();
        }
        return ResultUtils.failure();
    }

    @Override
    public Result getAlarmInfo(String type, Long createTime, Integer pageNow, Integer limit) {
        PageBean pageBean = new PageBean();
        pageBean.setPageNow(pageNow);
        pageBean.setLimit(limit);
        if(redisService.hasKey(Const.REDIS_ALARM_INFO_KEY)){
            List<Object> objects = redisService.lGet(Const.REDIS_ALARM_INFO_KEY, 0, -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            List<AlarmInfo> alarmInfos = new ArrayList<>();
            if(type == null && createTime == null){
                for (Object obj:objects) {
                    AlarmInfo alarmInfo = JSON.parseObject(obj.toString(), AlarmInfo.class);
                    alarmInfos.add(alarmInfo);
                }
            }else if(createTime == null){
                for (Object obj:objects) {
                    AlarmInfo alarmInfo = JSON.parseObject(obj.toString(), AlarmInfo.class);
                    if(type.equals(alarmInfo.getType())){
                        alarmInfos.add(alarmInfo);
                    }
                }
            }else if(type == null){
                for (Object obj:objects) {
                    AlarmInfo alarmInfo = JSON.parseObject(obj.toString(), AlarmInfo.class);
                    if((sdf.format(new Date(createTime))).equals(sdf.format(new Date(alarmInfo.getTs())))){
                        alarmInfos.add(alarmInfo);
                    }
                }
            }else {
                for (Object obj:objects) {
                    AlarmInfo alarmInfo = JSON.parseObject(obj.toString(), AlarmInfo.class);
                    if(type.equals(alarmInfo.getType()) && (sdf.format(new Date(createTime))).equals(sdf.format(new Date(alarmInfo.getTs())))){
                        alarmInfos.add(alarmInfo);
                    }
                }
            }
            alarmInfos = alarmInfos.stream().sorted(Comparator.comparing(AlarmInfo::getTs).reversed()).collect(Collectors.toList());
            List<AlarmInfo> alarmInfoList = new ArrayList<>();
            for (int i = pageBean.getOffset(); i < pageBean.getOffset() + pageBean.getLimit(); i++) {
                if(i < alarmInfos.size()){
                    alarmInfoList.add(alarmInfos.get(i));
                }
            }

            pageBean.setList(alarmInfoList);
            pageBean.setCount(alarmInfos.size());
        }
        return ResultUtils.success(pageBean);
    }

    @Override
    public Result deleteAlarmInfo(AlarmInfo alarmInfo) {
        long l = redisService.lRemove(Const.REDIS_ALARM_INFO_KEY, 0, JSON.toJSONString(alarmInfo));
        return l < 0? ResultUtils.failure(): ResultUtils.success();
    }

    @Override
    public Result getAlarmRule() {
        AlarmRule alarmRule = new AlarmRule(getRule(Const.REDIS_CPU_KEY),getRule(Const.REDIS_MEMORY_KEY),getRule(Const.REDIS_DISK_KEY));
        if(alarmRule.getRedisCpuRule()==null&&alarmRule.getRedisDiskRule()==null&&alarmRule.getRedisMemoryRule()==null){
            return ResultUtils.success(null);
        }
        return ResultUtils.success(alarmRule);
    }

    public RedisAlarmRule getRule(String key){
        RedisAlarmRule rule = null;
        if(redisService.hasKeyAndItem(Const.REDIS_ALARM_RULE_KEY,key)){
            rule = JSON.parseObject(redisService.hget(Const.REDIS_ALARM_RULE_KEY,key).toString(), RedisAlarmRule.class);
        }
        return rule;
    }

    private Integer getCycle(String key){
        Integer cycle = 1;
        if(redisService.hasKeyAndItem(Const.REDIS_ALARM_RULE_KEY,key)){
            RedisAlarmRule rule = JSON.parseObject(redisService.hget(Const.REDIS_ALARM_RULE_KEY, key).toString(), RedisAlarmRule.class);
            cycle = rule.getCycle();
        }
        return cycle;
    }
}
