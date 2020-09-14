package com.dhlk.light.factory.util;


/**
 * @Description
 * @Author lpsong
 * @Date 2020/6/12
 */
public class LedConst {
    //redis 中灯数据存储
    public final static String REDIS_POWER = "REDIS_POWER_"; //灯能耗
    public final static String REDIS_PEOPLEFELL = "REDIS_PEOPLEFELL_"; //灯人感触发状态
    public final static String REDIS_PEOPLEONOFF = "REDIS_PEOPLEONOFF_"; //灯人感开关状态
    public final static String REDIS_LIGHTFELL = "REDIS_LIGHTFELL_"; //灯光感开关状态

    public final static String REDIS_TENANTCOUD = "REDIS_TENANTCOUD_"; //租户code

    public final static String REDIS_TENANTID = "REDIS_TENANTID_"; //租户ID

    public final static String REDIS_MQTTISRIGHT = "REDIS_MQTTISRIGHT_"; //mqtt是否连接正常

    //本地订阅云端主题更新定时任务
    public final static String LOCAL_TOPIC_TASK_DATASYNC = "LOCAL_TOPIC_TASK_DATASYNC";//定时任务开关
    public final static String LOCAL_TOPIC_TASK_DELETE = "LOCAL_TOPIC_TASK_DELETE";//定时任务删除
    public final static String LOCAL_TOPIC_POWER_DATASYNC = "LOCAL_TOPIC_POWER_DATASYNC";//本地同步能耗、定时数据到云端
    public final static String LOCAL_TOPIC_PEOPLE_FEEL_DATASYNC = "LOCAL_TOPIC_PEOPLE_FEEL_DATASYNC";//本地同步人感、定时数据到云端
    public final static String LOCAL_TOPIC_ONLINE_DATASYNC = "LOCAL_TOPIC_ONLINE_DATASYNC";//本地同步在线时长、定时数据到云端

    public final static String LOCAL_TOPIC_LED_SAVE = "LOCAL_TOPIC_LED_SAVE";//本地订阅云端灯的数据保存
    public final static String LOCAL_TOPIC_LED_UPDATE = "LOCAL_TOPIC_LED_UPDATE";//本地订阅云端灯的数据修改
    public final static String LOCAL_TOPIC_LED_DELETE = "LOCAL_TOPIC_LED_DELETE";//本地订阅云端灯的数据删除

    public final static String LOCAL_TOPIC_AREA_SAVE = "LOCAL_TOPIC_AREA_SAVE";//云端区域保存给本地发消息
    public final static String LOCAL_TOPIC_AREA_DELETE = "LOCAL_TOPIC_AREA_DELETE";//云端区域删除给本地发消息

    public final static String LOCAL_TOPIC_SWITCH_SAVE = "LOCAL_TOPIC_SWITCH_SAVE";//本地订阅云端组数据
    public final static String LOCAL_TOPIC_LEDSWITCH_SAVE = "LOCAL_TOPIC_LEDSWITCH_SAVE";//本地订阅云端开关组中间表数据
    public final static String LOCAL_TOPIC_LEDSWITCH_DELETE = "LOCAL_TOPIC_LEDSWITCH_DELETE";//删除本地订阅云端数据,灯-组
    public final static String LOCAL_TOPIC_SWITCH_DELETE = "LOCAL_TOPIC_SWITCH_DELETE";//删除本地订阅云端数据,组
    public final static String LOCAL_TOPIC_ORIPOWER_SAVE = "LOCAL_TOPIC_ORIPOWER_SAVE";//本地订阅云端数据系统配置数据新增
    public final static String LOCAL_TOPIC_ORIPOWER_UPDATE = "LOCAL_TOPIC_ORIPOWER_UPDATE";//本地订阅云端预设照明数据修改

    public final static String LOCAL_TOPIC_SYS_VERSION = "LOCAL_TOPIC_SYS_VERSION";//本地订阅云端主题,提示版本文件下载
    public final static String LOCAL_TOPIC_SYNC_DATA = "LOCAL_TOPIC_SYNC_DATA";//云端发往本地主题,提示同步数据
    public static final String REDIS_SETVERSION_RETURN = "REDIS_SETVERSION_RETURN_"; //用来记录设置版本返回存入redis的key的前缀



    //本地与云端替通讯主题
    public final static String TOPIC_CLOUDTOLOCAL = "cloudToLocal";//云端发送本地主题
    public final static String TOPIC_LOCALTOCLOUD= "localToCloud";//云端订阅本地主题
    public final static String TOPIC_LOCALTOHDFS= "dhlk_lighting";//云端hdfs订阅主题
    //本地与硬件通讯主题
    public final static String LOCALTOHARD_TOPIC_DHLKLIGHT = "bi_light";//本地发送给硬件主题
    public final static String HARDTOLOCAL_TOPIC_BILIGHT= "dhlk_light/";//硬件发送本地mqtt主题

    //本地发送主题给云端
    public final static String CLOUD_TOPIC_ORIPOWER_SAVE= "CLOUD_TOPIC_ORIPOWER_SAVE";
    public final static String CLOUD_TOPIC_ORIPOWER_UPDATE= "CLOUD_TOPIC_ORIPOWER_UPDATE";
    public final static String CLOUD_TOPIC_SYNC_DATA = "CLOUD_TOPIC_SYNC_DATA";//本地发往云端主题,用于数据同步回调
    public static final String CLOUD_TOPIC_LED_VERSION = "CLOUD_TOPIC_LED_VERSION";


    public final static String REDIS_RECORDING_LOG = "REDIS_RECORDING_LOG"; //日志记录

    public final static String REDIS_MQTT_LED_POWER = "REDIS_MQTT_LED_POWER"; //存储mqtt订阅回来的灯能耗信息



    public final static String DHLK_TENANTID= "dhlk-tenantId"; //租户id

    public final static Integer RETRYCOUNT =0;//重发次数

    public final static Integer REDISTTIME =30;//灯数据redis数据存储时间

    public final static Integer RETRYTIME =10;//重发时间  单位秒

    public final static Integer SOCKETTIME =5;//webSocket刷新时间  单位秒

    public final static Integer BANTIME = 5;//记录点击的时间缓存到redis


    public final static Integer RECONCATTIME =10;//mqtt断开重新连接时间  单位秒


    public final static String REDIS_RECORD_REFRESH_ONOFF_TIME_ = "REDIS_RECORD_REFRESH_ONOFF_TIME_";//记录点击开关的时间缓存到redis


}
