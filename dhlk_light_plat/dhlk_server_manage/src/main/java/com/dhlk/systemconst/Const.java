package com.dhlk.systemconst;

import java.util.Map;

public class Const {
    public static final long SOCKETTIME = 5;//webSocket刷新时间  单位秒
    public static final String REDIS_ALARM_INFO_KEY = "REDIS_ALARM_INFO_KEY";//系统报警信息存入redis的key
    public static final String REDIS_ALARM_RULE_KEY = "REDIS_ALARM_RULE_KEY";//系统报警规则存入redis的key
    public static final String REDIS_SYSTEM_INFO_KEY = "REDIS_SYSTEM_INFO_KEY";//系统信息存入redis的key

    public static final String REDIS_MEMORY_KEY = "REDIS_MEMORY_KEY";//内存信息存入redis的key
    public static final String REDIS_CPU_KEY = "REDIS_CPU_KEY";//cpu信息存入redis的key
    public static final String REDIS_DISK_KEY = "REDIS_DISK_KEY";//磁盘信息存入redis的key

    public static final String MACINE_HANDSHAKE = "machine-handshake";//websocket的握手包
    public static final Integer WS_TS = 1;//websocket推送间隔时间

    public final static String TOPIC_CLOUDTOLOCAL_PROXY_DATA = "cloudToLocalProxyData";//云端发送本地主题,发送代理数据
    public final static String TOPIC_CLOUDTOLOCAL_PROXY_DATA_SUFFIX = "cloudToLocalProxyDataSuffix";//云端发送本地主题后缀



    public final static String SOFTWARECOMPANYNAME = "陕西大航联科科技有限公司";
    public final static String LIMITWORDS = "'||*"; //非法字符
    public final static String LIMITWORDS2 = "’||❄"; //替换字符

    public final static String SYTEMEMAIL = "master@dhlk.com";
    public final static String SYSTEMEAILPASSWORD = "master@dhlk.com";
    public final static String SYSTEMESTMP = "stmp@dhlk.com";

    public static final String BIAS = "/";
    public static final String DHLK_MOM = "dhlk_mom";
    public static final String LOAD_DHLK_CONF= "load_dhlk.conf";
    public static final String DHLKHW= "dhlkhw";

    /**
     * 状态
     * gchen
     */
    public final static Integer STATUS_DELETE = 2;
    public final static Integer STATUS_OK = 0;
    public final static Integer STATUS_BAN = 1;

    /**
     *  前端传入token的header的键
     *  gchen
     */
    public static final String TOKEN_HEADER = "Authorization";
    /**
     *  token缓存item前缀
     *  gchen
     */
    public static final String TOKEN_CACHE_ITEM_PREFIX = "dhlk.cache.item.token.";
    /**
     *  登录权限缓存key
     *  gchen
     */
    public static final String PERMISSIONS_CACHE_KEY = "PERMISSIONS_CACHE_KEY";
    /**
     *  登录token缓存key
     *  gchen
     */
    public static final String TOKEN_CACHE_KEY = "TOKEN_CACHE_KEY";

    /**
     *  登录权限缓存item前缀
     *  gchen
     */
    public static final String PERMISSIONS_CACHE_ITEM_PREFIX = "dhlk.cache.item.permissions.";

    /**
     *  token的过期时间，12个小时
     *  gchen
     */
    public static final long TOKEN_LOSE_TIME = 12*60*60;

    /**
     * 系统允许的文件类型
     */
    public final static String UNLIMITFILENAME = "json|xml|gif|jpg|png|doc|docx|xls|xlsx|ppt|pptx|txt|wps|pdf|bmp|dwg";

    /**
     * 系统允许上传文件的大小(单位：MB)
     */
    public final static Integer ALLOWUPLOADFILESIZE = 500;

    /**
     * 短信类常数,根据需要增加
     */
    public final static String SMSOFFICALURL = "";

