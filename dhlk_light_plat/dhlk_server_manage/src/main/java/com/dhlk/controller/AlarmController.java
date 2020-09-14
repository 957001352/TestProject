package com.dhlk.controller;

import com.dhlk.domain.AlarmInfo;
import com.dhlk.domain.AlarmRule;
import com.dhlk.domain.Result;
import com.dhlk.service.AlarmService;
import com.dhlk.utils.MyPath;
import com.dhlk.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


/**
 * 报警信息
 */
@RestController
@RequestMapping("/alarm")
@CrossOrigin
public class AlarmController {
    @Autowired
    private AlarmService alarmService;
    @Value("${jarname}")
    private String jarName;
    @PostMapping("setAlarmRule")
    public Result setAlarmRule(@RequestBody AlarmRule alarmRule){
        return alarmService.setAlarmRule(alarmRule);
    }

    @GetMapping("getAlarmInfo")
    public Result getAlarmInfo(@RequestParam(value = "type",required = false) String type,
                               @RequestParam(value = "createTime",required = false) Long createTime,
                               @RequestParam(value = "pageNow",defaultValue = "1") Integer pageNow,
                               @RequestParam(value = "limit",defaultValue = "10") Integer limit){
        return alarmService.getAlarmInfo(type,createTime,pageNow,limit);
    }

    @GetMapping("getAlarmRule")
    public Result getAlarmRule(){
        return alarmService.getAlarmRule();
    }

    @PostMapping("deleteAlarmInfo")
    public Result deleteAlarmInfo(@RequestBody AlarmInfo alarmInfo){
        return alarmService.deleteAlarmInfo(alarmInfo);
    }

    @PostMapping("aadddd")
    public Result aadddd(){
        return ResultUtils.success("ProjectPath:=====>"+ getPath()+"====>RealPath:=====>"+MyPath.getProjectPath());
    }

    public String getPath(){
        String projectPath = MyPath.getProjectPath();
        String substring = projectPath.substring(projectPath.indexOf(":") + 1, projectPath.indexOf(jarName));
        return substring;
    }
}
