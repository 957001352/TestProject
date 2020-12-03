package com.dhlk.utils;


import com.dhlk.domain.ProcessInfoEntity;
import com.dhlk.domain.ServerManagerEntity;
import com.dhlk.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: server
 * @description: 解析服务信息列表
 * @author: wqiang
 * @create: 2020-07-03 17:35
 **/
@Component
public class ParseStringUtils {

    @Autowired
    private RedisService redisService;

    /**
     * 解析服务字符串
     *
     * @param listStr
     * @return
     */
    public List<ServerManagerEntity> parseString(List<String> listStr) {
        List<ServerManagerEntity> list = new ArrayList<>();
        if (listStr != null && listStr.size() > 0) {
            for (int i = 1; i < listStr.size(); i++) {
                list.add(strToList(listStr.get(i).toString()));
            }
            return list;
        }
        return null;
    }


    /**
     * @param serverStr
     * @return
     */
    public ServerManagerEntity strToList(String serverStr) {
        ServerManagerEntity serverManagerEntity = new ServerManagerEntity();
        boolean flag = true;
        while (flag) {
            serverStr = serverStr.replaceAll("   ", "  ");
            int index = serverStr.indexOf("   ");
            if (index < 1) {
                flag = false;
            }
        }
        String[] strs = serverStr.split("  ");
        List serList = Arrays.asList(strs);
        if (serList != null && serList.size() > 0) {
            serverManagerEntity.setContainerId(serList.get(0).toString());
            serverManagerEntity.setImage(serList.get(1).toString());
            serverManagerEntity.setCommand(serList.get(2).toString());
            serverManagerEntity.setCreateTime(parseTime(serList.get(3).toString()));

            //获取开始时间结束时间及运行时长
            String[] strings = parseEndOrStartTime(serList.get(4).toString(), serList.get(3).toString());
            serverManagerEntity.setStartTime(strings[0]);
            serverManagerEntity.setEndTime(strings[2]);
            serverManagerEntity.setRunTime(strings[1]);
            if (serList.size() == 7) {
                serverManagerEntity.setName(serList.get(6).toString());
            } else {
                serverManagerEntity.setName(serList.get(5).toString());
            }

            if (!StringUtils.isNull(serList.get(4).toString())) {
                if (serList.get(4).toString().contains("Up")) {
                    serverManagerEntity.setStatusDes("Up");
                    serverManagerEntity.setStatus(1); //1开
                }
                if (serList.get(4).toString().contains("Exited")) {
                    serverManagerEntity.setStatusDes("Exited");
                    serverManagerEntity.setStatus(0); //0关
                }
                if (serList.get(4).toString().contains("Created")) {
                    serverManagerEntity.setStatusDes("Created");
                    serverManagerEntity.setStatus(0); //0关
                }
            } else {
                serverManagerEntity.setStatus(0); //0关
            }
        }

        return serverManagerEntity;
    }

    /**
     * 解析时间
     *
     * @param timeStr
     * @return
     */
    public String parseTime(String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        if (!StringUtils.isNull(timeStr) && StringUtils.HasDigit(timeStr)) {
            //提取数字
            int timeCount = StringUtils.extractFigure(timeStr);
            if (timeStr.contains("months")) {
                c.add(Calendar.MONTH, -timeCount);
            }
            if (timeStr.contains("weeks")) {
                c.add(Calendar.DAY_OF_WEEK, -timeCount * 7);

            }
            if (timeStr.contains("days")) {
                c.add(Calendar.DATE, -timeCount);

            }
            if (timeStr.contains("hours")) {
                c.add(Calendar.HOUR, -timeCount);
            }
            if (timeStr.contains("minutes")) {
                c.add(Calendar.MINUTE, -timeCount);

            }
            if (timeStr.contains("seconds")) {
                c.add(Calendar.SECOND, -timeCount);

            }
            return sdf.format(c.getTime());
        }
        if (!StringUtils.HasDigit(timeStr)) {
            if (timeStr.contains("About an hour ago")) {
                c.add(Calendar.HOUR, -1);
            }
            if (timeStr.contains("About an minutes ago")) {
                c.add(Calendar.MINUTE, -1);
            }
            if (timeStr.contains("About an seconds ago")) {
                c.add(Calendar.SECOND, -1);
            }
            if (timeStr.contains("About an months ago")) {
                c.add(Calendar.MONTH, -1);
            }
            if (timeStr.contains("About an weeks ago")) {
                c.add(Calendar.DAY_OF_WEEK, -1);
            }
            if (timeStr.contains("About an days ago")) {
                c.add(Calendar.DATE, -1);
            }
            return sdf.format(c.getTime());
        }
        return "";

    }


