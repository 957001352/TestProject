package com.dhlk.light.factory.controller;

import com.dhlk.domain.Result;
import com.dhlk.light.factory.service.MonitoringLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitoringLog")
public class MonitoringLogController {
    @Autowired
    private MonitoringLogService monitoringLogService;

    @GetMapping("/findPageList")
    public Result findList(@RequestParam(value ="sn",required = false) String sn,
                           @RequestParam(value ="status",required = false) String status,
                           @RequestParam(value ="startime",required = false) String startime,
                           @RequestParam(value ="endtime",required = false) String endtime,
                           @RequestParam(value ="pageNum",required = false) Integer pageNum,
                           @RequestParam(value ="pageSize",required = false) Integer pageSize){
        return monitoringLogService.findList(sn,status,startime,endtime,pageNum,pageSize);
    }
}
