package com.dhlk.light.service.dao;

import com.dhlk.entity.light.TaskSchedulerLog;

import java.util.List;

/**
 * 定时任务详情Mapper接口
 *
 * @author ruoyi
 * @date 2020-06-04
 */
public interface TaskSchedulerLogDao {
    /**
     * 查询定时任务详情
     *
     * @param id 定时任务详情ID
     * @return 定时任务详情
     */
    public TaskSchedulerLog selectTaskSchedulerLogById(Integer id);

    /**
     * 查询定时任务详情列表
     *
     * @param taskSchedulerLog 定时任务详情
     * @return 定时任务详情集合
     */
    public List<TaskSchedulerLog> selectTaskSchedulerLogList(TaskSchedulerLog taskSchedulerLog);

    /**
     * 新增定时任务详情
     *
     * @param taskSchedulerLog 定时任务详情
     * @return 结果
     */
    public int insertTaskSchedulerLog(TaskSchedulerLog taskSchedulerLog);

    /**
     * 修改定时任务详情
     *
     * @param taskSchedulerLog 定时任务详情
     * @return 结果
     */
    public int updateTaskSchedulerLog(TaskSchedulerLog taskSchedulerLog);

    /**
     * 删除定时任务详情
     *
     * @param id 定时任务详情ID
     * @return 结果
     */
    public int deleteTaskSchedulerLogById(Long id);


    /**
     * 删除定时任务日志
     *
     * @param id 定时任务详情ID
     * @return 结果
     */
    public int deleteTaskSchedulerLogBySchedulerId(Integer id);

    /**
     * 批量删除定时任务详情
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTaskSchedulerLogByIds(String[] ids);

    /**
     * TaskSchedulerLog
     *
     * @param scheduleId
     * @return
     */
    List<TaskSchedulerLog> findListByScheduleId(Integer scheduleId);
}
