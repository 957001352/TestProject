package com.dhlk.light.service.impl;



import com.dhlk.domain.Result;
import com.dhlk.entity.light.TaskScheduler;
import com.dhlk.entity.light.TaskSchedulerDetail;
import com.dhlk.light.service.dhlk_light_service.DhlkLightServiceApplication;
import com.dhlk.light.service.service.TaskSchedulerService;
import com.github.pagehelper.PageInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkLightServiceApplication.class)
public class TaskSchedulerServiceImplTest {
    @Autowired
    private TaskSchedulerService taskSchedulerService;


    //定时任务保存
    @Test
    public void insertTaskSchedulerTest() {
        TaskScheduler taskScheduler=new TaskScheduler();
        taskScheduler.setName("任务999");
        taskScheduler.setScheduleCycle("2,4,6");

        TaskSchedulerDetail taskSchedulerDetail=new TaskSchedulerDetail();
        taskSchedulerDetail.setScheduleId(taskScheduler.getId());
        taskSchedulerDetail.setTime("07:23");

        List<TaskSchedulerDetail> detailList=new ArrayList<TaskSchedulerDetail>();
        detailList.add(taskSchedulerDetail);

        taskScheduler.setTaskSchedulerDetailList(detailList);

        Result result = taskSchedulerService.saveTaskScheduler(taskScheduler);
        Assert.assertTrue(result.getCode()<0?false:true);
    }

    //定时任务保存
    @Test
    public void updateTaskSchedulerTest() {
        TaskScheduler taskScheduler=new TaskScheduler();
        taskScheduler.setId(21);
        taskScheduler.setName("任务666");
        taskScheduler.setScheduleCycle("2,3,6");

        TaskSchedulerDetail taskSchedulerDetail=new TaskSchedulerDetail();
        taskSchedulerDetail.setScheduleId(taskScheduler.getId());
        taskSchedulerDetail.setTime("09:43");

        List<TaskSchedulerDetail> detailList=new ArrayList<TaskSchedulerDetail>();
        detailList.add(taskSchedulerDetail);

        taskScheduler.setTaskSchedulerDetailList(detailList);

        Result result = taskSchedulerService.saveTaskScheduler(taskScheduler);
        Assert.assertTrue(result.getCode()<0?false:true);
    }
    //定时任务删除
    @Test
    public void deleteTaskSchedulerTest() {
        Result result = taskSchedulerService.deleteTaskSchedulerByIds("20,21");
        Assert.assertTrue(result.getCode()<0?false:true);
    }
    //定时任务查询
    @Test
    public void selectTaskSchedulerTest() {
        Result result = taskSchedulerService.selectTaskSchedulerList("试任",1,5);
        Assert.assertTrue(result.getCode()<0?false:true);
    }
    //定时任务开启
    @Test
    public void startTaskSchedulerTest() {
        Result result = taskSchedulerService.startTaskScheduler(15);
        Assert.assertTrue(result.getCode()<0?false:true);
    }
    //定时任务关闭
    @Test
    public void stopTaskSchedulerTest() {
        Result result = taskSchedulerService.startTaskScheduler(15);
        Assert.assertTrue(result.getCode()<0?false:true);
    }
}