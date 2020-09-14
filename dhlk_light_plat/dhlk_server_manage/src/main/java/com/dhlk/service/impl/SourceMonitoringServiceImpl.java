package com.dhlk.service.impl;

import com.dhlk.common.SystemRuntime;
import com.dhlk.domain.*;
import com.dhlk.service.SourceMonitoringService;
import com.dhlk.utils.LinuxCommandUtils;
import com.dhlk.utils.ResultUtils;
import org.hyperic.sigar.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SourceMonitoringServiceImpl implements SourceMonitoringService {
    SystemRuntime systemRuntime = new SystemRuntime();

    @Override
    public Result getAppRunStatus() {
        String[] cmd = {"/bin/sh", "-c", "ps -aux"};
        List<String> list = LinuxCommandUtils.exec(cmd);
        for (int i = 0; i < list.size(); i++) {
            String[] list1 = strSpilt(list.get(i));
            if (!(list1[7].contains("R") || list1[7].contains("S"))) {
                list.remove(i);
            }
        }
        List<ServerListInfo> serverList = new ArrayList();

        // 保存每个服务列表的索引以及相关信息, [index, servername, cpuusage, mem, networkIO, diskIO]
        for (int i = 1; i < list.size(); i++) {
            String[] list1 = strSpilt(list.get(i));

            ServerListInfo serverListInfo = new ServerListInfo();
            String command = "";
            for (int j = 10; j < list1.length; j++) {
                command = command + list1[j];
            }
            String finalCommand = (command.length() > 70) ? command.substring(0, 70) : command;
            serverListInfo.setCommand(finalCommand);
            serverListInfo.setCpuUsage(list1[2] + "%");
            serverListInfo.setMem(Float.parseFloat(list1[3]) + "%");
            serverListInfo.setRss(Long.parseLong(list1[5]) / 1024L + "Mb");
            serverListInfo.setStart(list1[8]);
            serverListInfo.setUser(list1[0]);
            serverListInfo.setVsz(Long.parseLong(list1[4]) / 1024L + "Mb");
            serverListInfo.setTime(list1[9]);

            serverList.add(serverListInfo);
        }

        Collections.sort(serverList);

        return ResultUtils.success(serverList);
    }

    @Override
    public Result getCPUUsageRate() throws SigarException {
        CpuInfo[] infos = systemRuntime.cpuPerc();
        List cpuListInfo = new ArrayList();
        CpuPerc[] cpuPercList = systemRuntime.cpuPercList();

        for (int i = 0; i < infos.length; i++) {
            CpuPerc cpu = cpuPercList[i];
            CpuInfo info = infos[i];
            int finalI = i;
            CPUInfo cpuInfo = new CPUInfo() {{
                this.setCpuNum(finalI + 1);
                this.setCpuTotal(info.getMhz());
                this.setCpuBrand(info.getVendor());
                this.setCpuType(info.getModel());
                this.setCpuUserUsageRate(CpuPerc.format(cpu.getUser())); // 获取总的使用率
                this.setCpuSysUsageRate(CpuPerc.format(cpu.getSys())); // 获取系统使用率
                this.setCpuErrRate(CpuPerc.format(cpu.getNice())); //获取错误率
                this.setCpuFreeRate(CpuPerc.format(cpu.getIdle())); //获取空闲率
                this.setCpuWaitRate(CpuPerc.format(cpu.getWait())); // 获取cpu等待率
                this.setCpuUsageRate(CpuPerc.format(cpu.getCombined()));//获取cpu总的使用率
            }};
            cpuListInfo.add(cpuInfo);
        }

        return ResultUtils.success(cpuListInfo);
    }

    @Override
    public Result getMemUsageRate() throws SigarException {
        Mem mem = systemRuntime.memory();
        Swap swap = systemRuntime.memoryExchane();
        MemoryInfo memoryInfo = new MemoryInfo() {{
            this.setMemTotal(mem.getTotal() / 1024L / 1024L / 1024L + "G av"); // 内存总量
            this.setMemUsed(mem.getUsed() / 1024L / 1024L / 1024L + "G used"); // 当前内存使用量
            this.setMemFree(mem.getFree() / 1024L / 1024L / 1024L + "G av"); //当前内存剩余量
            this.setMemExchangeAreaTotal(swap.getTotal() / 1024L / 1024L / 1024L + "G av"); //交换区总量
            this.setMemExchangeAreaUsage(swap.getUsed() / 1024L / 1024L / 1024L + "G used"); // 交换区使用量
            this.setMemExchangeAreaFree(swap.getFree() / 1024L / 1024L / 1024L + "G free"); // 交换区剩余量
        }};

        return ResultUtils.success(memoryInfo);
    }

    @Override
    public Result getDiskUsageRate() throws SigarException {
        List<Disk> fsList = new ArrayList();
        String[] cmd = {"/bin/sh", "-c", "df -h"};
        List<String> list = LinuxCommandUtils.exec(cmd);
        for (int i = 1; i < list.size(); i++) {
            String[] strings = strSpilt(list.get(i));
            Disk disk = new Disk() {{
                this.setDevName(strings[5]);
                this.setDiskTotal(strings[1]);
                this.setDiskAvail(strings[3]);
                this.setUseage(strings[2]);
                this.setDiskAvailRate(strings[4]);
            }};
            fsList.add(disk);
        }
        return ResultUtils.success(fsList);
    }

    @Override
    public Result getNetworkInfo() throws SigarException {
        Sigar sigar = new Sigar();
        String[] ifaces = sigar.getNetInterfaceList();
        List<NetworkCardInfo> nicInfoList = new ArrayList<>(); // 存放网卡信息列表
        for (int i = 0; i < ifaces.length; i++) {
            NetworkCardInfo networkCardInfo = new NetworkCardInfo();
            NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
            if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress()) || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
                    || NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
                continue;
            }

            NetInterfaceStat ifstat = sigar.getNetInterfaceStat(ifaces[i]);
            networkCardInfo.setNicName(ifaces[i]); //网卡名
            networkCardInfo.setIPAddr(cfg.getAddress()); // IP
            networkCardInfo.setNicMACAddr(cfg.getHwaddr()); //MAC地址
            networkCardInfo.setReceiveTotal(ifstat.getRxPackets()); // 接收的总包裹数
            networkCardInfo.setSendTotal(ifstat.getTxPackets()); //发送的总包裹数
            networkCardInfo.setSendTotalThrow(ifstat.getTxDropped()); //发送时丢弃的包数
            networkCardInfo.setReceieveTotalThrow(ifstat.getRxDropped()); //接收时丢弃的包数
            networkCardInfo.setSendTotalError(ifstat.getTxErrors()); // 发送时的错误包数
            networkCardInfo.setReceiveTotalError(ifstat.getRxErrors()); //接收时错误的包数

            nicInfoList.add(networkCardInfo);
        }
        return ResultUtils.success(nicInfoList);
    }

    // 字符串切割
    public String[] strSpilt(String string) {
        boolean flag = true;
        while (flag) {
            string = string.replaceAll("  ", " ");
            int i1 = string.indexOf("   ");
            if (i1 < 1) {
                flag = false;
            }
        }
        String[] strings = string.split("\\s+");//["root", "11", "0.0", "0.0", "0", "0", "?", "S", "5月12", "0:18", "[watchdog/0]"]
        return strings;
    }
}
