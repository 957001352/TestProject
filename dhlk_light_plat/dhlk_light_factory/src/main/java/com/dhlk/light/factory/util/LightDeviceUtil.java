package com.dhlk.light.factory.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.entity.light.*;
import com.dhlk.light.factory.dao.LedRecordDao;
import com.dhlk.light.factory.enums.LedEnum;
import com.dhlk.light.factory.mqtt.MqttCloudSender;
import com.dhlk.light.factory.mqtt.MqttLocalSender;
import com.dhlk.service.RedisService;
import com.dhlk.util.AuthUserUtil;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Description 控制照明设备
 * @Author lpsong
 * @Date 2020/6/8
 */
@Component
public class LightDeviceUtil {

    private Logger log = LoggerFactory.getLogger(LightDeviceUtil.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private MqttLocalSender mqttLocalSender;
    @Autowired
    private MqttCloudSender mqttCloudSender;
    @Autowired
    private LedRecordDao ledRecordDao;
    @Autowired
    private AuthUserUtil authUserUtil;


    /**
     * 将用户发送的灯与redis进行比较，如果redis存在，说明mqtt已经返回
     * @param sns
     * @return
     */
    /**
     * 将用户发送的灯与redis进行比较，如果redis存在，说明mqtt已经返回
     * @param sns
     * @return
     */
    private List<String> compareToMqttIsSuccess(String sns, Long ts, LedEnum ledEnum) {
        List<String> ledSns = new ArrayList<>();
        CountDownLatch countDown = new CountDownLatch(sns.split(",").length);//设置线程阻塞等待
        for (String sn : sns.split(",")) {
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            Future future = executorService.scheduleAtFixedRate(new Runnable() {
                Object backRes = null;
                private int count = 0;
                @Override
                public void run() {
                    count++;
                    if (ledEnum.getState() == LedEnum.RESTART.getState()) {
                        backRes = redisService.get(sn + "_" + ledEnum.getState());
                        //有返回结果或者等待100毫秒，跳出
                        if (backRes != null || count > 9) {
                            //无返回结果，则进行重新给mqtt发送消息
                            if (backRes == null) {
                                ledSns.add(sn);
                            }
                            executorService.shutdown();
                            countDown.countDown();
                        }
                    } else {//其他命令返回命令判断
                        backRes = redisService.get(sn + "_" + ts);
                        if (backRes != null || count > 10) {
                            if (backRes == null) {
                                ledSns.add(sn);
                            }
                            executorService.shutdown();
                            countDown.countDown();
                        }
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
     * 开关灯结果比对
     * @param sns
     * @param ts
     * @param ledEnum
     * @param onOff
     * @return
     */
    private List<String> ledOnOffToMqttIsSuccess(String sns, Long ts, LedEnum ledEnum, String onOff) {
        List<String> ledSns = new ArrayList<>();
        CountDownLatch countDown = new CountDownLatch(sns.split(",").length);//设置线程阻塞等待
        for (String sn : sns.split(",")) {
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
                        if (backRes == null) {
                            ledSns.add(sn);
                        } else {
                            //更新开灯结果
                            updateRedisLedStatus(sn, onOff);
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
     * 开灯完成更新开灯结果
     * @param sn
     * @param status
     * @return
     */
    public void updateRedisLedStatus(String sn,String status){
        Object power = redisService.get(LedConst.REDIS_POWER + sn);
        //获取能耗信息 判断redis里存在该sn的灯的能耗信息，如果有直接转换，没有就初始化
        LedPower ledPower = null;
        if (power == null) {
            ledPower = new LedPower();
            ledPower.setLedSn(sn);
            ledPower.setStatus(status);
        } else {
            ledPower = JSONObject.parseObject(power.toString(), LedPower.class);
            ledPower.setStatus(status);
        }
        redisService.set(LedConst.REDIS_POWER + sn, JSON.toJSONString(ledPower), LedConst.REDISTTIME);//增加30秒
    }
    /**
     * 本地mqtt消息发送
     * @param ledResult
     * @return
     */
    public void sendMessToLocalMqtt(LedResult ledResult) {
        try{
            mqttLocalSender.sendMQTTMessage(LedConst.LOCALTOHARD_TOPIC_DHLKLIGHT,JSONObject.toJSONString(ledResult));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 定时任务开灯,并设置亮度
     * @param sns
     * @param status
     * @return
     */
    public  List<String> ledTaskOnBright(String sns, Integer status,Integer bright){
        List<String> resList=new ArrayList<>();
        if(!CheckUtils.isNull(sns)){
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            CountDownLatch countDown = new CountDownLatch(1);//设置线程阻塞等待
            Future future = executorService.scheduleAtFixedRate(new Runnable() {
                private int count = 0;
                Long ts=System.currentTimeMillis();
                @Override
                public void run() {
                    count++;
                    //给mqtt发送开灯命令
                    for (String sn : sns.split(",")) {
                        sendMessToLocalMqtt(new LedResult(sn,LedEnum.ONOFF, new LedOnOff(status),ts));
                    }
                    //比对给没有mqtt未返回的灯重新发送
                    List<String> list = ledOnOffToMqttIsSuccess(sns,ts,LedEnum.ONOFF,status.toString());
                    if (count > LedConst.RETRYCOUNT || list.size() == 0) {
                        //发送两次或者灯已经全部开启/关闭则停止定时任务
                        resList.addAll(list);
                        countDown.countDown();
                        executorService.shutdown();
                        log.info(resList.size() + "定时开灯任务..." + list.size());
                        ledBrightness(sns,bright,false);
                    }
                }
            }, 0, LedConst.RETRYTIME, TimeUnit.SECONDS);//延时5秒，每20秒发送一次，发送2次结束该任务。
            try {
                countDown.await();//阻塞在这里,等到所有线程返回结果
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resList;
    }
    /**
    * 更新操作记录
     * @param userName
     * @param sns
     * @param ts
     * @param com_type
     * @param failList
     * @param ledResult
    * @return
    */
    public void updateLedReord(String userName, String[] sns, Long ts, LedEnum com_type, List<String> failList,LedResult ledResult){
        try {
            LedRecord ledRecord = new LedRecord(ts.toString(),com_type.getStateInfo(), JSONObject.toJSONString(ledResult), userName, "发送"+sns.length+"盏灯","cloud", DateUtils.stampToTime(ts),"失败"+failList.size()+"盏灯,"+failList.toString(),DateUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
            ledRecordDao.insert(ledRecord);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 获取当前登录用户名
     * @param
     * @return
     */
    private String userName(){
        JSONObject user=authUserUtil.currentUser();
        if(user!=null){
            return user.getString("name");
        }
        return "";
    }

    /*
     *  开关灯
     * @param sn
     * @param status  0-开，1-关闭
     * @return
     */
    public  void ledOnOrOff(String sns, Integer status,boolean isAdd){
        if(!CheckUtils.isNull(sns)){
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            String userName=null;
            if(isAdd) {
                userName = this.userName();
            }
            String finalUserName = userName;
            Future future = executorService.scheduleAtFixedRate(new Runnable() {
                private int count = 0;
                Long ts=System.currentTimeMillis();
                @Override
                public void run() {
                    count++;
                    //给mqtt发送开灯命令
                    for (String sn : sns.split(",")) {
                        sendMessToLocalMqtt(new LedResult(sn,LedEnum.ONOFF, new LedOnOff(status),ts));
                    }
                    //比对给没有mqtt未返回的灯重新发送
                    List<String> list = ledOnOffToMqttIsSuccess(sns,ts,LedEnum.ONOFF,status.toString());
                    if (count > LedConst.RETRYCOUNT || list.size() == 0) {
                        //发送两次或者灯已经全部开启/关闭则停止定时任务
                        executorService.shutdown();
                        log.info("停止开关灯任务...");
                        //更新返回结果
                        if(isAdd){
                            updateLedReord(finalUserName,sns.split(","),ts,LedEnum.ONOFF,list,new LedResult(sns,LedEnum.ONOFF, new LedOnOff(status),ts));
                        }
                        Long es=System.currentTimeMillis();
                    }
                }
            }, 0, LedConst.RETRYTIME, TimeUnit.SECONDS);//延时5秒，每20秒发送一次，发送2次结束该任务。


        }
    }
    /**
     * 灯闪一闪
     * @param sns
     * @return
     */
    public  void flashingLed(String sns) {
        if(!CheckUtils.isNull(sns)){
            String userName=this.userName();
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            Future future = executorService.scheduleAtFixedRate(new Runnable() {
                private int count = 0;
                Long ts=System.currentTimeMillis();
                @Override
                public void run() {
                    count++;
                    //给mqtt发送开灯命令
                    for(String sn:sns.split(",")) {
                        sendMessToLocalMqtt(new LedResult(sn,LedEnum.FLASHING,ts));
                    }
                    //比对给没有mqtt未返回的灯重新发送
                    List<String> list = compareToMqttIsSuccess(sns,ts,LedEnum.FLASHING);
                    if (count > LedConst.RETRYCOUNT||list.size()==0) {
                        //发送两次或者灯已经全部开启/关闭则停止定时任务
                        executorService.shutdown();
                        log.info("停止闪一闪任务...");
                        updateLedReord(userName,sns.split(","),ts,LedEnum.FLASHING,list,new LedResult(sns,LedEnum.FLASHING,ts));
                    }
                }
            },0, LedConst.RETRYTIME, TimeUnit.SECONDS);//延时5秒，每20秒发送一次，发送2次结束该任务。
        }
    }
    /*
     *  灯亮度设置
     * @param sn
     * @param bright 亮度设置
     * @return
     */
    public void ledBrightness(String sns,Integer bright,boolean isAdd){
        if(!CheckUtils.isNull(sns)){
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            String userName=null;
            if(isAdd) {
                userName = this.userName();
            }
            String finalUserName = userName;
            Future future = executorService.scheduleAtFixedRate(new Runnable() {
                private int count = 0;
                Long ts=System.currentTimeMillis();
                @Override
                public void run() {
                    count++;
                    //给mqtt发送设置亮度命令
                    for(String sn:sns.split(",")) {
                        sendMessToLocalMqtt(new LedResult(sn,LedEnum.LIGHTNESS, new LedBrightness(bright),ts));
                    }
                    //比对给没有mqtt未返回的灯重新发送
                    List<String> list= compareToMqttIsSuccess(sns,ts, LedEnum.LIGHTNESS);
                    if (count > LedConst.RETRYCOUNT||list.size()==0) {
                        //发送两次或者灯已经全部开启/关闭则停止定时任务
                        executorService.shutdown();
                        log.info("停止灯亮度设置任务...");
                        //定时任务，不保存操作记录
                        if(isAdd){
                            updateLedReord(finalUserName,sns.split(","),ts,LedEnum.LIGHTNESS,list,new LedResult(sns,LedEnum.LIGHTNESS, new LedBrightness(bright),ts));
                        }
                    }
                }
            },0, LedConst.RETRYTIME, TimeUnit.SECONDS);//延时5秒，每20秒发送一次，发送2次结束该任务。
        }
    }
    /*
     * 灯重启
     * @param sn
     * @return
     */
    public void ledRestart(String sns){
        if(!CheckUtils.isNull(sns)){
            String userName=this.userName();
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            Future future = executorService.scheduleAtFixedRate(new Runnable() {
                private int count = 0;
                Long ts=System.currentTimeMillis();
                @Override
                public void run() {
                    count++;
                    //给mqtt发送设置亮度命令
                    for(String sn:sns.split(",")) {
                        sendMessToLocalMqtt(new LedResult(sn,LedEnum.RESTART,ts));
                    }
                    //比对给没有mqtt未返回的灯重新发送
                    List<String> list= compareToMqttIsSuccess(sns,ts,LedEnum.RESTART);
                    if (count > LedConst.RETRYCOUNT||list.size()==0) {
                        //发送两次或者灯已经全部开启/关闭则停止定时任务
                        executorService.shutdown();
                        log.info("停止灯重启任务...");
                        updateLedReord(userName,sns.split(","),ts,LedEnum.RESTART,list,new LedResult(sns,LedEnum.RESTART,ts));
                    }
                }
            },0, LedConst.RETRYTIME, TimeUnit.SECONDS);//延时5秒，每20秒发送一次，发送2次结束该任务。
        }
    }

    /**
    * 灯设置组，此方法暂时没用
     * @param sns
     * @param grpId
    * @return
    */
    public void setLedGrpId(String sns,Integer grpId){
        if(!CheckUtils.isNull(sns)){
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            Future future = executorService.scheduleAtFixedRate(new Runnable() {
                private int count = 0;
                Long ts=System.currentTimeMillis();
                @Override
                public void run() {
                    count++;
                    //给mqtt发送设置亮度命令
                    for(String sn:sns.split(",")) {
                        JSONObject grp=new JSONObject();
                        grp.put("grp_id",grpId);
                        sendMessToLocalMqtt(new LedResult(sn,LedEnum.GRPID,grp,ts));
                    }
                    List<String> list= compareToMqttIsSuccess(sns,ts,LedEnum.GRPID);
                    if (count > LedConst.RETRYCOUNT||list.size()==0) {
                        //发送两次或者灯已经全部开启/关闭则停止定时任务
                        executorService.shutdown();
                        log.info("停止设置组任务...");
                    }
                }
            },0, LedConst.RETRYTIME, TimeUnit.SECONDS);//延时5秒，每20秒发送一次，发送2次结束该任务。
        }
    }
    /**
     * 获取人感配置
     * @param sn
     * @return
     */
    public void getPeopleFellConfig(String sn) {
        Long ts=System.currentTimeMillis();
        sendMessToLocalMqtt(new LedResult(sn, LedEnum.GETPEOPLEFELL,ts));
    }


    /**
     *
     * @param sns 灯的sn集合用逗号隔开
     * @param cmdType 发个mqtt的cmd_type
     * @return
     */
    public void sendMessageToMqttByThree(String sns,LedEnum cmdType,Object data,Integer tenantId){
        if(!CheckUtils.isNull(sns)){
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            Future future = executorService.scheduleAtFixedRate(new Runnable() {
                private int count = 0;
                Long ts=System.currentTimeMillis();
                @Override
                public void run() {
                    count++;
                    //给mqtt发送命令
                    for (String sn : sns.split(",")) {
                        LedResult ledResult = new LedResult(sn,cmdType,data,ts);
                        sendMessToLocalMqtt(ledResult);
                    }
                    //比对给没有mqtt未返回重新发送
                    List<String> list = commandToMqttIsSuccess(sns,cmdType,data,ts,tenantId,1);
                    if (count > LedConst.RETRYCOUNT || list.size() == 0) {
                        //发送两次或者已经全部返回则停止定时任务
                        executorService.shutdown();
                    }
                }
            }, 0, LedConst.RETRYTIME, TimeUnit.SECONDS);//延时5秒，每20秒发送一次，发送2次结束该任务。
        }
    }

    public List<String> commandToMqttIsSuccess(String sns,LedEnum cmdType, Object data,long ts,Integer tenantId,int status){
        List<String> ledSns = new ArrayList<>();
        CountDownLatch countDown = new CountDownLatch(sns.split(",").length);//设置线程阻塞等待
        for (String sn : sns.split(",")) {
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            Future future = executorService.scheduleAtFixedRate(new Runnable() {
                private  int count = 0;
                @Override
                public void run() {
                    count++;
                    boolean backRes = compareIsSuccess(sn,data,ts);
                    if (backRes || count > 10) {
                        //循环了10次，等待了100毫秒，仍无返回结果，则进行重新给mqtt发送消息
                        if (!backRes) {
                            ledSns.add(sn);
                        }
                        executorService.shutdown();
                        countDown.countDown();
                    }
                }
            }, 0, 6000, TimeUnit.MILLISECONDS);
        }
        try {
            countDown.await();//阻塞在这里,等到所有线程返回结果
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(ledSns.size()==0){
            InfoBox<String> infoBox = new InfoBox<>();
            infoBox.setSns(sns);
            infoBox.setTenantId(tenantId);
            mqttCloudSender.sendMQTTMessage(LedConst.CLOUD_TOPIC_LED_VERSION,JSON.toJSONString(infoBox));
        }
        log.info("停止"+cmdType.getStateInfo()+"任务...");
        return ledSns;
    }

    /**
     * 将用户发送的灯与redis进行比较，如果redis存在，说明mqtt已经返回
     * @param obj 传入的类型
     * @return
     */
    private boolean compareIsSuccess(String sn,Object obj,Long ts) {
        boolean flag = false;
        if(obj.getClass().equals(LedVersion.class)){//灯版本
            if(redisService.hasKey(LedConst.REDIS_SETVERSION_RETURN+sn+"_")){
                flag = true;
            }
        }
        return flag;
    }
}