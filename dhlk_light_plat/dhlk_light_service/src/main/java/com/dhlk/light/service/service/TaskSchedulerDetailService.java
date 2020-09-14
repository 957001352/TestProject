package com.dhlk.light.service.service;

import com.dhlk.entity.light.TaskSchedulerDetail;

import java.util.List;

/**
 * 定时任务详情Service接口
 * 
 * @author ruoyi
 * @date 2020-06-04
 */
public interface TaskSchedulerDetailService
{
    /**
     * 查询定时任务详情
     * 
     * @param id 定时任务详情ID
     * @return 定时任务详情
     */
    TaskSchedulerDetail selectTaskSchedulerDetailById(Long id);

    /**
     * 查询定时任务详情列表
     * 
     * @param taskSchedulerDetail 定时任务详情
     * @return 定时任务详情集合
     */
    List<TaskSchedulerDetail> selectTaskSchedulerDetailList(TaskSchedulerDetail taskSchedulerDetail);

    /**
     * 新增定时任务详情
     * 
     * @param taskSchedulerDetail 定时任务详情
     * @return 结果
     */
    int insertTaskSchedulerDetail(TaskSchedulerDetail taskSchedulerDetail);

    /**
     * 修改定时任务详情
     * 
     * @param taskSchedulerDetail 定时任务详情
     * @return 结果
     */public int updateTaskSchedulerDetail(TaskSchedulerDetail taskSchedulerDetail);

    /**
     * 批量删除定时任务详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteTaskSchedulerDetailByIds(String ids);

    /**
     * 删除定时任务详情信息
     * 
     * @param id 定时任务详情ID
     * @return 结果
     */
    int deleteTaskSchedulerDetailById(Long id);
}
