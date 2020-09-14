package com.dhlk.light.factory.dao;

import com.dhlk.entity.light.TaskScheduler;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 定时任务Mapper接口
 * 
 * @author ruoyi
 * @date 2020-06-04
 */
public interface TaskSchedulerDao
{
    /**
     * 查询定时任务
     * 
     * @param id 定时任务ID
     * @return 定时任务
     */
    public TaskScheduler selectTaskSchedulerById(Integer id);

    /**
     * 查询定时任务的名称是否重复
     *
     * @param id 定时任务ID
     * @return 定时任务
     */
    public TaskScheduler selectTaskSchedulerByNameAndId(@Param("id") Integer id, @Param("name") String name, @Param("tenantId") Integer tenantId);

    /**
     * 查询定时任务列表
     *
     * @return 定时任务集合
     */
    public List<TaskScheduler> selectTaskSchedulerList(@Param("name") String name, @Param("tenantId") Integer tenantId);

    /**
     * 新增定时任务
     * 
     * @param taskScheduler 定时任务
     * @return 结果
     */
    public int insertTaskScheduler(TaskScheduler taskScheduler);

    /**
     * 修改定时任务
     * 
     * @param taskScheduler 定时任务
     * @return 结果
     */
    public int updateTaskScheduler(TaskScheduler taskScheduler);

    /**
     * 删除定时任务
     * 
     * @param id 定时任务ID
     * @return 结果
     */
    public int deleteTaskSchedulerById(Integer id);

    /**
     * 批量删除定时任务
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTaskSchedulerByIds(String[] ids);

    /**
     * 查询定时任务日志 列表
     *
     * @param id 定时任务id
     * @return 定时任务日志集合
     */
    public List<Map<String,Object>> selectTaskSchedulerLogList(Integer id);

    /**
     * 查询定时任务
     *
     * @param tenantId 区域ID
     * @return 定时任务
     */
    List<TaskScheduler> selectTaskSchedulerByTenantId(@Param("tenantId") Integer tenantId);

    /**
     * 批量插入
     *
     * @param taskScheduler
     * @return
     */
    Integer insertBatch(List<TaskScheduler> taskScheduler);

    /**
     * 删除
     * @return
     */
    Integer delete();
}
