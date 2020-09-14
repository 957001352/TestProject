package com.dhlk.light.factory.dao;

import com.dhlk.entity.light.TaskSchedulerDetail;

import java.util.List;

/**
 * 定时任务详情Mapper接口
 * 
 * @author ruoyi
 * @date 2020-06-04
 */
public interface TaskSchedulerDetailDao
{
    /**
     * 查询定时任务详情
     * 
     * @param id 定时任务详情ID
     * @return 定时任务详情
     */
    public TaskSchedulerDetail selectTaskSchedulerDetailById(Long id);

    /**
     * 查询定时任务详情列表
     * 
     * @param taskSchedulerDetail 定时任务详情
     * @return 定时任务详情集合
     */
    public List<TaskSchedulerDetail> selectTaskSchedulerDetailList(TaskSchedulerDetail taskSchedulerDetail);

    /**
     * 新增定时任务详情
     * 
     * @param taskSchedulerDetail 定时任务详情
     * @return 结果
     */
    public int insertTaskSchedulerDetail(TaskSchedulerDetail taskSchedulerDetail);

    /**
     * 修改定时任务详情
     * 
     * @param taskSchedulerDetail 定时任务详情
     * @return 结果
     */
    public int updateTaskSchedulerDetail(TaskSchedulerDetail taskSchedulerDetail);

    /**
     * 删除定时任务详情
     * 
     * @param id 定时任务详情ID
     * @return 结果
     */
    public int deleteTaskSchedulerDetailById(Long id);

    /**根据定时任务id
     * 删除定时任务详情
     *
     * @param schedulerId 定时任务详情ID
     * @return 结果
     */
    public int deleteTaskSchedulerDetailBySchedulerId(Integer schedulerId);

    /**
     * 批量删除定时任务详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTaskSchedulerDetailByIds(String[] ids);

    /**
     * 批量插入
     *
     * @param taskSchedulerDetail
     * @returnt
     */
    Integer insertBatch(List<TaskSchedulerDetail> taskSchedulerDetail);

    /**
     * 删除
     *
     * @return
     */
    Integer delete();


    /**
     * 根据租户Id获取TaskSchedulerDetail
     *
     * @param scheduleId
     * @return
     */
    List<TaskSchedulerDetail> findListByScheduleId(Integer scheduleId);
}
