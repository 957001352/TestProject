package com.dhlk.task.job;

import com.dhlk.systemconst.Const;
import com.dhlk.task.AlarmInfoToRedis;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MemoryJob implements Job {

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        JobDataMap mergedJobDataMap = arg0.getMergedJobDataMap();
        AlarmInfoToRedis alarmInfoToRedis = (AlarmInfoToRedis) mergedJobDataMap.get("alarmInfoToRedis");
        alarmInfoToRedis.getAlarm(Const.REDIS_MEMORY_KEY,"内存");
        // TODO Auto-generated method stub
        System.out.println("memory开启了定时任务");

    }

}
