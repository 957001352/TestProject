package com.dhlk.light.factory.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.TaskScheduler;

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
    Result selectTaskSchedulerById(Integer id);

    /**
     * 查询定时任务的名称是否重复
     *
     * @param id 定时任务ID
     * @param name 定时任务名称
     * @return 定时任务
     */
    Result selectTaskSchedulerById(Integer id, String name);

    /**
     * 查询定时任务列表
     *
     * @return 定时任务集合
     */
    Result selectTaskSchedulerList(String name, Integer pageNum, Integer pageSize);

    /**
     * 新增定时任务
     *
     * @param taskScheduler 定时任务
     * @return 结果
     */
    Result insertTaskScheduler(TaskScheduler taskScheduler);


    /**
     * 批量删除定时任务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    Result deleteTaskSchedulerByIds(String ids);

    /**
     * 删除定时任务信息
     *
     * @param id 定时任务ID
     * @return 结果
     */
    Result deleteTaskSchedulerById(Integer id);
    /**
     * 开启定时任务
     *
     * @param taskSchedulerId 定时任务ID
     * @return 结果
     */
    Result startTaskScheduler(Integer taskSchedulerId);
    /**
     * 停止定时任务
     *
     * @param taskSchedulerId 定时任务ID
     * @return 结果
     */
    Result stopTaskScheduler(Integer taskSchedulerId);
    /**
     * 查询定时任务日志列表
     *
     * @param taskSchedulerId 定时任务ID
     * @return 结果
     */
    Result selectTaskSchedulerLogList(Integer taskSchedulerId, Integer pageNum, Integer pageSize);
}
