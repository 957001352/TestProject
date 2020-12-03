package com.dhlk.light.service.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.entity.basicmodule.BiProxyServerInfo;
import com.dhlk.entity.light.*;
import com.dhlk.light.service.dao.LedRecordDao;
import com.dhlk.light.service.enums.LedEnum;
import com.dhlk.light.service.mqtt.MqttSendServer;
import com.dhlk.light.service.service.LedParamInfoService;
import com.dhlk.service.RedisService;
import com.dhlk.util.AuthUserUtil;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.DateUtils;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
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


    @Resource
    private MqttSendServer mqttSendServer;

    @Autowired
    private RedisService redisService;
    @Autowired
    private HeaderUtil headerUtil;
    @Autowired
    private LedRecordDao ledRecordDao;
    @Autowired
    private AuthUserUtil authUserUtil;
    @Autowired
    private LedParamInfoService ledParamInfoService;
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
                    if (ledEnum.getState().equals(LedEnum.RESTART.getState())) {
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
     * @param onOff
     * @return
     */
    private List<String> ledOnOffToMqttIsSuccess(String sns, Long ts,Integer onOff,String operate) {
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
                                updateRedisLedStatus(sn, onOff,operate);
                            }
                            executorService.shutdown();
                            countDown.countDown();
                        }
                    }
                }, 0, 250, TimeUnit.MILLISECONDS);
            }
        try {
            countDown.await();//阻塞在这里,等到所有线程返回结果
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ledSns;
    }
    /**
     * 开灯，光感，人感设置完成更新结果
     * @param sn
     * @param status
     * @return
     */
    public void updateRedisLedStatus(String sn,Integer status,String type){
        Object power = redisService.get(LedConst.REDIS_POWER + sn);
        //获取能耗信息 判断redis里存在该sn的灯的能耗信息，如果有直接转换，没有就初始化
        LedPower ledPower = null;
        if (power == null) {
            ledPower = new LedPower();
            ledPower.setLedSn(sn);
        } else {
            ledPower = JSONObject.parseObject(power.toString(), LedPower.class);
        }
        if(type.equals("onOff")){
            ledPower.setStatus(String.valueOf(status));
        }else if(type.equals("light")){
            if(status==1){
                redisService.set(LedConst.REDIS_LIGHTFELL + sn, status.toString(), LedConst.REDISTTIME);  //增加30秒
                //开光感，关人感
                redisService.del(LedConst.REDIS_PEOPLEFELL + sn);//人感触发上报值
                redisService.del(LedConst.REDIS_PEOPLEONOFF+sn);  //人感开关状态值
            }else{
                redisService.del(LedConst.REDIS_LIGHTFELL + sn);
            }
        }else if(type.equals("people")){
            if(status==1){
                redisService.set(LedConst.REDIS_PEOPLEFELL + sn, status.toString(), LedConst.REDISTTIME);  //增加30秒
                //开人感，关光感
                redisService.del(LedConst.REDIS_LIGHTFELL + sn);
            }else{
                redisService.del(LedConst.REDIS_PEOPLEFELL + sn);
            }
        }
        redisService.set(LedConst.REDIS_POWER + sn, JSON.toJSONString(ledPower), LedConst.REDISTTIME);//增加30秒
    }
    /**
     * 本地mqtt消息发送
     * @param data
     * @return
     */
    public void sendMessToMqtt(LedResult data){
        mqttSendServer.sendMQTTMessage(LedConst.TOPIC_CLOUDTOLOCAL,JSONObject.toJSONString(data));
    }
    /**
    * 插入发送记录
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
            LedRecord ledRecord = new LedRecord(ts.toString(),com_type.getStateInfo(), JSONObject.toJSONString(ledResult), userName, "发送"+sns.length+"盏灯","cloud", DateUtils.stampToTime(ts),"失败"+failList.size()+"盏灯,"+failList.toString(),DateUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"),ledResult.getTenantId());
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
    public  void ledOnOrOff(String sns, String status){
        if(!CheckUtils.isNull(sns)){
            String userName=this.userName();
            Integer tenantId=headerUtil.tenantId();
            //控制开关灯后3s websocket不给页面推送数据
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            Future future = executorService.scheduleAtFixedRate(new Runnable() {
                private int count = 0;
                Long ts=System.currentTimeMillis();
                @Override
                public void run() {
                    count++;
                    //给mqtt发送开灯命令
                    for (String sn : sns.split(",")) {
                        sendMessToMqtt(new LedResult(sn,LedEnum.ONOFF, new LedOnOff(Integer.parseInt(status)),ts,tenantId));
                    }
                    //比对给没有mqtt未返回的灯重新发送
                    List<String> list = ledOnOffToMqttIsSuccess(sns,ts,Integer.parseInt(status),"onOff");
                    if (count > LedConst.RETRYCOUNT || list.size() == 0) {
                        redisService.set(LedConst.REDIS_WEBSOCKET, 0, 3);  //增加30秒
                        //发送两次或者灯已经全部开启/关闭则停止定时任务
                        executorService.shutdown();
                        //更新返回结果
                        updateLedReord(userName,sns.split(","),ts,LedEnum.ONOFF,list,new LedResult(sns,LedEnum.ONOFF, new LedOnOff(Integer.parseInt(status)),ts,tenantId));

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
            Integer tenantId=headerUtil.tenantId();
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            Future future = executorService.scheduleAtFixedRate(new Runnable() {
                private int count = 0;
                Long ts=System.currentTimeMillis();
                @Override
                public void run() {
                    count++;
                    //给mqtt发送开灯命令
                    for(String sn:sns.split(",")) {
                        sendMessToMqtt(new LedResult(sn,LedEnum.FLASHING,ts,tenantId));
                    }
                    //比对给没有mqtt未返回的灯重新发送
                    List<String> list = compareToMqttIsSuccess(sns,ts,LedEnum.FLASHING);
                    if (count > LedConst.RETRYCOUNT||list.size()==0) {
                        //发送两次或者灯已经全部开启/关闭则停止定时任务
                        executorService.shutdown();
                        log.info("停止闪一闪任务...");
                        updateLedReord(userName,sns.split(","),ts,LedEnum.FLASHING,list,new LedResult(sns,LedEnum.FLASHING,ts,tenantId));
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
    public void ledBrightness(String sns,Integer bright){
        if(!CheckUtils.isNull(sns)){
            String userName=this.userName();
            Integer tenantId=headerUtil.tenantId();
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            Future future = executorService.scheduleAtFixedRate(new Runnable() {
                private int count = 0;
                Long ts=System.currentTimeMillis();
                @Override
                public void run() {
                    count++;
                    //给mqtt发送设置亮度命令
                    for(String sn:sns.split(",")) {
                        sendMessToMqtt(new LedResult(sn,LedEnum.LIGHTNESS,new LedBrightness(bright),ts,tenantId));
                    }
                    //比对给没有mqtt未返回的灯重新发送
                    List<String> list = compareToMqttIsSuccess(sns,ts,LedEnum.LIGHTNESS);
                    if (count > LedConst.RETRYCOUNT||list.size()==0) {
                        //发送两次或者灯已经全部开启/关闭则停止定时任务
                        executorService.shutdown();
                        log.info("停止灯亮度设置任务...");
                        updateLedReord(userName,sns.split(","),ts,LedEnum.LIGHTNESS,list,new LedResult(sns,LedEnum.LIGHTNESS,new LedBrightness(bright),ts,tenantId));
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
            Integer tenantId=headerUtil.tenantId();
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            Future future = executorService.scheduleAtFixedRate(new Runnable() {
                private int count = 0;
                Long ts=System.currentTimeMillis();
                @Override
                public void run() {
                    count++;
                    //给mqtt发送设置亮度命令
                    for(String sn:sns.split(",")) {
                        sendMessToMqtt(new LedResult(sn,LedEnum.RESTART,ts,tenantId));
                    }
                    //比对给没有mqtt未返回的灯重新发送
                    List<String> list = compareToMqttIsSuccess(sns,ts,LedEnum.RESTART);
                    if (count > LedConst.RETRYCOUNT||list.size()==0) {
                        //发送两次或者灯已经全部开启/关闭则停止定时任务
                        executorService.shutdown();
                        log.info("停止灯重启任务...");
                        updateLedReord(userName,sns.split(","),ts,LedEnum.RESTART,list,new LedResult(sns,LedEnum.RESTART,ts,tenantId));
                    }
                }
            },0, LedConst.RETRYTIME, TimeUnit.SECONDS);//延时5秒，每20秒发送一次，发送2次结束该任务。
        }
    }






    /*
     * 开关重启 此方法暂时不用
     * @param sn
     * @return
     */
    public void switchRestart(String sns){
        if(!CheckUtils.isNull(sns)){
            Integer tenantId=headerUtil.tenantId();
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            Future future = executorService.scheduleAtFixedRate(new Runnable() {
                private int count = 0;
                Long ts=System.currentTimeMillis();
                @Override
                public void run() {
                    count++;
                    //给mqtt发送设置亮度命令
                    for(String sn:sns.split(",")) {
                        LedResult ledResult= new LedResult(sn,LedEnum.RESTART,LedEnum.DEVSWITCH,ts);
                        ledResult.setTenantId(tenantId);
                        sendMessToMqtt(ledResult);
                    }
                    //比对给没有mqtt未返回的灯重新发送
                    List<String> list= compareToMqttIsSuccess(sns,ts,LedEnum.RESTART);
                    if (count > LedConst.RETRYCOUNT||list.size()==0) {
                        //发送两次或者灯已经全部开启/关闭则停止定时任务
                        executorService.shutdown();
                        log.info("停止开关重启任务...");
                    }
                }
            },0, LedConst.RETRYTIME, TimeUnit.SECONDS);//延时5秒，每20秒发送一次，发送2次结束该任务。
        }
    }
    /**
     *  灯设置组  此方法暂时不用
     * @param sns
     * @param grpId
     * @return
     */
    public void setLedGrpId(String sns,Integer grpId){
        if(!CheckUtils.isNull(sns)){
            Integer tenantId=headerUtil.tenantId();
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            Future future = executorService.scheduleAtFixedRate(new Runnable() {
                private int count = 0;
                Long ts=System.currentTimeMillis();
                @Override
                public void run() {
                    count++;
                    //比对给没有mqtt未返回的灯重新发送
                    //给mqtt发送设置亮度命令
                    for(String sn:sns.split(",")) {
                        JSONObject grp=new JSONObject();
                        grp.put("grp_id",grpId);
                        sendMessToMqtt(new LedResult(sn,LedEnum.GRPID,grp,ts,tenantId));
                    }
                    List<String> list = compareToMqttIsSuccess(sns,ts,LedEnum.LIGHTNESS);
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
     * 开关组设置  此方法暂时不用
     * @param sn
     * @param groupObj
     * @return
     */
    public void setSwitchGrpId(String sn,JSONObject groupObj){
        if(!CheckUtils.isNull(sn)){
            Integer tenantId=headerUtil.tenantId();
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            Future future = executorService.scheduleAtFixedRate(new Runnable() {
                private int count = 0;
                Long ts=System.currentTimeMillis();
                @Override
                public void run() {
                    count++;
                    //给mqtt发送设置亮度命令
                    sendMessToMqtt(new LedResult(sn,LedEnum.SWITCHSET,LedEnum.DEVSWITCH,groupObj,ts,tenantId));
                    //比对给没有mqtt未返回的灯重新发送
                    List<String> list= compareToMqttIsSuccess(sn,ts,LedEnum.SWITCHSET);
                    if (count > LedConst.RETRYCOUNT||list.size()==0) {
                        //发送两次或者灯已经全部开启/关闭则停止定时任务
                        executorService.shutdown();
                        log.info("停止开关设置任务...");
                    }
                }
            },0, LedConst.RETRYTIME, TimeUnit.SECONDS);//延时5秒，每20秒发送一次，发送2次结束该任务。
        }
    }


    /**
     * 获取wifi配置
     * @param sns 开关sn的集合逗号隔开
     */
    public void acquireWifi(String sns,LedWifiInfo ledWifiInfo,Integer tenantId,int status) {
        sendMessageToMqttByThree(sns,LedEnum.GETWIFI,LedEnum.DEVLIGHT,ledWifiInfo,tenantId,status);
    }
    /**
     * 获取光感配置
     * @param sns 灯sn的集合逗号隔开
     * @param intensityInfo 光感数据
     * @return
     */
    public void acquireIntensity(String sns, IntensityInfo intensityInfo,Integer tenantId,int status) {
        sendMessageToMqttByThree(sns,LedEnum.GETLIGHTFELL,LedEnum.DEVLIGHT,intensityInfo,tenantId,status);
    }
    /**
     * 获取人感配置
     * @param sns 灯sn的集合逗号隔开
     * @param peopleFeelInfo 人感数据
     */
    public void acquirePeopleFeel(String sns, PeopleFeelInfo peopleFeelInfo,Integer tenantId,int status) {
        sendMessageToMqttByThree(sns,LedEnum.GETPEOPLEFELL,LedEnum.DEVLIGHT,peopleFeelInfo,tenantId,status);
    }
    /**
     * 获取版本
     * @param sns sn的集合逗号隔开
     * @param version 版本数据
     */
    public void acquireVersion(String sns, Version version,Integer tenantId,int status) {
        sendMessageToMqttByThree(sns,LedEnum.GETVERSION,LedEnum.DEVLIGHT,version,tenantId,status);
    }




    /**
     * 设置光感
     * @param sns 灯sn的集合逗号隔开
     * @param intensityInfo 光感数据
     * @return
     */
    public void intensityContro(String sns, IntensityInfo intensityInfo) {
        intensityInfo.setIllumi_mode(0);
        sendMessageToMqttNoReturn(sns,LedEnum.SETLIGHTFELL,LedEnum.DEVLIGHT,intensityInfo,headerUtil.tenantId());
    }
    /**
     * 设置开关wifi
     * @param sns 开关sn的集合逗号隔开
     * @param switchWifiInfo wifi数据
     */
    public void switchWifiContro(String sns, SwitchWifiInfo switchWifiInfo) {
        sendMessageToMqttNoReturn(sns,LedEnum.SETWIFI,LedEnum.DEVSWITCH,switchWifiInfo,headerUtil.tenantId());
    }
    /**
     * 设置人感
     * @param sns 灯sn的集合逗号隔开
     * @param peopleFeelInfo 人感数据
     */
    public void peopleFeelContro(String sns, PeopleFeelInfo peopleFeelInfo) {
        sendMessageToMqttNoReturn(sns,LedEnum.SETPEOPLEFELL,LedEnum.DEVLIGHT,peopleFeelInfo,headerUtil.tenantId());
    }

    /**
     * 设置灯wifi
     * @param sns 灯sn的集合逗号隔开
     * @param ledWifiInfo wifi数据
     */
    public void wifiContro(String sns, LedWifiInfo ledWifiInfo) {
        sendMessageToMqttNoReturn(sns,LedEnum.SETWIFI,LedEnum.DEVLIGHT,ledWifiInfo,headerUtil.tenantId());
    }


    /**
     * 灯固件升级
     * @param infoBox 封装数据
     */
    public void ledFirmwareUpgrade(InfoBox<LedVersionInfo> infoBox) throws MqttException {
        infoBox.setTenantId(headerUtil.tenantId());
        mqttSendServer.sendMQTTMessageToLocal(LedConst.LOCAL_TOPIC_SYS_VERSION,JSON.toJSONString(infoBox));
    }

    /**
     * 给本地发送同步数据
     * @param syncDataResult 封装数据
     */
    public void syncDataToLocal(SyncDataResult syncDataResult) throws MqttException {
        mqttSendServer.sendMQTTMessageToLocal(LedConst.LOCAL_TOPIC_SYNC_DATA,JSON.toJSONString(syncDataResult));
    }
    /**
     * 开关固件升级
     * @param sns sn的集合逗号隔开
     * @param ledVersion 版本数据
     */
    public void switchFirmwareUpgrade(String sns, LedVersion ledVersion) {
        sendMessageToMqttByThree(sns,LedEnum.FIRMWAREUPDATE,LedEnum.DEVSWITCH,ledVersion,headerUtil.tenantId(),1);
    }




    /**
     * 将用户发送的灯与redis进行比较，如果redis存在，说明mqtt已经返回
     * @param obj 传入的类型
     * @return
     */
    private boolean compareIsSuccess(String sn,Object obj,Long ts) {
        boolean flag = false;
        List<String> ledSns=new ArrayList<>();
        if(obj.getClass().equals(IntensityInfo.class)){//光感
            if(redisService.hasKey(LedConst.REDIS_LIGHTFELL_RETURN+sn+"_"+ts)){
                flag = true;
            }
        }else if(obj.getClass().equals(PeopleFeelInfo.class)){//人感
            if(redisService.hasKey(LedConst.REDIS_PEOPLEFELL_RETURN+sn+"_"+ts)){
                flag = true;
            }
        }else if(obj.getClass().equals(LedWifiInfo.class)){//wifi
            if(redisService.hasKey(LedConst.REDIS_WIFI_RETURN+sn+"_"+ts)){
                flag = true;
            }
        }else if(obj.getClass().equals(LedVersion.class)){//固件升级
            if(redisService.hasKey(LedConst.REDIS_FIRMWARE_UPDATE_RETURN+sn+"_"+ts)){
                flag = true;
            }
        }else if(obj.getClass().equals(Version.class)){//版本
            if(redisService.hasKey(LedConst.REDIS_VERSION_RETURN+sn+"_"+ts)){
                flag = true;
            }
        }
        return flag;
    }


    /**
     *
     * @param sns 灯的sn集合用逗号隔开
     * @param cmdType 发个mqtt的cmd_type
     * @param data 传入的数据
     * @return
     */
    public void sendMessageToMqttByThree(String sns,LedEnum cmdType,LedEnum devType,Object data,Integer tenantId,int status){
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
                        LedResult ledResult = new LedResult(sn,cmdType,devType,data,ts,tenantId);
                        if(LedWifiInfo.class.equals(data.getClass())){
                            ledResult = new LedResult(sn,cmdType,devType,ts,tenantId);
                        }
                        sendMessToMqtt(ledResult);
                    }
                    //比对给没有mqtt未返回重新发送
                    List<String> list = commandToMqttIsSuccess(sns,cmdType,data,ts,status);
                    if (count > LedConst.SETRETRYCOUNT || list.size() == 0) {
                        //发送两次或者已经全部返回则停止定时任务
                        executorService.shutdown();
                    }
                }
            }, 0, LedConst.SETRETRYTIME, TimeUnit.SECONDS);//延时5秒，每20秒发送一次，发送2次结束该任务。
        }
    }


    public List<String> commandToMqttIsSuccess(String sns,LedEnum cmdType, Object data,long ts,int status){
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
                        if (backRes) {
                            ledSns.add(sn);
                        }
                        executorService.shutdown();
                        countDown.countDown();
                    }
                }
            }, 0, 300, TimeUnit.MILLISECONDS);
        }
        try {
            countDown.await();//阻塞在这里,等到所有线程返回结果
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(ledSns.size() == 0){
            return ledSns;
        }
        if(LedEnum.GETLIGHTFELL== cmdType){
            updateControLedParam(ledSns,LedConst.REDIS_LIGHTFELL_RETURN,ts);
        }else if(LedEnum.GETPEOPLEFELL == cmdType){
            updateControLedParam(ledSns,LedConst.REDIS_PEOPLEFELL_RETURN,ts);
        }else if(LedEnum.GETWIFI== cmdType){
            updateControLedParam(ledSns,LedConst.REDIS_WIFI_RETURN,ts);
        }else if(LedEnum.GETVERSION== cmdType){
            updateControLedParam(ledSns,LedConst.REDIS_VERSION_RETURN,ts);
        }
        log.info("停止"+cmdType.getStateInfo()+"任务...");
        return ledSns;
    }


    /**
     * 设置参数时更新灯参数数据
     */

    public void updateControLedParam(List<String> sns,String key,long ts){
        List<LedParamInfo> insertData = new ArrayList<>();
        List<LedParamInfo> updateData = new ArrayList<>();
        for (String sn:sns){
            //更新灯参数数据库数据
            String info = redisService.get(key+sn + "_" + ts)+"";
            if(!"null".equals(info) && !"".equals(info)){
                LedParamInfo ledParamInfo = new LedParamInfo();
                if(LedConst.REDIS_LIGHTFELL_RETURN.equals(key)){
                    ledParamInfo = new LedParamInfo(JSON.parseObject(info,IntensityInfo.class));
                }else if(LedConst.REDIS_PEOPLEFELL_RETURN.equals(key)){
                    ledParamInfo = new LedParamInfo(JSON.parseObject(info,PeopleFeelInfo.class));
                }else if(LedConst.REDIS_WIFI_RETURN.equals(key)){
                    ledParamInfo = new LedParamInfo(JSON.parseObject(info,LedWifiInfo.class));
                }else if(LedConst.REDIS_VERSION_RETURN.equals(key)){
                    ledParamInfo = new LedParamInfo(JSON.parseObject(info).getString("version"));
                }
                ledParamInfo.setSn(sn);
                Integer ledParamInfoId = ledParamInfoService.isExistSn(ledParamInfo.getSn());
                if ( ledParamInfoId != null ) {
                    ledParamInfo.setId(ledParamInfoId);
                    updateData.add(ledParamInfo);
                } else {
                    insertData.add(ledParamInfo);
                }
            }
        }
        if(insertData.size() > 0)
            ledParamInfoService.insertList(insertData);

        if(updateData.size() > 0){
            ledParamInfoService.updateList(updateData);

        }
    }


    /**
     *人感，光感，wifi设置命令发送
     * @param sns 灯的sn集合用逗号隔开
     * @param cmdType 发个mqtt的cmd_type
     * @param data 传入的数据
     * @return 无返回
     */
    public void sendMessageToMqttNoReturn(String sns,LedEnum cmdType,LedEnum devType,Object data,Integer tenantId){
        String userName=this.userName();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        Future future = executorService.scheduleAtFixedRate(new Runnable() {
            private int count = 0;
            Long ts=System.currentTimeMillis();
            @Override
            public void run() {
                count++;
                //给mqtt发送命令
                for (String sn : sns.split(",")) {
                    LedResult ledResult = new LedResult(sn,cmdType,devType,data,ts,tenantId);
                    if(data == null){
                        ledResult = new LedResult(sn,cmdType,devType,ts);
                    }
                    sendMessToMqtt(ledResult);
                }
                List<String> list =null;
                if(data.getClass().equals(IntensityInfo.class)){
                    IntensityInfo light= (IntensityInfo) data;
                    list = ledOnOffToMqttIsSuccess(sns,ts,light.getOn_off(),"light");
                }else   if(data.getClass().equals(PeopleFeelInfo.class)){
                    PeopleFeelInfo people= (PeopleFeelInfo) data;
                    list = ledOnOffToMqttIsSuccess(sns,ts,people.getOn_off(),"people");
                }else   if(data.getClass().equals(LedWifiInfo.class)){
                    list = compareToMqttIsSuccess(sns,ts,cmdType);
                }
                if (count > LedConst.RETRYCOUNT || list.size() == 0) {
                    executorService.shutdown();
                    if(LedEnum.SETLIGHTFELL==cmdType){
                        acquireIntensity(sns, new IntensityInfo(),tenantId,1);
                    }else if(LedEnum.SETPEOPLEFELL==cmdType){
                        acquirePeopleFeel(sns,new PeopleFeelInfo(),tenantId,1);
                    }
//                    else if(LedEnum.SETWIFI==cmdType){
//                        acquireWifi(sns,new LedWifiInfo(),tenantId,1);
//                    }

                    LedResult ledResult = new LedResult(sns,cmdType,devType,data,ts,tenantId);
                    if(data == null){
                        ledResult = new LedResult(sns,cmdType,devType,ts,tenantId);
                    }
                    updateLedReord(userName,sns.split(","),ts,cmdType,list,ledResult);
                }
            }
        }, 0, LedConst.RETRYTIME, TimeUnit.SECONDS);//延时5秒，每20秒发送一次，发送2次结束该任务。
    }

    /**
     * 增加代理，发给本地的数据
     */
    public void addReseller(BiProxyServerInfo biProxyServerInfo) throws MqttException {
        mqttSendServer.sendMQTTMessageToLocal(LedConst.TOPIC_CLOUDTOLOCAL_PROXY_DATA,JSON.toJSONString(biProxyServerInfo));
    }

    /**
     * 获取返回参数，判断是否设置成功
     */
    public List<String> sendMessageToMqttControWifi(String sns, Integer tenantId){
        CountDownLatch countDown = new CountDownLatch(1);//设置线程阻塞等待
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        Long ts=System.currentTimeMillis();
        String[] split = sns.split(",");
        List<String> list = new ArrayList<>();
        Future future = executorService.scheduleAtFixedRate(new Runnable() {
            private int count = 0;
            @Override
            public void run() {
                count++;
                //给mqtt发送命令
                for (String sn : split) {
                    sendMessToMqtt(new LedResult(sn,LedEnum.GETLIGHTFELL,LedEnum.DEVLIGHT,new IntensityInfo(),ts,tenantId));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sendMessToMqtt(new LedResult(sn,LedEnum.GETPEOPLEFELL,LedEnum.DEVLIGHT,new PeopleFeelInfo(),ts,tenantId));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sendMessToMqtt(new LedResult(sn,LedEnum.GETWIFI,LedEnum.DEVLIGHT,new LedWifiInfo(),ts,tenantId));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sendMessToMqtt(new LedResult(sn,LedEnum.GETVERSION,LedEnum.DEVLIGHT,new Version<Integer>(0),ts,tenantId));
                }
                List<String> backSns = compareToMqttIsSuccessIPWV(sns,ts);
                list.addAll(backSns);

                if (count > LedConst.RETRYCOUNT || list.size() == 0) {
                    executorService.shutdown();
                    countDown.countDown();
                }
            }
        }, 0, LedConst.RETRYTIME, TimeUnit.SECONDS);//延时5秒，每20秒发送一次，发送3次结束该任务。

        try {
            countDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        updateAquireLedParam(split,LedConst.REDIS_LIGHTFELL_RETURN,ts);
        return list;
    }

    /**
     * 获取参数时更新灯参数数据
     */

    public void updateAquireLedParam(String[] sns,String key,long ts){
        List<LedParamInfo> insertData = new ArrayList<>();
        List<LedParamInfo> updateData = new ArrayList<>();
        for (String sn:sns){
            //更新灯参数数据库数据
            String intensityInfo = redisService.get(LedConst.REDIS_LIGHTFELL_RETURN+sn + "_" + ts)+"";
            String peopleFeelInfo = redisService.get(LedConst.REDIS_PEOPLEFELL_RETURN+sn + "_" + ts)+"";
            String ledWifiInfo = redisService.get(LedConst.REDIS_WIFI_RETURN+sn + "_" + ts)+"";
            String versionInfo = redisService.get(LedConst.REDIS_VERSION_RETURN+sn + "_" + ts)+"";

            IntensityInfo intensity = null;
            PeopleFeelInfo peopleFeel = null;
            LedWifiInfo ledWifi = null;
            Version version = null;

            if(!"null".equals(intensityInfo) && !"".equals(intensityInfo)) {
                intensity = JSON.parseObject(intensityInfo, IntensityInfo.class);
            }
            if(!"null".equals(peopleFeelInfo) && !"".equals(peopleFeelInfo)) {
                peopleFeel = JSON.parseObject(peopleFeelInfo, PeopleFeelInfo.class);
            }
            if(!"null".equals(ledWifiInfo) && !"".equals(ledWifiInfo)) {
                ledWifi = JSON.parseObject(ledWifiInfo, LedWifiInfo.class);
            }
            if(!"null".equals(versionInfo) && !"".equals(versionInfo)) {
                version = JSON.parseObject(versionInfo, Version.class);
            }
            LedParamInfo ledParamInfo = new LedParamInfo(intensity,peopleFeel,ledWifi,version);
            if(!"{}".equals(JSON.toJSONString(ledParamInfo))){
                ledParamInfo.setSn(sn);
                Integer ledParamInfoId = ledParamInfoService.isExistSn(ledParamInfo.getSn());
                if ( ledParamInfoId != null ) {
                    ledParamInfo.setId(ledParamInfoId);
                    updateData.add(ledParamInfo);
                } else {
                    insertData.add(ledParamInfo);
                }
            }
        }

        if(insertData.size() > 0)
            ledParamInfoService.insertList(insertData);

        if(updateData.size() > 0){
            ledParamInfoService.updateList(updateData);
        }
    }


    /**
     * 将用户发送的光感、人感、WIFI与redis进行比较，如果redis存在，说明mqtt已经返回
     * @param sns
     * @return
     */
    private List<String> compareToMqttIsSuccessIPWV(String sns, Long ts) {
        List<String> ledSns = new ArrayList<>();
        CountDownLatch countDown = new CountDownLatch(sns.split(",").length);//设置线程阻塞等待
        for (String sn : sns.split(",")) {
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            Future future = executorService.scheduleAtFixedRate(new Runnable() {
                boolean backRes = false;
                boolean backRes1 = false;
                boolean backRes2 = false;
                boolean backRes3 = false;
                boolean backRes4 = false;
                private int count = 0;
                @Override
                public void run() {
                    // rediskey+jsonObject.get("SN") + "_" + jsonObject.get("ts")
                    count++;
                    if(backRes1){
                        backRes1 = redisService.hasKey(LedConst.REDIS_LIGHTFELL_RETURN + sn + "_" + ts);
                    }
                    if(backRes2){
                        backRes2 = redisService.hasKey(LedConst.REDIS_PEOPLEFELL_RETURN + sn + "_" + ts);
                    }
                    if(backRes3){
                        backRes3 = redisService.hasKey(LedConst.REDIS_WIFI_RETURN + sn + "_" + ts);
                    }
                    if(backRes4){
                        backRes4 = redisService.hasKey(LedConst.REDIS_VERSION_RETURN + sn + "_" + ts);
                    }
                    if(backRes1&&backRes2&&backRes3&&backRes4){
                        backRes = true;
                    }
                    if (backRes || count > 10) {
                        if (!backRes) {
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


    public void refreshParam(String sns,Integer tenantId) {
        sendMessageToMqttControWifi(sns,tenantId);
    }



}