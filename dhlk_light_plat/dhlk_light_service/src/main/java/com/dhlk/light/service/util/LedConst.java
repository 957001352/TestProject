package com.dhlk.light.service.util;


import java.util.Map;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/6/12
 */
public class LedConst {
    //redis 中灯数据存储
    public final static String REDIS_ONOFF = "REDIS_ONOFF_";//灯开关
    public final static String REDIS_POWER = "REDIS_POWER_"; //灯能耗
    public final static String REDIS_ONLINE = "REDIS_ONLINE_"; //灯在线
    public final static String REDIS_BRIGHT = "REDIS_BRIGHT_"; //灯亮度
    public final static String REDIS_RESTART= "REDIS_RESTART_"; //灯重启
    public final static String REDIS_FAULT= "REDIS_FAULT_"; //灯故障
    public final static String REDIS_PEOPLEFELL = "REDIS_PEOPLEFELL_"; //灯人感触发状态
    public final static String REDIS_PEOPLEONOFF = "REDIS_PEOPLEONOFF_"; //灯人感开关状态
    public final static String REDIS_LIGHTFELL = "REDIS_LIGHTFELL_"; //灯光感




    public final static String REDIS_LIGHTFELL_RETURN = "REDIS_LIGHTFELL_RETURN_"; //灯光感设置返回
    public final static String REDIS_PEOPLEFELL_RETURN = "REDIS_PEOPLEFELL_RETURN_"; //人感设置返回
    public final static String REDIS_WIFI_RETURN = "REDIS_WIFI_RETURN"; //wifi设置返回
    public final static String REDIS_VERSION_RETURN = "REDIS_VERSION_RETURN"; //wifi设置返回
    public static final String REDIS_FIRMWARE_UPDATE_RETURN = "REDIS_FIRMWARE_UPDATE_RETURN";//固件升级返回
    //云端发送给本地mqtt主题
    public final static String LOCAL_TOPIC_UPGRADE= "LOCAL_TOPIC_UPGRADE"; //灯升级


    public final static String TOPIC_CLOUDTOLOCAL_PROXY_DATA = "cloudToLocalProxyData";//云端发送本地主题,发送代理数据

    public final static String TOPIC_CLOUDTOLOCAL = "cloudToLocal";//云端发送本地主题
    public final static String TOPIC_LOCALTOCLOUD= "localToCloud";//云端订阅本地主题


    public final static String LOCAL_TOPIC_TASK_ONOFF = "LOCAL_TOPIC_TASK_ONOFF";//定时任务开关
    public final static String LOCAL_TOPIC_TASK_DATASYNC = "LOCAL_TOPIC_TASK_DATASYNC";//定时数据同步
    public final static String LOCAL_TOPIC_TASK_DELETE = "LOCAL_TOPIC_TASK_DELETE";//云端定时任务删除
    public final static String LOCAL_TOPIC_POWER_DATASYNC = "LOCAL_TOPIC_POWER_DATASYNC";//本地同步能耗、定时数据到云端
    public final static String LOCAL_TOPIC_ONLINE_DATASYNC = "LOCAL_TOPIC_ONLINE_DATASYNC";//本地同步在线时长、定时数据到云端
    public final static String LOCAL_TOPIC_PEOPLE_FEEL_DATASYNC = "LOCAL_TOPIC_PEOPLE_FEEL_DATASYNC";//本地同步人感、定时数据到云端

    public final static String LOCAL_TOPIC_LED_SAVE = "LOCAL_TOPIC_LED_SAVE";//云端灯保存给本地发消息
    public final static String LOCAL_TOPIC_LED_UPDATE = "LOCAL_TOPIC_LED_UPDATE";//云端灯保存给本地发消息
    public final static String LOCAL_TOPIC_LED_DELETE = "LOCAL_TOPIC_LED_DELETE";//云端灯删除给本地发消息

    public final static String LOCAL_TOPIC_AREA_SAVE = "LOCAL_TOPIC_AREA_SAVE";//云端区域保存给本地发消息
    public final static String LOCAL_TOPIC_AREA_UPDATE = "LOCAL_TOPIC_AREA_UPDATE";//云端区域保存给本地发消息
    public final static String LOCAL_TOPIC_AREA_DELETE = "LOCAL_TOPIC_AREA_DELETE";//云端区域删除给本地发消息

