package com.dhlk.light.factory.task;

import com.dhlk.entity.light.Led;
import com.dhlk.entity.light.TaskScheduler;
import com.dhlk.entity.light.TaskSchedulerDetail;
import com.dhlk.entity.light.TaskSchedulerLog;
import com.dhlk.light.factory.dao.TaskSchedulerLogDao;
import com.dhlk.light.factory.service.impl.LedServiceImpl;
import com.dhlk.light.factory.util.LightDeviceUtil;
import com.dhlk.light.factory.websocket.SpringContextHolder;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//开灯
public class LightTask implements Runnable {
    private Logger log = LoggerFactory.getLogger(LightTask.class);
    private TaskScheduler taskScheduler;

    private TaskSchedulerDetail taskSchedulerDetail;

    public LightTask(TaskScheduler taskScheduler, TaskSchedulerDetail taskSchedulerDetail) {
        this.taskScheduler = taskScheduler;
        this.taskSchedulerDetail = taskSchedulerDetail;
    }

    @Override
    public void run() {
        //执行定时开关灯任务
        LightDeviceUtil lightDeciveUtil = SpringContextHolder.getBean(LightDeviceUtil.class);
        Integer status=taskSchedulerDetail.getOnOff();
        String operate=null;
        if(status==1){//开灯并设置亮度
            lightDeciveUtil.ledTaskOnBright(getLedSns(taskScheduler.getSwitchId()),status,taskSchedulerDetail.getBrightness());
            operate="开灯";
        }else if(status==0){//关灯操作
            lightDeciveUtil.ledOnOrOff(getLedSns(taskScheduler.getSwitchId()),status,false);
            operate="关灯";
        }else if(status==2){//亮度设置
            lightDeciveUtil.ledBrightness(getLedSns(taskScheduler.getSwitchId()),taskSchedulerDetail.getBrightness(),false);
            operate="调光";
        }
        TaskSchedulerLog log=new TaskSchedulerLog();
        log.setCreateTime( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        log.setResult(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+operate + "成功");
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
