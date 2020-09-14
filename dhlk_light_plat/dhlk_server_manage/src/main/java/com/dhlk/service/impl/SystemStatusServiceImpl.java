package com.dhlk.service.impl;

import com.dhlk.common.SystemRuntime;
import com.dhlk.domain.Disk;
import com.dhlk.domain.SystemStatus;
import com.dhlk.service.GetSysStateService;
import com.dhlk.service.SystemStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DecimalFormat;
import java.util.Enumeration;

@Service
@Slf4j
public class SystemStatusServiceImpl extends UnicastRemoteObject implements SystemStatusService {
    @Autowired
    private GetSysStateService getSysStateService;

    public SystemStatusServiceImpl() throws RemoteException{
        super();
        // TODO Auto-generated constructor stub
    }
    private static final long serialVersionUID = 1L;
    @Override
    public SystemStatus getRuntime(){
        SystemStatus sp = new SystemStatus();
        SystemRuntime systemRuntime = new SystemRuntime();

        DecimalFormat df = new DecimalFormat("#.##");
        try {
            //获取cpu使用率
//            CpuPerc cpu = systemRuntime.cpu();
//            String combined = df.format(cpu.getCombined()*100d);
            log.info((int) Math.round(SystemRuntime.getCPURate())+"获取cpu使用率");
            sp.setCpu_combined((int) Math.round(SystemRuntime.getCPURate()));

            //获取内存使用率
//            Mem memory = systemRuntime.memory();
//
//            String calculation = CalculationUtil.calculation(memory.getUsed(), memory.getTotal());
            String memUsage = SystemRuntime.getMemUsage();
            log.info(memUsage+"获取内存使用率");
            sp.setMemory_combined(Float.parseFloat(SystemRuntime.Memory()));


            //获取磁盘占用
            Disk file = systemRuntime.file();

            sp.setDisk(file);
            //获取网卡速率
            double[] net = systemRuntime.net();
            sp.setNet_speed(net);

            //获取系统信息
            sp.setSystemInfo(getSysStateService.getSysState());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sp;
    }








    public static String CompIp() {
        String ip = "";
        Enumeration<NetworkInterface> IFaces = null;
        try {
            IFaces = NetworkInterface.getNetworkInterfaces();
            while (IFaces.hasMoreElements()) {
                NetworkInterface fInterface = IFaces.nextElement();
                if (!fInterface.isVirtual() && !fInterface.isLoopback() && fInterface.isUp()) {
                    Enumeration<InetAddress> adds = fInterface.getInetAddresses();
                    while (adds.hasMoreElements()) {
                        InetAddress address = adds.nextElement();
                        byte[] bs = address.getAddress();
                        if (bs.length == 4)
                            ip += address.getHostAddress() + ",";
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ip.substring(0, ip.length() - 1);
    }
}
