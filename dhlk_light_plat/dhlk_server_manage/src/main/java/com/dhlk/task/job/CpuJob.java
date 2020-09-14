package com.dhlk.task.job;

import com.dhlk.systemconst.Const;
import com.dhlk.task.AlarmInfoToRedis;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class CpuJob implements Job {

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        // TODO Auto-generated method stub
        JobDataMap mergedJobDataMap = arg0.getMergedJobDataMap();
        AlarmInfoToRedis alarmInfoToRedis = (AlarmInfoToRedis) mergedJobDataMap.get("alarmInfoToRedis");
        alarmInfoToRedis.getAlarm(Const.REDIS_CPU_KEY,"CPU");
        System.out.println("cpu开启了定时任务");

    }

}