    public final static String LOCAL_TOPIC_SWITCH_SAVE = "LOCAL_TOPIC_SWITCH_SAVE";//云端开关组保存给本地发消息
    public final static String LOCAL_TOPIC_LEDSWITCH_SAVE = "LOCAL_TOPIC_LEDSWITCH_SAVE";//云端开关组中间表发送数据给本地
    public final static String LOCAL_TOPIC_LEDSWITCH_DELETE = "LOCAL_TOPIC_LEDSWITCH_DELETE";//云端删除组-灯关联数据
    public final static String LOCAL_TOPIC_SWITCH_DELETE = "LOCAL_TOPIC_SWITCH_DELETE";//云端删除组数据

    public final static String LOCAL_TOPIC_ORIPOWER_SAVE = "LOCAL_TOPIC_ORIPOWER_SAVE";//云端系统配置保存给本地发数据
    public final static String LOCAL_TOPIC_ORIPOWER_UPDATE = "LOCAL_TOPIC_ORIPOWER_UPDATE";//云端照明设置预设值修改

    public final static String LOCAL_TOPIC_SYS_VERSION = "LOCAL_TOPIC_SYS_VERSION";//云端发往本地主题,提示版本文件下载

    public final static String LOCAL_TOPIC_SYNC_DATA = "LOCAL_TOPIC_SYNC_DATA";//云端发往本地主题,提示同步数据


    //云端订阅本地mqtt主题
    public final static String CLOUD_TOPIC_FAULT= "CLOUD_TOPIC_FAULT"; //灯故障
    public final static String CLOUD_TOPIC_ORIPOWER_SAVE = "CLOUD_TOPIC_ORIPOWER_SAVE";//亮度设置新增
    public final static String CLOUD_TOPIC_ORIPOWER_UPDATE = "CLOUD_TOPIC_ORIPOWER_UPDATE";////亮度设置修改
    public final static String CLOUD_TOPIC_SYNC_DATA = "CLOUD_TOPIC_SYNC_DATA";//本地发往云端主题,用于数据同步回调
    public static final String CLOUD_TOPIC_LED_VERSION = "CLOUD_TOPIC_LED_VERSION";//本地发往云端主题,用于灯版本数据更新

    public final static String DHLK_TENANTID= "dhlk-tenantId"; //租户id

    public final static Integer RETRYCOUNT =0;//重发次数

    public final static Integer SETRETRYCOUNT =3;//wifi设置重发次数
    public final static Integer SETRETRYTIME =20;//wifi设置重发时间  单位秒


    public final static Integer RETRYTIME =10;//重发时间  单位秒

    public final static Integer REDISTTIME =10;//灯数据redis数据存储时间

    public final static Integer BRIGHT =30;//调光测试, 功率30

    public final static String REDIS_RECORD_REFRESH_UPLEDPARAM_TIME = "REDIS_RECORD_REFRESH_UPLEDPARAM_TIME";//记录点击灯参数列表的时间缓存到redis
    public final static Integer BANTIME = 5;//记录点击的时间缓存到redis

    public final static Integer RECONCATTIME =10;//mqtt断开重新连接时间  单位秒


    public final static String REDIS_RECORD_REFRESH_ONOFF_TIME_ = "REDIS_RECORD_REFRESH_ONOFF_TIME_";//记录点击开关的时间缓存到redis
    public final static String REDIS_RECORD_REFRESH_SETLIGHT_TIME_ = "REDIS_RECORD_REFRESH_SETLIGHT_TIME_";//记录点击设置亮度的时间缓存到redis
    public final static String REDIS_RECORD_REFRESH_UPGRADE_TIME_ = "REDIS_RECORD_REFRESH_UPGRADE_TIME_";//记录点击升级的时间缓存到redis
    public final static String REDIS_RECORD_REFRESH_SETLIGHTFEEL_TIME_ = "REDIS_RECORD_REFRESH_SETLIGHTFEEL_TIME_";//记录点击设置光感的时间缓存到redis
    public final static String REDIS_RECORD_REFRESH_SETPEOPLEFEEL_TIME_ = "REDIS_RECORD_REFRESH_SETPEOPLEFEEL_TIME_";//记录点击设置人感的时间缓存到redis
    public final static String REDIS_RECORD_REFRESH_SETWIFI_TIME_ = "REDIS_RECORD_REFRESH_SETWIFI_TIME_";//记录点击设置wifi的时间缓存到redis

}
