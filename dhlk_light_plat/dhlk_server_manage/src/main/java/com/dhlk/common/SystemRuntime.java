package com.dhlk.common;

import com.dhlk.domain.Disk;
import com.dhlk.utils.CalculationUtil;
import org.hyperic.sigar.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析出cpu，内存，磁盘灯信息
 */
public class SystemRuntime {
    private Sigar sigar = null;
    public void update(){
        sigar = new Sigar();
    }
    
    public SystemRuntime(){
        sigar = new Sigar();
    }
    // 内存
    public Mem memory() throws SigarException {
        Mem mem = sigar.getMem();
        return mem;
    }

    // 内存交换区
    public Swap memoryExchane() throws SigarException {
        Swap swap = sigar.getSwap();
        return swap;
    }

    //cpu
    public CpuPerc cpu() throws SigarException {
        CpuPerc perc = sigar.getCpuPerc();
        return perc;
    }

    //cpuinfos
    public CpuInfo[] cpuPerc() throws SigarException {
        CpuInfo infos[] = sigar.getCpuInfoList();
        return infos;
    }

    //cpuList
    public CpuPerc[] cpuPercList() throws SigarException {
        CpuPerc cpuList[] = null;
        cpuList = sigar.getCpuPercList();
        return cpuList;
    }


    public Disk file() throws Exception {
        Disk disk = new Disk();
        long total = 0;
        long used = 0;
        float diskReads = 0;
        float diskWrites = 0;
        FileSystem fslist[] = sigar.getFileSystemList();
        for (int i = 0; i < fslist.length; i++) {
            FileSystem fs = fslist[i];
            FileSystemUsage usage = null;
            usage = sigar.getFileSystemUsage(fs.getDirName());
            switch (fs.getType()) {
                case 0: // TYPE_UNKNOWN ：未知
                    break;
                case 1: // TYPE_NONE
                    break;
                case 2: // TYPE_LOCAL_DISK : 本地硬盘
                    // 文件系统总大小
                    total += usage.getTotal();
                    // 文件系统使用量
                    used += usage.getUsed();
                    break;
                case 3:// TYPE_NETWORK ：网络
                    break;
                case 4:// TYPE_RAM_DISK ：闪存
                    break;
                case 5:// TYPE_CDROM ：光驱
                    break;
                case 6:// TYPE_SWAP ：页面交换
                    break;
            }
//            diskReads += usage.getDiskReads();
//            diskWrites += usage.getDiskWrites();
        }
        String calculation = CalculationUtil.calculation(used, total);
        disk.setUsePercent(calculation);

        long readSpeed  = getTotalDiskReadByte();
        long writeSpeed = getTotalDiskWriteByte();
        Thread.sleep(1000);
        update();
        float rs = (getTotalDiskReadByte() - readSpeed);
        float ws = (getTotalDiskWriteByte() - writeSpeed);
        BigDecimal r = new BigDecimal(rs /1024 /1024);
        BigDecimal w = new BigDecimal(ws /1024 /1024);
        diskReads = r.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        diskWrites = w.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

//        System.out.println(diskReads+"=================================>read");
//        System.out.println(diskWrites+"=================================>write");

        disk.setDisk_reads(diskReads);
        disk.setDisk_writes(diskWrites);
        return disk;
    }


    public double[] net() throws Exception{
        double[] result = {0,0};
        String[] oBytes = getOBytes();
        long time  = System.currentTimeMillis();
        Thread.sleep(1000);
        update();
        String[] oBytesAfter = getOBytes();
        Double r = Double.parseDouble(oBytesAfter[0])-Double.parseDouble(oBytes[0]);
        Double t = Double.parseDouble(oBytesAfter[1])-Double.parseDouble(oBytes[1]);
        DecimalFormat df = new DecimalFormat("#.00");
        String rx = df.format(r);
        String tx = df.format(t);
        result[0] = Double.parseDouble(rx);
        result[1] = Double.parseDouble(tx);
//        float[] result = {0f,0f};
//        update();
//        long time  = System.currentTimeMillis();
//
//        List<NetInterfaceStat> netInterfaceStats = netBytes();
//        long rx = 0;
//        long tx = 0;
//        for (NetInterfaceStat netInterfaceStat:netInterfaceStats) {
//            rx += netInterfaceStat.getRxBytes();
//            tx += netInterfaceStat.getTxBytes();
//        }
//        Thread.sleep(1000);
//        update();
//        time  = System.currentTimeMillis()-time;
//        List<NetInterfaceStat> netInterfaceStats1 = netBytes();
//        long rxNow = 0;
//        long txNow = 0;
//        for (NetInterfaceStat netInterfaceStat1:netInterfaceStats1) {
//            rxNow += netInterfaceStat1.getRxBytes();
//            txNow += netInterfaceStat1.getTxBytes();
//        }
//        rx = rxNow-rx;
//        tx = txNow-tx;
//
//        result[0] = rx*1f/1024/time;// kb/s
//        result[1] = tx*1f/1024/time;

        return result;
    }

