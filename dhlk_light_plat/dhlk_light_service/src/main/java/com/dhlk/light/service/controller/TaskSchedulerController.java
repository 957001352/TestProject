package com.dhlk.light.service.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.TaskScheduler;
import com.dhlk.light.service.service.TaskSchedulerService;
import com.dhlk.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 定时任务Controller
 * 
 * @author ruoyi
 * @date 2020-06-04
 */
@RestController
@RequestMapping("/scheduler")
@Api(value = "TaskSchedulerController", description = "定时任务")
public class TaskSchedulerController
{
    @Autowired
    private TaskSchedulerService taskSchedulerService;

    @ApiOperation("开启定时任务")
    @GetMapping("/startTask")
    @RequiresPermissions("scheduler:startTask")
    public Result startTaskScheduler(@RequestParam("taskSchedulerId") Integer taskSchedulerId){
        // 开启定时任务，对象注解Scope是多例的。
        return taskSchedulerService.startTaskScheduler(taskSchedulerId);

    }
    @ApiOperation("停止定时任务")
    @GetMapping("/stopTask")
    @RequiresPermissions("scheduler:stopTask")
    public Result stopTaskScheduler(@RequestParam("taskSchedulerId")Integer taskSchedulerId) throws Exception {
        // 提前测试用来测试线程1进行对比是否关闭。
        return taskSchedulerService.stopTaskScheduler(taskSchedulerId);
    }

    /**
     * 查询定时任务列表
     */
    @ApiOperation("查询定时任务列表")
    @GetMapping("/findTaskSchedulerList")
    @RequiresAuthentication
    public Result findTaskSchedulerList(@RequestParam(value = "name", required = false) String name,
                                        @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return taskSchedulerService.selectTaskSchedulerList(name, pageNum, pageSize);
    }

    /**
     * 新增保存定时任务
     */
    @ApiOperation("新增/修改定时任务")
    @PostMapping("/saveTask")
    @RequiresPermissions("scheduler:save")
    public Result saveTaskScheduler(@RequestBody TaskScheduler taskScheduler)
    {
        return taskSchedulerService.saveTaskScheduler(taskScheduler);
    }
    /**
     * 删除定时任务
     */
    @ApiOperation("删除定时任务")
    @GetMapping( "/deleteTask")
    @RequiresPermissions("scheduler:delete")
    public Result delete(@RequestParam("ids")String ids)
    {
        return taskSchedulerService.deleteTaskSchedulerByIds(ids);
    }

    @ApiOperation("查询定时任务日志列表")
    @GetMapping( "/selectTaskSchedulerLogList")
    @RequiresAuthentication
    public Result selectTaskSchedulerLogList(@RequestParam(value = "taskSchedulerId")Integer taskSchedulerId,@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize){
        return taskSchedulerService.selectTaskSchedulerLogList(taskSchedulerId,pageNum, pageSize);
    }

    /**
     * 修改定时任务状态
     */
    @ApiOperation("修改定时任务状态")
    @GetMapping("/updateSchedulerStatus")
    @RequiresAuthentication
    public Result updateSchedulerStatus(@RequestParam("schedulerId") String schedulerId,
                                        @RequestParam("status") String status) {
        return taskSchedulerService.updateSchedulerStatus(schedulerId, status);
    }
}
