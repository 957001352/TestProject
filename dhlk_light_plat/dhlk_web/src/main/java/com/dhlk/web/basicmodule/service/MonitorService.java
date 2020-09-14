package com.dhlk.web.basicmodule.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.MonitorDetail;
import com.dhlk.web.basicmodule.service.fbk.MonitorServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "basicmodule-service/monitor", fallback = MonitorServiceFbk.class)
public interface MonitorService {

    @RequestMapping(value = "/indexData")
    Result indexList(MonitorDetail detail);
}