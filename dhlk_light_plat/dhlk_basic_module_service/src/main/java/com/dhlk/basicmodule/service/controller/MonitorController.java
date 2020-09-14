package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.IMonitorService;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.MonitorDetail;
import com.dhlk.utils.ResultUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/monitor")
public class MonitorController {

    @Autowired
    private IMonitorService monitorService;

    /**
     * 获取监控的数据
     *
     * @return
     */
    @RequestMapping("/indexData")
    @RequiresPermissions("monitor:index")
    public Result findMonitorList() {
        MonitorDetail monitorDetail = new MonitorDetail();
        return ResultUtils.success(monitorService.indexList(monitorDetail));
    }
}