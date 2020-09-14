package com.dhlk.service.impl;

import com.dhlk.domain.SystemInfo;
import com.dhlk.service.GetSysStateService;
import com.dhlk.utils.LinuxCommandUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 获取系统信息:
 *  cpu 使用率
 *  内存使用率
 *  磁盘占用情况
 *  磁盘读写速率
 *  网卡速率
 *  系统信息：版本，开机时间
 *
 */
@Service
public class GetSysStateImpl  implements GetSysStateService {

    @Override
    public SystemInfo getSysState() {
        // 计算系统开机时间
        List<String> sysInfo = LinuxCommandUtils.exec(new String[]{"/bin/sh", "-c","cat /proc/uptime| awk -F. '{run_days=$1 / 86400;run_hour=($1 % 86400)/3600;run_minute=($1 % 3600)/60;run_second=$1 % 60;printf(\"%d-%d-%d-%d\",run_days,run_hour,run_minute,run_second)}'"});
        String day = "0";
        String hour = "0";
        String min = "0";
        if(sysInfo != null){
            //系统已运行：0天1时8分4秒
            String[] split = sysInfo.get(0).split("-");
            if(split != null){
                day = split[0].trim();
                hour = split[1].trim();
                min = split[2].trim();
            }
        }
        // 获取系统版本情况
        List<String> exec = LinuxCommandUtils.exec(new String[]{"/bin/sh", "-c","cat /etc/redhat-release"});
        String sysVersion = exec.get(0);

        return new SystemInfo("丝路启航BI一体机",sysVersion,day,hour,min);
    }
}
