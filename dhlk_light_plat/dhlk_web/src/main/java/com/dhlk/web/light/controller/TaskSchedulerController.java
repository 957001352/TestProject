package com.dhlk.web.light.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.TaskScheduler;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.TaskSchedulerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


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
    public Result startTaskScheduler(@RequestParam("taskSchedulerId") Integer taskSchedulerId) throws Exception {
        // 开启定时任务，对象注解Scope是多例的。
        return taskSchedulerService.startTaskScheduler(taskSchedulerId);

    }
    @ApiOperation("停止定时任务")
    @GetMapping("/stopTask")
    public Result stopTaskScheduler(@RequestParam("taskSchedulerId")Integer taskSchedulerId) throws Exception {
        // 提前测试用来测试线程1进行对比是否关闭。
        return taskSchedulerService.stopTaskScheduler(taskSchedulerId);
    }

    /**
     * 查询定时任务列表
     */
    //@RequiresPermissions("system:scheduler:list")
    @ApiOperation("查询定时任务列表")
    @GetMapping("/findTaskSchedulerList")
public Result findTaskSchedulerList(@RequestParam(value = "name", required = false) String name,@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize)
{
    return taskSchedulerService.selectTaskSchedulerList(name,pageNum, pageSize);
}


    /**
     * 新增保存定时任务
     */
    //@RequiresPermissions("system:scheduler:add")
    @ApiOperation("新增/修改定时任务")
    @PostMapping("/saveTask")
    public Result saveTaskScheduler(@Valid @RequestBody TaskScheduler taskScheduler, BindingResult bindingResult)
    {
        Result result = ResultUtils.loadResult(bindingResult);
        if (result == null) {
            return taskSchedulerService.saveTaskScheduler(taskScheduler);
        }
        return result;
    }
    /**
     * 删除定时任务
     */
    //@RequiresPermissions("system:scheduler:remove")
    @ApiOperation("删除定时任务")
    @GetMapping( "/deleteTask")
    public Result delete(@RequestParam(value = "ids")String ids)
    {
        return taskSchedulerService.deleteTaskSchedulerByIds(ids);
    }
    @ApiOperation("查询定时任务日志列表")
    @GetMapping( "/selectTaskSchedulerLogList")
    public Result selectTaskSchedulerLogList(@RequestParam(value = "taskSchedulerId")Integer taskSchedulerId){
        return taskSchedulerService.selectTaskSchedulerLogList(taskSchedulerId);
    }

    /**
     * 修改定时任务状态
     */
    @ApiOperation("修改定时任务状态")
    @GetMapping("/updateSchedulerStatus")
    public Result updateSchedulerStatus(@RequestParam("schedulerId") String schedulerId,
                                        @RequestParam("status") String status) {
        return taskSchedulerService.updateSchedulerStatus(schedulerId, status);
    }
}