    /**
     * 解析开始时间\结束时间\运行时长
     *
     * @param endTimeStr
     * @return
     */
    public String[] parseEndOrStartTime(String endTimeStr, String createTime) {

        String[] strs = new String[3];
        if (!StringUtils.isNull(endTimeStr)) {
            if (endTimeStr.contains("Up")) {
                //开始时间 (如果是UP，开始时间就是当前时间减去运行时长)
                strs[0] = parseTime(endTimeStr);
                //运行时长
                if (endTimeStr.contains("days")) {
                    strs[1] = String.valueOf(StringUtils.extractFigure(endTimeStr) * 24);
                } else if (endTimeStr.contains("weeks")) {
                    strs[1] = String.valueOf(StringUtils.extractFigure(endTimeStr) * 7 * 24);
                } else if (endTimeStr.contains("months")) {
                    strs[1] = String.valueOf(StringUtils.extractFigure(endTimeStr) * 30 * 7 * 24);
                } else if (endTimeStr.contains("hours")) {
                    strs[1] = String.valueOf(StringUtils.extractFigure(endTimeStr));
                } else {
                    strs[1] = "1";
                }
                //结束时间
                strs[2] = "";

            }
            if (endTimeStr.contains("Exited")) {
                //开始时间
                strs[0] = parseTime(createTime);
                //结束时间
                strs[2] = parseTime(endTimeStr);
                //运行时长
                strs[1] = DateUtils.getMistiming(strs[2], strs[0]) + "";
            }

        } else {
            for (String s : strs) {
                s = "";
            }
        }


        return strs;
    }


    /**
     * 解析进程字符串
     */
    public List<ProcessInfoEntity> parseProcessString(List<String> listStr, String subFlag, String flag) {
        List<ProcessInfoEntity> list = new ArrayList<>();
        if (listStr != null && listStr.size() > 0) {
            for (int i = 0; i < listStr.size(); i++) {
                //去除nginx: worker进程
                if (listStr.get(i).toString().contains("nginx: worker")) {
                    continue;
                }
                //将字符串转换为实体
                ProcessInfoEntity processInfoEntity = lineStrToList(listStr.get(i).toString(), subFlag, flag);
                if (processInfoEntity != null) {
                    list.add(processInfoEntity);
                }

            }
            return list;
        }
        return null;
    }

    /**
     * 解析linex进程 将一行信息解析成list
     *
     * @param serverStr
     * @return
     */
    public ProcessInfoEntity lineStrToList(String serverStr, String subFlag, String flag) {
        //System.out.println("subFlag-------------------" + subFlag);
        //将web后台每一行字符串进行截取
        if ("java -jar".equals(subFlag)) {
            String beforeStr = org.apache.commons.lang3.StringUtils.substringBefore(serverStr, subFlag);
            String afterStr = org.apache.commons.lang3.StringUtils.substringAfter(serverStr, subFlag);
            //System.out.println("web截取前面：----------------" + beforeStr);
            //System.out.println("web截取后面：----------------" + afterStr);
            return getLineInfo(beforeStr, afterStr, flag);
        }
        //将mysql、mosquitto服务进行截取
        if ("/".equals(subFlag)) {
            String beforeStr = org.apache.commons.lang3.StringUtils.substringBefore(serverStr, subFlag);
            String afterStr = org.apache.commons.lang3.StringUtils.substringAfter(serverStr, subFlag);
            //System.out.println("redis截取前面：----------------" + beforeStr);
            //System.out.println("redis截取后面：----------------" + afterStr);
            return getLineInfo(beforeStr, afterStr, flag);
        }

        if ("redis".equals(subFlag)) {
            String beforeStr = org.apache.commons.lang3.StringUtils.substringBefore(serverStr, subFlag);
            String afterStr = org.apache.commons.lang3.StringUtils.substringAfter(serverStr, subFlag);
            //System.out.println("redis截取前面：----------------" + beforeStr);
            //System.out.println("redis截取后面：----------------" + afterStr);
            return getLineInfo(beforeStr, afterStr, flag);
        }


        //将nginx服务进行截取
        if ("nginx".equals(subFlag)) {
            String beforeStr = org.apache.commons.lang3.StringUtils.substringBefore(serverStr, subFlag);
            String afterStr = org.apache.commons.lang3.StringUtils.substringAfter(serverStr, subFlag);
            //System.out.println("nginx截取前面：----------------" + beforeStr);
            //System.out.println("nginx截取后面：----------------" + afterStr);
            return getLineInfo(beforeStr, afterStr, flag);
        }
        return null;
    }

