package com.dhlk.service;

import com.dhlk.domain.Result;
import org.hyperic.sigar.SigarException;

/**
 * 资源监控页面接口
 */
public interface SourceMonitoringService {
    // 获取应用运行状态接口
    Result getAppRunStatus();
    Result getCPUUsageRate() throws SigarException;
    // 内存使用情况
    Result getMemUsageRate() throws SigarException;
    // 磁盘使用情况
    Result getDiskUsageRate() throws SigarException;
    //网卡信息
    Result getNetworkInfo() throws SigarException;
}
