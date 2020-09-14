package com.dhlk.light.service.task;

import com.dhlk.entity.light.Led;
import com.dhlk.entity.light.TaskScheduler;
import com.dhlk.entity.light.TaskSchedulerDetail;
import com.dhlk.entity.light.TaskSchedulerLog;
import com.dhlk.light.service.dao.TaskSchedulerLogDao;
import com.dhlk.light.service.service.impl.LedServiceImpl;
import com.dhlk.light.service.util.LightDeviceUtil;
import com.dhlk.light.service.websocket.SpringContextHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//开灯
public class LightTask implements Runnable {

    private TaskScheduler taskScheduler;

    private TaskSchedulerDetail taskSchedulerDetail;

    public LightTask(TaskScheduler taskScheduler, TaskSchedulerDetail taskSchedulerDetail) {
        this.taskScheduler = taskScheduler;
        this.taskSchedulerDetail = taskSchedulerDetail;
    }

    @Override
    public void run() {
        //执行定时任务  onOff 0 开灯   1 关灯
//        if (taskSchedulerDetail.getOnOff() == 0) {
//        }else{
//             }
        //执行定时开关灯任务
        LightDeviceUtil lightDeciveUtil = SpringContextHolder.getBean(LightDeviceUtil.class);
        lightDeciveUtil.ledOnOrOff(getLedSns(taskScheduler.getSwitchId()),String.valueOf(taskSchedulerDetail.getOnOff()));

        System.out.println("执行开灯任务" + this.taskScheduler.getName() + this.taskSchedulerDetail.getId() + "成功" + new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date()));
        TaskSchedulerLog log=new TaskSchedulerLog();
        log.setCreateTime( new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date()));
        log.setResult("执行开灯任务" + this.taskScheduler.getName() + this.taskSchedulerDetail.getId() + "成功" + new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date()));
        log.setScheduleId(this.taskScheduler.getId());
        log.setSchedulerDetailId(this.taskSchedulerDetail.getId());
        TaskSchedulerLogDao taskSchedulerLogMapper = (TaskSchedulerLogDao) SpringContextHolder.getApplicationContext().getBean("taskSchedulerLogDao");
        taskSchedulerLogMapper.insertTaskSchedulerLog(log);

    }

    //根据开关id获取所有关联灯的sns以逗号隔开的字符串
    public String getLedSns(Integer switchId){
        LedServiceImpl ledService = SpringContextHolder.getBean(LedServiceImpl.class);
        List<Led> leds = (List<Led>) ledService.findList(null, null, String.valueOf(taskScheduler.getSwitchId())).getData();
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < leds.size(); i++) {
            if(i != leds.size()-1){
                result.append(leds.get(i).getSn()+",");
            }else{
                result.append(leds.get(i).getSn());
            }
        }
        return String.valueOf(result);
    }
}
