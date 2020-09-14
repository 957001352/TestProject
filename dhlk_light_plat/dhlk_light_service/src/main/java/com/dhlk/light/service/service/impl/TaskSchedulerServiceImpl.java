package com.dhlk.light.service.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.dhlk.domain.Result;
import com.dhlk.entity.light.Area;
import com.dhlk.entity.light.SyncDataResult;
import com.dhlk.entity.light.TaskScheduler;
import com.dhlk.entity.light.TaskSchedulerDetail;
import com.dhlk.enums.ResultEnum;
import com.dhlk.enums.SystemEnums;
import com.dhlk.exceptions.MyException;
import com.dhlk.light.service.config.ScheduledConf;
import com.dhlk.light.service.dao.TaskSchedulerDao;
import com.dhlk.light.service.dao.TaskSchedulerDetailDao;
import com.dhlk.light.service.dao.TaskSchedulerLogDao;
import com.dhlk.light.service.mqtt.MqttSendServer;
import com.dhlk.light.service.service.DataSyncService;
import com.dhlk.light.service.service.TaskSchedulerService;
import com.dhlk.light.service.task.LightTask;
import com.dhlk.light.service.util.HeaderUtil;
import com.dhlk.light.service.util.LedConst;
import com.dhlk.light.service.util.LightDeviceUtil;
import com.dhlk.light.service.util.SchedulerResult;
import com.dhlk.systemconst.Const;
import com.dhlk.util.AuthUserUtil;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 定时任务Service业务层处理
 *
 * @author ruoyi
 * @date 2020-06-04
 */
@Service
//@Scope("prototype")
public class TaskSchedulerServiceImpl implements TaskSchedulerService
{
    @Autowired
    private TaskSchedulerDao taskSchedulerDao;

    @Autowired
    private TaskSchedulerDetailDao taskSchedulerDetailDao;

    @Autowired
    private TaskSchedulerLogDao taskSchedulerLogDao;

    @Autowired
    private HeaderUtil headerUtil;

    @Autowired
    private MqttSendServer mqttSendServer;
    /**
     * 查询定时任务
     *
     * @param id 定时任务ID
     * @return 定时任务
     */
    @Override
    public Result selectTaskSchedulerById(Integer id)
    {
        if(!CheckUtils.checkId(id) || id<1){
            new MyException(SystemEnums.PARMS_ILLEGAL);
        }
        TaskScheduler taskScheduler = taskSchedulerDao.selectTaskSchedulerById(id);
        return ResultUtils.success(taskScheduler);
    }

    @Override
    public Result selectTaskSchedulerById(Integer id, String name) {
        //id可以为空
        if(CheckUtils.isNull(name)){
            new MyException(SystemEnums.PARMS_ILLEGAL);
        }
        return ResultUtils.success(taskSchedulerDao.selectTaskSchedulerById(id));
    }

