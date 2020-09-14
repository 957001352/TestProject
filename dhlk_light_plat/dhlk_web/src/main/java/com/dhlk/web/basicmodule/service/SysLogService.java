package com.dhlk.web.basicmodule.service;

import com.dhlk.web.basicmodule.service.fbk.SysLogServiceFbk;
import com.dhlk.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: jzhao
 * @Date: 2020/4/13 16:45
 * @Description:
 */
@FeignClient(value = "basicmodule-service/logFile", fallback = SysLogServiceFbk.class)
public interface SysLogService {
    @GetMapping(value = "/searchLogFile")
    Result searchLogFile();
}
