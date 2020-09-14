package com.dhlk.light.service.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.domain.Result;
import com.dhlk.entity.light.*;
import com.dhlk.light.service.dao.InspectionReportDao;
import com.dhlk.light.service.dao.LedDao;
import com.dhlk.light.service.dao.TaskSchedulerDao;
import com.dhlk.light.service.enums.LedEnum;
import com.dhlk.light.service.mqtt.MqttSendServer;
import com.dhlk.light.service.util.LedConst;
import com.dhlk.light.service.util.LedResult;
import com.dhlk.light.service.util.LightDeviceUtil;
import com.dhlk.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author xkliu
 * @date 2020/6/24
 */
public class InspectionTask implements Runnable {

    private Logger log = LoggerFactory.getLogger(InspectionTask.class);

    private InspectionReportDao inspectionReportDao;

    private MqttSendServer mqttSendServer;

    private RedisService redisService;

    private LedDao ledDao;

    private LightDeviceUtil lightDeviceUtil;

    private TaskSchedulerDao taskSchedulerDao;

    private Integer tenantId;

    private List<InspectionReport> inspectionReports;

    private String key;
    /**
    *  开灯测试
     * @param ledSns
     * @param jsonArray
    * @return
    */
    public void reportLedOn(List<String> ledSns,JSONArray jsonArray) {
        //执行开灯测试,1开灯,0关灯
        jsonArray.add(JSONObject.toJSON(new Result(1, "正在执行开灯测试......", key)));
        redisService.set(key, jsonArray, 600);
        if (ledSns!=null&&ledSns.size()>0) {
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            CountDownLatch countDown = new CountDownLatch(1);//设置线程阻塞等待
            Future future = executorService.scheduleAtFixedRate(new Runnable() {
                private int count = 0;
                Long ts=System.currentTimeMillis();
                @Override
                public void run() {
                    count++;
                    //给mqtt发送开灯命令
                    for (String sn : ledSns) {
                        LedResult ledResult = new LedResult(sn,LedEnum.ONOFF, new LedOnOff(1),ts,tenantId);
                        sendMessToMqtt(ledResult);
                    }
                    //比对给没有mqtt未返回的灯重新发送，第一次不比对直接返回
                    List<String> list =compareToMqttIsSuccess(ledSns,ts);
                    if (count > LedConst.RETRYCOUNT || list.size() == 0) {
                        //将开灯无返回的灯设置为开灯失败
                        for (InspectionReport inspectionReport : inspectionReports) {
                            if(list.contains(inspectionReport.getSn())){
                                inspectionReport.setOns("1");
                            }
                        }
                        List<InspectionReport> failSns = inspectionReports.stream().filter(a -> a.getOns().equals("0")).collect(Collectors.toList());
                        Result res = new Result(1, "开灯测试完成,共测试:" + ledSns.size() + "个灯,合格" + (ledSns.size()-failSns.size()) + "个,"+ "不合格" +failSns.size() + "个", key);
                        jsonArray.add(JSONObject.toJSON(res));
                        redisService.set(key, jsonArray, 600);
                        countDown.countDown();
                        //发送两次或者灯已经全部开启/关闭则停止定时任务
                        executorService.shutdown();
                        //调用调光
                        ledBrightness(ledSns,jsonArray);
                    }
                }
            }, 0, LedConst.RETRYTIME, TimeUnit.SECONDS);//延时5秒，每20秒发送一次，发送2次结束该任务。
            try {
                countDown.await();//阻塞在这里,等到所有线程返回结果
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    /**
     * 灯亮度设置
     * @return
     */
    public void ledBrightness(List<String> ledSns,JSONArray jsonArray) {
        jsonArray.add(JSONObject.toJSON(new Result(1, "正在执行调光测试......", key)));
        redisService.set(key, jsonArray, 600);
        if (ledSns!=null&&ledSns.size()>0) {
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            CountDownLatch countDown = new CountDownLatch(1);//设置线程阻塞等待
            Future future = executorService.scheduleAtFixedRate(new Runnable() {
                private int count = 0;
                Long ts=System.currentTimeMillis();
                @Override
                public void run() {
                    count++;
                    //给mqtt发送设置亮度命令
                    for (String sn : ledSns) {
                        sendMessToMqtt(new LedResult(sn,LedEnum.LIGHTNESS, new LedBrightness(LedConst.BRIGHT),ts,tenantId));
                    }
                    //比对给没有mqtt未返回的灯重新发送，第一次不比对直接返回
                    List<String> list = compareToMqttIsSuccess(ledSns,ts);
                    if (count > LedConst.RETRYCOUNT || list.size() == 0) {
                        //将亮度设置无返回的灯设置为失败
                        for (InspectionReport inspectionReport : inspectionReports) {
                            if(list.contains(inspectionReport.getSn())){
                                inspectionReport.setDimming("1");
                            }
                        }
                        List<InspectionReport> failSns = inspectionReports.stream().filter(a -> a.getDimming().equals("0")).collect(Collectors.toList());
                        Result res = new Result(1, "调光测试完成,共测试:" + ledSns.size() + "个灯,合格" + (ledSns.size()-failSns.size()) + "个," + "不合格" +failSns.size() + "个", key);
                        jsonArray.add(JSONObject.toJSON(res));
                        redisService.set(key, jsonArray, 600);
                        //发送两次或者灯已经全部开启/关闭则停止定时任务
                        countDown.countDown();
                        executorService.shutdown();
                        //调用调光
                        reportLedOff(ledSns,jsonArray);
                    }
                }
            }, 0, LedConst.RETRYTIME, TimeUnit.SECONDS);//延时5秒，每20秒发送一次，发送2次结束该任务。
            try {
                countDown.await();//阻塞在这里,等到所有线程返回结果
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
    *  关灯测试
     * @param ledSns
     * @param jsonArray
    * @return
    */
    public void reportLedOff(List<String> ledSns,JSONArray jsonArray) {
        //执行开灯测试,1开灯,0关灯
        jsonArray.add(JSONObject.toJSON(new Result(1, "正在执行关灯测试......", key)));
        redisService.set(key, jsonArray, 600);
        if (ledSns!=null&&ledSns.size()>0) {
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            CountDownLatch countDown = new CountDownLatch(1);//设置线程阻塞等待
            Future future = executorService.scheduleAtFixedRate(new Runnable() {
                private int count = 0;
                Long ts=System.currentTimeMillis();
                @Override
                public void run() {
                    count++;
                    //给mqtt发送开灯命令
                    for (String sn : ledSns) {
                        sendMessToMqtt(new LedResult(sn,LedEnum.ONOFF, new LedOnOff(0),ts,tenantId));
                    }
                    //比对给没有mqtt未返回的灯重新发送，第一次不比对直接返回
                    List<String> list = compareToMqttIsSuccess(ledSns,ts);
                    if (count > LedConst.RETRYCOUNT || list.size() == 0) {
                        //将关灯无返回的灯设置为开灯失败
                        for (InspectionReport inspectionReport : inspectionReports) {
                            if(list.contains(inspectionReport.getSn())){
                                inspectionReport.setOff("1");
                            }
                        }
                        //线程都执行完后再进行数据存库,
                        //先删除当前租户下的测试报告数据，在进行添加数据

                        try {
                            inspectionReportDao.deleteByTenantId(tenantId);
                            inspectionReportDao.insertBatch(inspectionReports);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        List<InspectionReport> failSns = inspectionReports.stream().filter(a -> a.getOff().equals("0")).collect(Collectors.toList());
                        Result res = new Result(0, "关灯测试完成,共测试:" + ledSns.size() + "个灯,合格" + (ledSns.size()-failSns.size()) + "个," + "不合格" +failSns.size() + "个", key);
                        jsonArray.add(JSONObject.toJSON(res));
                        redisService.set(key, jsonArray, 600);
                        countDown.countDown();
                        //发送两次或者灯已经全部开启/关闭则停止定时任务
                        executorService.shutdown();
                    }
                }
            }, 0, LedConst.RETRYTIME, TimeUnit.SECONDS);//延时5秒，每20秒发送一次，发送2次结束该任务。
            try {
                countDown.await();//阻塞在这里,等到所有线程返回结果
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



    @Override
    public void run() {
        JSONArray jsonArray = new JSONArray();
        try {
            //获取当前租户下的灯的sn码,拼接成字符串
            List<Led> leds = ledDao.findListByTenantId(tenantId);
            String sns=leds.stream().map(Led::getSn).collect(Collectors.joining(","));
            //用来存储开灯成功后的sn
            List<String> ledSns = new ArrayList<>();
            //默认设置所有灯开关结果都为合格
            for (Led led : leds) {
                ledSns.add(led.getSn());
                InspectionReport inspectionReport = new InspectionReport("0","0", "0", "0", led.getSn(),tenantId);
                inspectionReports.add(inspectionReport);
            }
            reportLedOn(ledSns,jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
            redisService.set(key, "验收报告执行异常!", 600);
        }
    }



    /**
     * 将用户发送的灯与redis进行比较，如果redis存在，说明mqtt已经返回
     * @return
     */

    private List<String> compareToMqttIsSuccess(List<String> sns, Long ts) {
        List<String> ledSns = new ArrayList<>();
        CountDownLatch countDown = new CountDownLatch(sns.size());//设置线程阻塞等待
        for (String sn : sns) {
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            Future future = executorService.scheduleAtFixedRate(new Runnable() {
                Object backRes = null;
                private  int count = 0;
                @Override
                public void run() {
                    count++;
                    backRes = redisService.get(sn + "_" + ts);
                    if (backRes != null || count > 10) {
                        //循环了10次，等待了100毫秒，仍无返回结果，则进行重新给mqtt发送消息
                        if (backRes != null) {
                            ledSns.add(sn);
                        }
                        executorService.shutdown();
                        countDown.countDown();
                    }
                }
            }, 0, 100, TimeUnit.MILLISECONDS);
        }
        try {
            countDown.await();//阻塞在这里,等到所有线程返回结果
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ledSns;
    }
    /**
     * mqtt消息发送
     * @param ledResult
     * @return
     */
    public void sendMessToMqtt(LedResult ledResult) {
        mqttSendServer.sendMQTTMessage(LedConst.TOPIC_CLOUDTOLOCAL,JSONObject.toJSONString(ledResult));
    }




//    public List<String> timedTask(Integer tenantId) throws ExecutionException, InterruptedException {
//        //获取任务详情
//        ScheduledFuture future = null;
//        String sn = "";
//        ThreadPoolTaskScheduler threadPoolTask = this.threadPoolTaskScheduler();
//        List<TaskScheduler> taskSchedulers = taskSchedulerDao.selectTaskSchedulerByTenantId(tenantId);//headerUtil.tenantId()
//        List<String> result = new ArrayList<>();
//        if (taskSchedulers != null && taskSchedulers.size() > 0) {
//            for (TaskScheduler taskScheduler : taskSchedulers) {
//                Integer switchId = taskScheduler.getSwitchId();
//                List<Led> leds = ledDao.findLedsBySwitchId(String.valueOf(switchId));
//                for (Led led : leds) {
//                    sn += led.getSn() + ",";
//                }
//                List<TaskSchedulerDetail> taskSchedulerDetailList = taskScheduler.getTaskSchedulerDetailList();
//                List<String> list = new ArrayList<>();
//
//                if (taskSchedulerDetailList != null && taskSchedulerDetailList.size() > 0) {
//                    for (TaskSchedulerDetail taskSchedulerDetail : taskSchedulerDetailList) {
//                        String finalSn = sn;
//                        //线程池传入具体任务和cron触发器  new CronTrigger()方法的参数是cron表达式
//                        future = threadPoolTask.schedule(new Runnable() {
//                            @Override
//                            public void run() {
//                                list.addAll(lightDeviceUtil.ledReportOnOrOff(finalSn, String.valueOf(taskSchedulerDetail.getOnOff())));
//                            }
//                        }, new CronTrigger(taskSchedulerDetail.getExpression()));
//                    }
//                }
//
//                //开启定时任务成功 修改任务状态为开启0
//                result.addAll(list);
//                threadPoolTask.shutdown();
//                System.out.println("-------------" + list.size());
//            }
//        }
//        return result;
//    }
//
//    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
//        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
//        executor.initialize();
//        executor.setPoolSize(10);
//        executor.setThreadNamePrefix("task-");
//        // 线程池对拒绝任务（无线程可用）的处理策略，目前只支持AbortPolicy、CallerRunsPolicy；默认为后者
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        //调度器shutdown被调用时等待当前被调度的任务完成
//        executor.setWaitForTasksToCompleteOnShutdown(true);
//        //等待时长
//        executor.setAwaitTerminationSeconds(60);
//        return executor;
//    }


    public InspectionReportDao getInspectionReportDao() {
        return inspectionReportDao;
    }

    public void setInspectionReportDao(InspectionReportDao inspectionReportDao) {
        this.inspectionReportDao = inspectionReportDao;
    }


    public RedisService getRedisService() {
        return redisService;
    }

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    public LedDao getLedDao() {
        return ledDao;
    }

    public void setLedDao(LedDao ledDao) {
        this.ledDao = ledDao;
    }

    public LightDeviceUtil getLightDeviceUtil() {
        return lightDeviceUtil;
    }

    public void setLightDeviceUtil(LightDeviceUtil lightDeviceUtil) {
        this.lightDeviceUtil = lightDeviceUtil;
    }

    public TaskSchedulerDao getTaskSchedulerDao() {
        return taskSchedulerDao;
    }

    public void setTaskSchedulerDao(TaskSchedulerDao taskSchedulerDao) {
        this.taskSchedulerDao = taskSchedulerDao;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public List<InspectionReport> getInspectionReports() {
        return inspectionReports;
    }

    public void setInspectionReports(List<InspectionReport> inspectionReports) {
        this.inspectionReports = inspectionReports;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MqttSendServer getMqttSendServer() {
        return mqttSendServer;
    }

    public void setMqttSendServer(MqttSendServer mqttSendServer) {
        this.mqttSendServer = mqttSendServer;
    }

    public InspectionTask(InspectionReportDao inspectionReportDao,MqttSendServer mqttSendServer,
                          RedisService redisService, LedDao ledDao, LightDeviceUtil lightDeviceUtil,
                          TaskSchedulerDao taskSchedulerDao, Integer tenantId, List<InspectionReport> inspectionReports, String key) {
        this.inspectionReportDao = inspectionReportDao;
        this.redisService = redisService;
        this.ledDao = ledDao;
        this.lightDeviceUtil = lightDeviceUtil;
        this.taskSchedulerDao = taskSchedulerDao;
        this.tenantId = tenantId;
        this.inspectionReports = inspectionReports;
        this.key = key;
        this.mqttSendServer=mqttSendServer;
    }
}
