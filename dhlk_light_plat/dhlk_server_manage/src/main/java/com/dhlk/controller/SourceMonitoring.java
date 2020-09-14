package com.dhlk.controller;

import com.dhlk.domain.Result;
import com.dhlk.service.SourceMonitoringService;
import org.hyperic.sigar.SigarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 资源监控页
 */
@CrossOrigin
@RestController
@RequestMapping("/sourcemonitoring")
public class SourceMonitoring {
    @Autowired
    SourceMonitoringService sourceMonitoringService;

    /**
     * 获取应用运行状态信息
     *
     * @return
     */
    @GetMapping("/getAppRunInfo")
    public Result getAppRunInfo() {
        return sourceMonitoringService.getAppRunStatus();
    }

    /**
     * 获取cpu相关信息
     */
    @GetMapping("/getCPUInfo")
    public Result getCPUInfo() throws SigarException {
        return sourceMonitoringService.getCPUUsageRate();
    }

    /**
     * 获取系统内存使用信息
     *
     * @return
     */
    @GetMapping("/getMemoryInfo")
    public Result getMemoryInfo() throws SigarException {
        return sourceMonitoringService.getMemUsageRate();
    }


    /**
     * 获取系统磁盘信息
     *
     * @return
     */
    @GetMapping("/getDiskInfo")
    public Result getDiskInfo() throws SigarException {
        return sourceMonitoringService.getDiskUsageRate();
    }

    /**
     * 获取网卡信息
     *
     * @return
     */
    @GetMapping("/getNetworkCardInfo")
    public Result getNetworkCardInfo() throws SigarException {
        return sourceMonitoringService.getNetworkInfo();
    }
}