    //tb接口api
    public final static String TBLOGINSUCCESSTOKEN="Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5d3hmZGo5OTk5QGxlYW5zaXRlLmNuIiwic2NvcGVzIjpbIlRFTkFOVF9BRE1JTiJdLCJ1c2VySWQiOiJhMTY1NWU2MC0yMGExLTExZWEtOGY2Ni0yNTQzMjcxOTY2OWQiLCJlbmFibGVkIjp0cnVlLCJpc1B1YmxpYyI6ZmFsc2UsInRlbmFudElkIjoiYTE0OGQ1YjAtMjBhMS0xMWVhLThmNjYtMjU0MzI3MTk2NjlkIiwiY3VzdG9tZXJJZCI6IjEzODE0MDAwLTFkZDItMTFiMi04MDgwLTgwODA4MDgwODA4MCIsImlzcyI6InRoaW5nc2JvYXJkLmlvIiwiaWF0IjoxNTg0OTQ0NjM5LCJleHAiOjE1ODQ5NTM2Mzl9.JGKd-zE5VatXWIhWtD1EZsOfqGMkm_3wrWJ4LAmUpaFWWEcffV740yp_0H3wT2KT3RvcbDAlVNfGY0iKC45q8A";
    //用户登录
    public final static String TBUSERLOGIN="/api/auth/login";
    //保存设备
    public final static String TBSAVEDEVICE = "/api/device";
    //删除设备  /api/device/{deviceId}
    public final static String TBDELETEDEVICEBYID = "/api/device";
    //根据id查询设备 /api/device/{deviceId}
    public final static String SELECTTBDEVICEBYID = "/api/device";
    //保存设备共享属性 /api/plugins/telemetry/{entityType}/{entityId}/{scope}
    public final static String SAVEDEVICESHAREDATTRIBUTE= "/api/plugins/telemetry";
    //获取设备历史遥测数据 /api/plugins/telemetry/{entityType}/{entityId}/values/timeseries
    public final static String GETTIMESERIES= "/api/plugins/telemetry";
    //警报 /api/alarm/{entityType}/{entityId}{?searchStatus,status,limit,startTime,endTime,ascOrder,offset,fetchOriginator}
    public final static String GETALARMS= "/api/alarm";
    //根据设备名字查询设备信息 /api/tenant/devices{?deviceName}
    public final static String GETTENANTDEVICE= "/api/tenant/devices";

    //保存租户
    public final static String TBSAVETENANT = "/api/tenant";

    //保存租户管理员
    public final static String TBSAVUSER = "/api/user";

    //flume认证加密私钥
    public final static String FLUMNAUTHPRIVATEKEY = "dhlk_flume";


    /**
     *收件人
     */
    public static Map<String,String> receiverMap=null;
    /**
     * 发送邮件主题
     */
    public static String subject="";
    /**
     * 发送内容
     */
    public static String content="";
    /**
     * 附件地址
     */
    public static String filePath="";


    /**
     * bi 代理相关信息
     */
    public static final String BI_PROXY_SERVER_IP = "192.168.2.114";
    public static final int BI_PROXY_SERVER_PROT = 8090;
    public static final String BI_PROXY_SERVER_USERNAME = "admin";
    public static final String BI_PROXY_SERVER_PASSWORD = "admin";

    public static String BI_PROXY_SERVER_TOKEN = "";

    public static final String BI_LOCAL_NODERED_URL = "localhost:7070";
    public static final int BI_MIN_PORT = 1001;
    public static final int BI_MAX_PORT = 1999;
    public static final String BI_NODERED_SERVER_NAME = "nodered";
    public static final String BI_NAME_PREFIX = "BI-";


    /**
     * @description: docker命令常量
     * @author: wqiang
     * @create: 2020-07-07 14:45
     **/
    //查看所有服务
    public static final String DOCKER_ALL_SERVER_CMD = "docker ps -a";
    //删除服务
    public static final String DOCKER_DELETE_SERVER_CMD = "docker rm ";
    //停止服务
    public static final String DOCKER_STOP_SERVER_CMD = "docker stop ";
    //开启服务
    public static final String DOCKER_START_SERVER_CMD = "docker start ";
    //根据名称查询服务
    public static final String SEARCH_SERVER_BY_NAME_COMMAND="docker ps -aq --filter name=";
    //更具名称运行服务
    public static final String RUN_SERVER_BY_NAME_COMMAND="docker run -di --name=";
    //解释脚本
    public static final String BIN_BASH=" /bin/bash";
    //加载镜像到本地仓库
    public static final String LOAD_MIRROR_TO_LOCAL_COMMAND="docker load --input ";

    /**
     * @description: linex 命令常量
     * @author: wqiang
     * @create: 2020-07-13 10:45
     **/

    //查看所有web后天进程信息
    public static final String GREP_dhlk_COMMAND="ps -eo pid,lstart,cmd | grep dhlk | grep -v grep";
    //停止进程
    public static final String KILL_PROCESS_COMMAND = "kill -9 ";
    //web后台开启进程
    public static final String START_WEB_PROCESS_COMMAND = "nohup java -jar ";
    //启动mysql命令
    public static final String START_MYSQL_PROCESS_COMMAND="service mysqld start";
    //启动nginx命令
    public static final String START_NGINX_PROCESS_COMMAND="service nginx start";
    //启动redis命令
    public static final String START_REDIS_PROCESS_COMMAND="service redis start";

    public static final String RM_RF_COMMAND="rm -rf ";
    //启动mosquitto
    public static final String START_MOSQUITTO_PROCESS_COMMAND=" mosquitto -c /home/mosquitto-1.5.1/mosquitto.conf -d";



    //public static final String PS_EF_COMMAND="ps -eo pid,lstart,cmd | grep dhlk";

    //public static final String PS_EF_COMMAND="ps -ef | grep redis";

}
