package com.dhlk.light.factory.service.impl;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.TaskScheduler;
import com.dhlk.entity.light.TaskSchedulerDetail;
import com.dhlk.enums.ResultEnum;
import com.dhlk.enums.SystemEnums;
import com.dhlk.exceptions.MyException;
import com.dhlk.light.factory.config.ScheduledConf;
import com.dhlk.light.factory.dao.TaskSchedulerDao;
import com.dhlk.light.factory.dao.TaskSchedulerDetailDao;
import com.dhlk.light.factory.dao.TaskSchedulerLogDao;
import com.dhlk.light.factory.service.TaskSchedulerService;
import com.dhlk.light.factory.task.LightTask;
import com.dhlk.light.factory.util.HeaderUtil;
import com.dhlk.systemconst.Const;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.HttpClientUtils;
import com.dhlk.utils.ResultUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

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
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture future;

    @Autowired
    private HttpServletRequest request;

    @Value("${cloud.baseUrl}")
    private String cloudBaseUrl;
    @Autowired
    private HeaderUtil headerUtil;

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
    public Result insertTaskScheduler(TaskScheduler taskScheduler)
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
                    return ResultUtils.success();
                }
            }
        }else {
            res = taskSchedulerDao.updateTaskScheduler(taskScheduler);
            //先删除 再保存
            int res1=taskSchedulerDetailDao.deleteTaskSchedulerDetailBySchedulerId(taskScheduler.getId());
            if(res>0 && res1>0){
                //保存定时任务详情
                int count=saveTaskSchedulerDetail(taskScheduler);
                if(count>0){
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
        if(taskSchedulerDao.deleteTaskSchedulerByIds(strings) > 0){
            //删除定时任务详情和日志
            for (String string:strings) {
                taskSchedulerDetailDao.deleteTaskSchedulerDetailBySchedulerId(Integer.parseInt(string));
                taskSchedulerLogDao.deleteTaskSchedulerLogBySchedulerId(Integer.parseInt(string));
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
            new MyException(SystemEnums.PARMS_ILLEGAL);
        }
        //根据任务id获取任务
        TaskScheduler taskScheduler = taskSchedulerDao.selectTaskSchedulerById(taskSchedulerId);
        if (taskScheduler != null) {
            //获取任务详情
            List<TaskSchedulerDetail> taskSchedulerDetailList = taskScheduler.getTaskSchedulerDetailList();
            for (TaskSchedulerDetail taskSchedulerDetail : taskSchedulerDetailList) {
                //线程池传入具体任务和cron触发器  new CronTrigger()方法的参数是cron表达式
                future = threadPoolTaskScheduler.schedule(new LightTask(taskScheduler,taskSchedulerDetail), new CronTrigger(taskSchedulerDetail.getExpression()));
                //把任务放入map中
                ScheduledConf.map.put(taskScheduler.getName() + taskSchedulerDetail.getId(), future);
            }
            //开启定时任务成功 修改任务状态为开启0
            taskScheduler.setStatus(0);
            taskSchedulerDao.updateTaskScheduler(taskScheduler);
            //更新云端定时任务状态
            //this.updateCloudTaskStatus(taskScheduler);
            return ResultUtils.success();
        }else  return ResultUtils.failure();
    }
    private  void updateCloudTaskStatus(TaskScheduler taskScheduler){
        try {
            Map params = new HashMap();
            params.put("schedulerId", taskScheduler.getId().toString());
            params.put("status", taskScheduler.getStatus().toString());
            Map headers = new HashMap();
            headers.put(Const.TOKEN_HEADER, request.getHeader(Const.TOKEN_HEADER));
            HttpClientUtils.doGet(cloudBaseUrl + "/scheduler/updateSchedulerStatus", headers, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public Result stopTaskScheduler(Integer taskSchedulerId){
        if(!CheckUtils.checkId(taskSchedulerId) || taskSchedulerId<1){
            new MyException(SystemEnums.PARMS_ILLEGAL);
        }
        //根据任务id获取任务
        TaskScheduler taskScheduler = taskSchedulerDao.selectTaskSchedulerById(taskSchedulerId);
        if (taskScheduler != null) {
            //获取任务详情
            List<TaskSchedulerDetail> taskSchedulerDetailList = taskScheduler.getTaskSchedulerDetailList();
            for (TaskSchedulerDetail taskSchedulerDetail : taskSchedulerDetailList) {
                ////把从map中获取任务
                ScheduledFuture scheduledFuture = ScheduledConf.map.get(taskScheduler.getName() + taskSchedulerDetail.getId());
                if(scheduledFuture!=null){
                    //中断一个线程池里正在执行的任务
                    scheduledFuture.cancel(true);
                    // 查看任务是否在正常执行之前结束,正常true
                    boolean cancelled = scheduledFuture.isCancelled();
                    while (!cancelled) {
                        scheduledFuture.cancel(true);
                    }
                }
            }
            //关闭定时任务成功 修改任务状态为关闭
            taskScheduler.setStatus(1);
            taskSchedulerDao.updateTaskScheduler(taskScheduler);
            //更新云端定时任务状态
            //this.updateCloudTaskStatus(taskScheduler);
            return ResultUtils.success();
        }else  return ResultUtils.failure();
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

    //保存定时任务详情
    private int saveTaskSchedulerDetail(TaskScheduler taskScheduler){
        AtomicInteger count=new AtomicInteger();
        List<TaskSchedulerDetail> taskSchedulerDetailList = taskScheduler.getTaskSchedulerDetailList();
        for (TaskSchedulerDetail taskSchedulerDetail : taskSchedulerDetailList) {
            taskSchedulerDetail.setScheduleId(taskScheduler.getId());
            //生成cron表达式 只执行一次 例如  0 35 20 * * 3,5,6    每周三、周五、周六 20:35:00 各执行一次
            String[] times = taskSchedulerDetail.getTime().split(":");
            taskSchedulerDetail.setExpression("0 " + times[1] + " " + times[0] + " * * " + taskScheduler.getScheduleCycle());
            int res=taskSchedulerDetailDao.insertTaskSchedulerDetail(taskSchedulerDetail);
            //保存成功 count加1
            if(res>0){
                count.getAndIncrement();
            }
        }
        return count.get();
    }



}