    /**
     * 查询定时任务列表
     *
     * @param name 定时任务名称
     * @return 定时任务
     */
    @Override
    public Result selectTaskSchedulerList(String name,Integer pageNum, Integer pageSize)
    {   //name可以为空
        if(!CheckUtils.checkId(pageNum) || !CheckUtils.checkId(pageSize)){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<TaskScheduler> taskSchedulerList = taskSchedulerDao.selectTaskSchedulerList(name,headerUtil.tenantId());
        PageInfo<TaskScheduler> taskSchedulerPage = new PageInfo<>(taskSchedulerList);
        return ResultUtils.success(taskSchedulerPage);
    }

    /**
     * 新增定时任务
     *
     * @param taskScheduler 定时任务
     * @return 结果
     */
    @Override
    @Transactional
    public Result saveTaskScheduler(TaskScheduler taskScheduler)
    {
        if (CheckUtils.isNull(taskScheduler)) {
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        if(taskSchedulerDao.selectTaskSchedulerByNameAndId(taskScheduler.getId(),taskScheduler.getName(),headerUtil.tenantId())!=null){
            return ResultUtils.error("任务名称重复!");
        }
        int res=0;
        if(CheckUtils.isNull(taskScheduler.getId())){
            taskScheduler.setStatus(1);
            taskScheduler.setTenantId(headerUtil.tenantId());
            res = taskSchedulerDao.insertTaskScheduler(taskScheduler);
            if(res>0){
                //保存定时任务详情
                int count=saveTaskSchedulerDetail(taskScheduler);
                if(count>0){
                    mqttSendServer.sendMQTTMessage(LedConst.LOCAL_TOPIC_TASK_DATASYNC,JSON.toJSONString(taskScheduler));
                    return ResultUtils.success();
                }
            }
        }else {
            TaskScheduler task = taskSchedulerDao.selectTaskSchedulerById(taskScheduler.getId());
            if(task == null){
                return ResultUtils.error("该任务不存在");
            }
            res = taskSchedulerDao.updateTaskScheduler(taskScheduler);
            //先删除 再保存
            //int res1=taskSchedulerDetailDao.deleteTaskSchedulerDetailBySchedulerId(taskScheduler.getId());
            if(res>0){
                //保存定时任务详情
                int count=saveTaskSchedulerDetail(taskScheduler);
                if(count>0){
                    taskScheduler.setTenantId(headerUtil.tenantId());
                    mqttSendServer.sendMQTTMessage(LedConst.LOCAL_TOPIC_TASK_DATASYNC,JSON.toJSONString(taskScheduler));
                    return ResultUtils.success();
                }
            }
        }
        return ResultUtils.failure();
    }


    /**
     * 删除定时任务对象
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional
    public Result deleteTaskSchedulerByIds(String ids)
    {
        if (CheckUtils.isNull(ids)) {
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        String[] strings = ids.split(",");
        for (String str: strings) {
            //先查询任务是否存在
            TaskScheduler taskScheduler = taskSchedulerDao.selectTaskSchedulerById(Integer.valueOf(str));
            if(taskScheduler == null){
                return ResultUtils.error("该任务已被删除");
            }
        }
        if(taskSchedulerDao.deleteTaskSchedulerByIds(strings) > 0){
            //删除定时任务详情和日志
            for (String string:strings) {
                taskSchedulerDetailDao.deleteTaskSchedulerDetailBySchedulerId(Integer.parseInt(string));
                taskSchedulerLogDao.deleteTaskSchedulerLogBySchedulerId(Integer.parseInt(string));
                mqttSendServer.sendMQTTMessage(LedConst.LOCAL_TOPIC_TASK_DELETE,string);
                //lightDeviceUtil.sendMessToMqttTaskDel(LedConst.LOCAL_TOPIC_TASK_DELETE,string);
            }
            return ResultUtils.success();
        }else return ResultUtils.failure();
    }

    /**
     * 删除定时任务信息
     *
     * @param id 定时任务ID
     * @return 结果
     */
    public Result deleteTaskSchedulerById(Integer id)
    {
        if(!CheckUtils.checkId(id) || id<1){
            new MyException(SystemEnums.PARMS_ILLEGAL);
        }
        if(taskSchedulerDao.deleteTaskSchedulerById(id) > 0){
            return ResultUtils.success();
        }else return ResultUtils.failure();
    }

    @Override
    public Result startTaskScheduler(Integer taskSchedulerId) {
        if(!CheckUtils.checkId(taskSchedulerId) || taskSchedulerId<1){
            return ResultUtils.success("参数错误");
        }
        SchedulerResult schedulerResult = new SchedulerResult(LedConst.LOCAL_TOPIC_TASK_ONOFF, taskSchedulerId, 0, headerUtil.cloudToken());
        return ResultUtils.success("命令已发送");
    }

    @Override
    public Result stopTaskScheduler(Integer taskSchedulerId) throws Exception {
        if(!CheckUtils.checkId(taskSchedulerId) || taskSchedulerId<1){
            return ResultUtils.success("参数错误");
        }
        // 1
        SchedulerResult schedulerResult = new SchedulerResult(LedConst.LOCAL_TOPIC_TASK_ONOFF, taskSchedulerId, 1, headerUtil.cloudToken());
        return ResultUtils.success("命令已发送");
    }

    @Override
    public Result selectTaskSchedulerLogList(Integer id,Integer pageNum, Integer pageSize) {
        if( !CheckUtils.checkId(id) || id<1 || !CheckUtils.checkId(pageNum) || !CheckUtils.checkId(pageSize)){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> maps = taskSchedulerDao.selectTaskSchedulerLogList(id);
        PageInfo<Map<String, Object>> taskSchedulerLogPage = new PageInfo<>(maps);
        return ResultUtils.success(taskSchedulerLogPage);
    }

    @Override
    public Result updateSchedulerStatus(String schedulerId,String status) {
        if(CheckUtils.isNull(schedulerId) || CheckUtils.isNull(status)){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        if(taskSchedulerDao.updateTaskStatus(Integer.parseInt(schedulerId),Integer.parseInt(status)) < 0) {
            return ResultUtils.failure();
        }
        return ResultUtils.success();
    }

    //保存定时任务详情
    private int saveTaskSchedulerDetail(TaskScheduler taskScheduler){
        AtomicInteger count=new AtomicInteger();
        List<Integer> inIds= taskSchedulerDetailDao.findListByScheduleId(taskScheduler.getId()).stream().map(TaskSchedulerDetail::getId).collect(Collectors.toList());
        List<TaskSchedulerDetail> taskSchedulerDetailList = taskScheduler.getTaskSchedulerDetailList();
        for (TaskSchedulerDetail taskSchedulerDetail : taskSchedulerDetailList) {
            taskSchedulerDetail.setScheduleId(taskScheduler.getId());
            //生成cron表达式 只执行一次 例如  0 35 20 * * 3,5,6    每周三、周五、周六 20:35:00 各执行一次
            String[] times = taskSchedulerDetail.getTime().split(":");
            taskSchedulerDetail.setExpression("0 " + times[1] + " " + times[0] + " * * " + taskScheduler.getScheduleCycle());
            int res=0;
            if(CheckUtils.isNull(taskSchedulerDetail.getId())){
                res=taskSchedulerDetailDao.insertTaskSchedulerDetail(taskSchedulerDetail);
            }else{
                if(inIds.contains(taskSchedulerDetail.getId())){
                    inIds.remove(taskSchedulerDetail.getId());
                }
                res=taskSchedulerDetailDao.updateTaskSchedulerDetail(taskSchedulerDetail);
            }
            //保存成功 count加1
            if(res>0){
                count.getAndIncrement();
            }
        }
        if(inIds!=null&&inIds.size()>0){
            for (Integer inId : inIds) {
                taskSchedulerDetailDao.deleteTaskSchedulerDetailById(Long.parseLong(inId.toString()));
            }
        }
        return count.get();
    }
}
