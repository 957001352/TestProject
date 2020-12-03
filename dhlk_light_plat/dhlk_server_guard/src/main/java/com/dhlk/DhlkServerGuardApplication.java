package com.dhlk;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import utils.LinuxCommandUtils;
import utils.StringUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class DhlkServerGuardApplication {
    static Log log = LogFactory.get();

    public static void main(String[] args) {

        if (args == null || args.length == 0) {
            //守护进程
            defaultExe();
        } else if (args.length == 1 && "1".equals(args[0])) {
            //杀死所有进程，执行格式：java -jar dhlk_server_guard.jar 1
            killAllApplication();
        } else if (args.length == 1 && "2".equals(args[0])) {
            //启动全部配置在properties中的应用，执行格式：java -jar dhlk_server_guard.jar 2
            defaultExe();
        } else if (args.length == 2 && "3".equals(args[0]) && args[1] != null && !"".equals(args[1])) {  //杀死某一个
            //杀死配置在properties某一个指定应用，执行格式：java -jar dhlk_server_guard.jar 3 dhlk_light_factory
            stopApplication(args[1]);
        } else {
            //默认守护进程
            defaultExe();
        }

    }


    /***
     * 守护进程
     */
    public static void defaultExe() {
        log.info("进入了守护进程");
        DhlkServerGuardApplication guardProcess = new DhlkServerGuardApplication();
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Set<Object> keys = guardProcess.readProp();

            if (keys != null) {
                for (Object o : keys) {
                    //服务是否启动
                    if (!applicationStatus(String.valueOf(o))) {
                        startApp(guardProcess.findPropValus(String.valueOf(o)));
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * 杀死所有进程
     */
    public static void killAllApplication() {
        DhlkServerGuardApplication guardProcess = new DhlkServerGuardApplication();
        //获取配置properties中的应用
        Set<Object> keys = guardProcess.readProp();
        if (keys != null) {
            for (Object appName : keys) {
                stopApplication(String.valueOf(appName));
            }
        }
    }


    /**
     * 获取服务运行状体
     *
     * @param appName
     * @return
     */

    public static boolean applicationStatus(String appName) {
        if (!StringUtils.isNullOrEmpty(appName)) {
            String[] wedCmds = {"/bin/sh", "-c", "ps -eo pid,lstart,cmd | grep " + appName + " | grep -v grep"};
            List<String> exec = LinuxCommandUtils.exec(wedCmds);
            if (exec != null && exec.size() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 停止指定名称的服务
     *
     * @param appName
     * @return
     */

    public static boolean stopApplication(String appName) {
        String pid = getPid(appName);
        String[] wedCmds = {"/bin/sh", "-c", "kill -9 " + pid};
        List<String> exec = LinuxCommandUtils.exec(wedCmds);
        if (exec == null || exec.size() <= 0) {
            log.info("停止" + appName + "应用成功");
            return true;
        }
        log.info(appName + "停止失败！！请检查");
        return false;
    }

    /**
     * 获取所有的key
     * 配置文件key
     *
     * @return
     * @throws IOException
     */
    public Set<Object> readProp() {
        InputStream in = this.getClass().getResourceAsStream("/propertity.properties");
        Properties props = new Properties();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
            props.load(inputStreamReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props.keySet();
    }

    /**
     * 更具key获取value
     *
     * @param key
     * @return
     */
    public String findPropValus(String key) {
        InputStream in = this.getClass().getResourceAsStream("/propertity.properties");
        Properties props = new Properties();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
            props.load(inputStreamReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(props.get(key));
    }

    /**
     * 开启服务
     *
     * @param startCommand
     * @return
     */
    public static void startApp(String startCommand) {
        if (!StringUtils.isNullOrEmpty(startCommand)) {
            String[] Cmds = {"/bin/sh", "-c", startCommand};
            List<String> resultList = LinuxCommandUtils.exec(Cmds);
            log.info("启动服务成功，启动命令为：" + String.valueOf(Cmds[2]));
        }
    }


    /**
     * 更具名称查询进程号
     */

    public static String getPid(String appName) {
        if (!StringUtils.isNullOrEmpty(appName)) {
            String[] wedCmds = {"/bin/sh", "-c", "ps -eo pid,lstart,cmd | grep " + appName + " | grep -v grep"};
            List<String> exec = LinuxCommandUtils.exec(wedCmds);
            if (exec != null && exec.size() > 0) {
                String[] beforeArgs = exec.get(0).trim().split("\\s+");
                return beforeArgs[0];
            }
        }
        return null;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date now = new Date();
        return sdf.format(now);

    }
}
