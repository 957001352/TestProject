package com.dhlk.task;

import com.alibaba.fastjson.JSON;
import com.dhlk.domain.RedisAlarmRule;
import com.dhlk.service.RedisService;
import com.dhlk.systemconst.Const;
import com.dhlk.task.job.CpuJob;
import com.dhlk.task.job.MemoryJob;
import com.dhlk.utils.SpringContextHolder;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * 系统一启动就开启定时任务
 */
@Component
@DependsOn("springContextHolder")
public class BuildTask {
    //获取调度器
    private Scheduler scheduler;
    {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public BuildTask(){
        AlarmInfoToRedis alarmInfoToRedis = SpringContextHolder.getBean("alarmInfoToRedis");
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("alarmInfoToRedis",alarmInfoToRedis);
        try {
            collectCpuAlarmInfo(getCycle(Const.REDIS_CPU_KEY)*60, CpuJob.class,"cpu","cpuTrigger",jobDataMap);
            collectCpuAlarmInfo(getCycle(Const.REDIS_MEMORY_KEY)*60, MemoryJob.class,"memory","memoryTrigger",jobDataMap);
            scheduler.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public Scheduler getScheduler(){
        return this.scheduler;
    }

    public  void collectCpuAlarmInfo(Integer cycle, Class<? extends Job> job, String jobKeyGroup, String triggerGroup, JobDataMap jobDataMap) throws InterruptedException{
        try {
            //创建任务器：定义任务细节
            JobDetail jobDetail = JobBuilder.newJob(job).setJobData(jobDataMap).withIdentity("dhlk", jobKeyGroup).build();
            ScheduleBuilder cpuScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(cycle).repeatForever();
            //定义触发器
            Trigger trigger= TriggerBuilder.newTrigger().withIdentity("simpleTrigger", triggerGroup)
                    .withSchedule(cpuScheduleBuilder).startNow().build();

            //将任务和触发器注册到调度器中
            scheduler.scheduleJob(jobDetail, trigger);

            // Thread.sleep(1000*30);
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private Integer getCycle(String key){
        Integer cycle = 1;
        RedisService redisService = SpringContextHolder.getBean("redisServiceImpl");
        if(redisService.hasKeyAndItem(Const.REDIS_ALARM_RULE_KEY,key)){
            RedisAlarmRule rule = JSON.parseObject(redisService.hget(Const.REDIS_ALARM_RULE_KEY, key).toString(), RedisAlarmRule.class);
            cycle = rule.getCycle();
        }
        return cycle;
    }
}