    public List<NetInterfaceStat> netBytes() throws Exception {

        List<NetInterfaceStat> netInterfaceStats = new ArrayList<>();
        String ifNames[] = sigar.getNetInterfaceList();
        NetInterfaceStat result = null;
        for (int i = 0; i < ifNames.length; i++) {
            String name = ifNames[i];
            NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
            result = sigar.getNetInterfaceStat(name);
            netInterfaceStats.add(result);
        }
        return netInterfaceStats;
    }



    public static Double getCPURate() {
        Double CPURate = 0.0;
        Double CPURate1 = 0.0;
        try {
            Sigar sigar = new Sigar();
            CpuInfo infos[] = new CpuInfo[0];
            infos = sigar.getCpuInfoList();
            CpuPerc cpuList[] = null;
            cpuList = sigar.getCpuPercList();
            for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用
                CpuInfo info = infos[i];
                CPURate += cpuList[i].getCombined();
            }
            CPURate1 = CPURate / infos.length;
        } catch (SigarException e) {
            e.printStackTrace();
        }
        CPURate1 = (double) Math.round(CPURate1 * 100);
        return CPURate1;
    }

    private static String osName = System.getProperty("os.name");
    /***
     *  获取内存使用率
     */
    public static String Memory(){
        if (osName.toLowerCase().contains("windows") || osName.toLowerCase().contains("win")) {
            String memory = getMemery();
            return memory;
        } else {
            String memory = getMemUsage()+"";
            return memory;
        }
    }
    /**
     * 获取windows内存使用率
     */
    public static String getMemery() {
        String format = "";
        try {
            DecimalFormat df = new DecimalFormat("#.00");
            Sigar sigar = new Sigar();
            Mem mem = sigar.getMem();
            float total = mem.getTotal() / 1024L;
            float used = mem.getUsed() / 1024L;
            format = df.format((used/total)*100);
            return  format;
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return  format;
    }
    /***
     * 获取linux内存使用率
     */
    public static String getMemUsage() {
        Map<String, Object> map = new HashMap<String, Object>();
        InputStreamReader inputs = null;
        BufferedReader buffer = null;
        try {
            inputs = new InputStreamReader(new FileInputStream("/proc/meminfo"));
            buffer = new BufferedReader(inputs);
            String line = "";
            while (true) {
                line = buffer.readLine();
                if (line == null)
                    break;
                int beginIndex = 0;
                int endIndex = line.indexOf(":");
                if (endIndex != -1) {
                    String key = line.substring(beginIndex, endIndex);
                    beginIndex = endIndex + 1;
                    endIndex = line.length();
                    String memory = line.substring(beginIndex, endIndex);
                    String value = memory.replace("kB", "").trim();
                    map.put(key, value);
                }
            }
            long memTotal = Long.parseLong(map.get("MemTotal").toString());
            long memFree = Long.parseLong(map.get("MemFree").toString());
            long memused = memTotal - memFree;
            long buffers = Long.parseLong(map.get("Buffers").toString());
            long cached = Long.parseLong(map.get("Cached").toString());
            double usage = (double) (memused-buffers-cached) / memTotal * 100;
            DecimalFormat df = new DecimalFormat("#.00");
            String format = df.format(usage);
            return format;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
                inputs.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return "0";
    }

    /**
     * 获取磁盘Read吞吐量
     *
     * @return
     */
    public static long getTotalDiskReadByte() {
        Sigar sigar = new Sigar();
        long totalByte = 0;
        try {
            FileSystem[] fslist = sigar.getFileSystemList();
            for (int i = 0; i < fslist.length; i++) {
                if (fslist[i].getType() == 2) {
                    FileSystemUsage usage = sigar.getFileSystemUsage(fslist[i].getDirName());
                    totalByte += usage.getDiskReadBytes();
                }
            }
        } catch (SigarException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return totalByte;
    }
    /**
     * 获取磁盘Write吞吐量
     *
     * @return
     */
    public static long getTotalDiskWriteByte() {
        Sigar sigar = new Sigar();
        long totalByte = 0;
        try {
            FileSystem[] fslist = sigar.getFileSystemList();
            for (int i = 0; i < fslist.length; i++) {
                if (fslist[i].getType() == 2) {
                    FileSystemUsage usage = sigar.getFileSystemUsage(fslist[i].getDirName());
                    totalByte += usage.getDiskWriteBytes();
                }
            }
        } catch (SigarException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return totalByte;
    }


    /**
     * @Description: 系统上传下载总量
     * @Date:
     */
    public static String[] getOBytes() {
        Sigar sigar = new Sigar();
        String ifNames[] = null;
        String[] arr = new String[2];

        long txbyte = 0;
        long rxbyte = 0;
        try {
            //获取网卡名称
            ifNames = sigar.getNetInterfaceList();
            for (int i = 0; i < ifNames.length; i++) {
                String name = ifNames[i];
                NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);//fasong
                long txBytes = ifstat.getTxBytes();
                long rxBytes = ifstat.getRxBytes();
                if (txBytes != 0 && !"lo".equals(name)) {
                    txbyte += txBytes;
                    rxbyte += rxBytes;

                }
            }
            arr[0] = bytes2kb(rxbyte);
            arr[1] = bytes2kb(txbyte);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return arr;
    }

    //long byte转Kb
    public static String bytes2kb(long bytes) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format((double) bytes / 1024);
    }
}
