package com.dhlk.light.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.TaskScheduler;
import java.util.List;
import java.util.Map;

/**
 * 定时任务Service接口
 * 
 * @author jlv
 * @date 2020-06-04
 */
public interface TaskSchedulerService
{
    /**
     * 查询定时任务
     * 
     * @param id 定时任务ID
     * @return 定时任务
     */
    public Result selectTaskSchedulerById(Integer id);

    /**
     * 查询定时任务的名称是否重复
     *
     * @param id 定时任务ID
     * @param name 定时任务名称
     * @return 定时任务
     */
    public Result selectTaskSchedulerById(Integer id,String name);

    /**
     * 查询定时任务列表
     *
     * @return 定时任务集合
     */
    public Result selectTaskSchedulerList(String name,Integer pageNum, Integer pageSize);

    /**
     * 新增定时任务
     * 
     * @param taskScheduler 定时任务
     * @return 结果
     */
    public Result saveTaskScheduler(TaskScheduler taskScheduler);


    /**
     * 批量删除定时任务
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public Result deleteTaskSchedulerByIds(String ids);

    /**
     * 删除定时任务信息
     * 
     * @param id 定时任务ID
     * @return 结果
     */
    public Result deleteTaskSchedulerById(Integer id);
    /**
     * 开启定时任务
     *
     * @param taskSchedulerId 定时任务ID
     * @return 结果
     */
    public Result startTaskScheduler(Integer taskSchedulerId);
    /**
     * 停止定时任务
     *
     * @param taskSchedulerId 定时任务ID
     * @return 结果
     */
    public Result stopTaskScheduler(Integer taskSchedulerId) throws Exception;
    /**
     * 查询定时任务日志列表
     *
     * @param taskSchedulerId 定时任务ID
     * @return 结果
     */
    public Result selectTaskSchedulerLogList(Integer taskSchedulerId,Integer pageNum, Integer pageSize);

    /**
     * 修改定时任务状态
     */
    Result updateSchedulerStatus(String schedulerId,String status);
}
