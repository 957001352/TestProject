package com.dhlk.web.light.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.TaskScheduler;
import com.dhlk.web.light.service.fbk.CompanyListServiceFbk;
import com.dhlk.web.light.service.fbk.SwitchServiceFbk;
import com.dhlk.web.light.service.fbk.TaskSchedulerServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 定时任务Service接口
 * 
 * @author jlv
 * @date 2020-06-04
 */
@FeignClient(value = "light-service/scheduler", fallback = TaskSchedulerServiceFbk.class)
public interface TaskSchedulerService
{
    /**
     * 查询定时任务
     * 
     * @param id 定时任务ID
     * @return 定时任务
     */
   // public Result selectTaskSchedulerById(Integer id);

    /**
     * 查询定时任务列表
     *
     * @return 定时任务集合
     */
    @GetMapping("/findTaskSchedulerList")
    Result selectTaskSchedulerList(@RequestParam(value = "name", required = false) String name,@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize);

    /**
     * 新增定时任务
     * 
     * @param taskScheduler 定时任务
     * @return 结果
     */
    @PostMapping("/saveTask")
    Result saveTaskScheduler(@RequestBody TaskScheduler taskScheduler);


    /**
     * 批量删除定时任务
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @GetMapping( "/deleteTask")
    Result deleteTaskSchedulerByIds(@RequestParam("ids")String ids);

    /**
     * 删除定时任务信息
     * 
     * @param id 定时任务ID
     * @return 结果
     */
   // public Result deleteTaskSchedulerById(Integer id);
    /**
     * 开启定时任务
     *
     * @param taskSchedulerId 定时任务ID
     * @return 结果
     */
    @RequestMapping("/startTask")
    Result startTaskScheduler(@RequestParam("taskSchedulerId") Integer taskSchedulerId) throws Exception;
    /**
     * 停止定时任务
     *
     * @param taskSchedulerId 定时任务ID
     * @return 结果
     */
    @RequestMapping("/stopTask")
    Result stopTaskScheduler(@RequestParam("taskSchedulerId")Integer taskSchedulerId) throws Exception ;
    /**
     * 查询定时任务日志列表
     *
     * @param taskSchedulerId 定时任务ID
     * @return 结果
     */
    @GetMapping( "/selectTaskSchedulerLogList")
    Result selectTaskSchedulerLogList(@RequestParam("taskSchedulerId")Integer taskSchedulerId);

    /**
     * 修改定时任务状态
     */
    @GetMapping("/updateSchedulerStatus")
    Result updateSchedulerStatus( @RequestParam("schedulerId") String schedulerId,
                                  @RequestParam("status") String status);
}
