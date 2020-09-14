package com.dhlk.web.basicmodule.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.MonitorDetail;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.basicmodule.service.MonitorService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/monitor")
@Api(description = "监控管理")
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    @GetMapping("/indexData")
    public Result indexData(MonitorDetail detail) {
        return monitorService.indexList(detail);
    }
}