    /**
     * 根据字符串截取的前后进行封装实体
     *
     * @param beforeStr
     * @param afterStr
     * @return
     */
    public ProcessInfoEntity getLineInfo(String beforeStr, String afterStr, String flag) {

        ProcessInfoEntity processInfoEntity = new ProcessInfoEntity();
        String[] beforeArgs = beforeStr.trim().split("\\s+");
        String[] afterArgs = afterStr.trim().split("\\s+");
        //进程id
        if (beforeArgs != null && beforeArgs.length > 0) {
            processInfoEntity.setPid(beforeArgs[0].toString());
            //开始时间
            processInfoEntity.setStartTime(DateUtils.getTransformBIH(beforeArgs[1].toString()
                    + " " + beforeArgs[2].toString()
                    + " " + beforeArgs[3].toString()
                    + " " + beforeArgs[4].toString()
                    + " " + beforeArgs[5].toString()
            ));
            //运行时间=当前时间-开始时间
            processInfoEntity.setRunTime(DateUtils.getMistiming(DateUtils.getCurrentTime(), processInfoEntity.getStartTime()).toString());

            //获取进程名称
            if (Arrays.asList(afterArgs).stream().filter(k -> k.contains("dhlk_light_factory")).collect(Collectors.toList()).size() > 0) {
                processInfoEntity.setProcessName("dhlk_light_factory");
                processInfoEntity.setType("2");
            } else if (Arrays.asList(afterArgs).stream().filter(k -> k.contains("dhlk_server_manage")).collect(Collectors.toList()).size() > 0) {
                processInfoEntity.setProcessName("dhlk_server_manage");
                processInfoEntity.setType("3");
            } else if (Arrays.asList(afterArgs).stream().filter(k -> k.contains("dhlk_server_guard")).collect(Collectors.toList()).size() > 0) {
                processInfoEntity.setProcessName("dhlk_server_guard");
                processInfoEntity.setType("6");
            } else if (Arrays.asList(afterArgs).stream().filter(k -> k.contains("quitto")).collect(Collectors.toList()).size() > 0) {
                processInfoEntity.setProcessName("mosquitto");
                processInfoEntity.setType("3");
            } else if (Arrays.asList(afterArgs).stream().filter(k -> k.contains("nginx")).collect(Collectors.toList()).size() > 0) {
                processInfoEntity.setProcessName("nginx");
                processInfoEntity.setType("3");
            } else if (Arrays.asList(afterArgs).stream().filter(k -> k.contains("-server")).collect(Collectors.toList()).size() > 0) {
                processInfoEntity.setProcessName("redis");
                processInfoEntity.setType("3"); //3表示不升级，供前端控制
            } else if (Arrays.asList(afterArgs).stream().filter(k -> k.contains("mysqld")).collect(Collectors.toList()).size() > 0) {
                processInfoEntity.setProcessName("mysql");
                processInfoEntity.setType("3");
            } else if (Arrays.asList(afterArgs).stream().filter(k -> k.contains("dhlk_mom")).collect(Collectors.toList()).size() > 0) {
                processInfoEntity.setProcessName("dhlk_mom");
                processInfoEntity.setType("5");
            } else {
                //去除过滤之外的进程
                return null;
            }

            //过滤掉不需要初始化地址到redis的服务
            if (!processInfoEntity.getProcessName().equals("mosquitto")
                    && !processInfoEntity.getProcessName().equals("nginx")
                    && !processInfoEntity.getProcessName().equals("redis")
                    && !processInfoEntity.getProcessName().equals("mysql")
                    && !processInfoEntity.getProcessName().equals("dhlk_mom")) {
                //mosquitto、nginx、redis、mysql 不需要升级，也不需要初始化地址到redis
                //初始化地址到redis
                initPathToRedis(processInfoEntity.getProcessName(), flag, afterStr);
            }

            processInfoEntity.setStatus("1");

        }
        return processInfoEntity;

    }

    public List<ProcessInfoEntity> getFrontCloudAndLocal() {
        List<ProcessInfoEntity> list = new ArrayList<>();

        //前端云
       /* ProcessInfoEntity frontCloudEntity = new ProcessInfoEntity();
        frontCloudEntity.setPid("");
        frontCloudEntity.setType("4");
        frontCloudEntity.setProcessName("front_cloud");
        frontCloudEntity.setRunTime("");
        frontCloudEntity.setStartTime("");
        frontCloudEntity.setStatus("1");
        list.add(frontCloudEntity);*/

        //一体机前端
        ProcessInfoEntity monitor = new ProcessInfoEntity();
        monitor.setPid("");
        monitor.setType("4");
        monitor.setProcessName("front_monitor");
        monitor.setRunTime("");
        monitor.setStartTime("");
        monitor.setStatus("1");
        list.add(monitor);

        ProcessInfoEntity frontLocalEntity = new ProcessInfoEntity();
        frontLocalEntity.setPid("");
        frontLocalEntity.setType("4");
        frontLocalEntity.setProcessName("front_local");
        frontLocalEntity.setRunTime("");
        frontLocalEntity.setStartTime("");
        frontLocalEntity.setStatus("1");
        list.add(frontLocalEntity);

        //sql
        ProcessInfoEntity sqlEntity = new ProcessInfoEntity();
        sqlEntity.setPid("");
        sqlEntity.setType("1");
        sqlEntity.setProcessName("sql");
        sqlEntity.setRunTime("");
        sqlEntity.setStartTime("");
        sqlEntity.setStatus("1");
        list.add(sqlEntity);

        return list;

    }

    /**
     * 初始化地址到redis
     *
     * @param key      服务名称
     * @param flag     空是正常查询  2定时查询标识
     * @param afterStr
     */
    public void initPathToRedis(String key, String flag, String afterStr) {
        String substring = "";//初始化地址，将地址存入redis
        if (StringUtils.isNullOrEmpty(flag)) {
            if (!afterStr.contains("/")) {
                substring = afterStr;
            } else {
                substring = afterStr.substring(afterStr.indexOf("/"), afterStr.length());
            }
            redisService.set(key, substring);
        }
    }
}
