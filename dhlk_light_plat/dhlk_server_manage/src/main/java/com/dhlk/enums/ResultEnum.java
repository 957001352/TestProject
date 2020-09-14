package com.dhlk.enums;

/**
 * 版本        修改时间        作者      修改内容
 * V1.0        ------        jpdong     原始版本
 * 文件说明: 自定义枚举，可以根据range进行自定义
 **/
public enum  ResultEnum {
    //0-999 正常
    NOERROR(0, "成功！"),
    UNKNOWN_ERR(-1, "非法操作，请检查"),
    FAILURE(-2,"数据更新失败"),
    //1000-1999
    UNNORMAL_INPUT_ERROR(1000,"未知的输入错误"),
    SYSTEM_ERR(1001, "系统错误"),
    DATABASE_ERR(1002, "数据库异常"),
    TOKEN_ISNULL(1003,"token错误"),
    NULLDATA(1004,"数据不存在"),
    NETWORK_ERR(1005,"数据加载延迟，请刷新页面"),
    KAPTCHA_ERR(1006,"验证码错误"),

    //2000-2999
    NUMBERIC_FORMAT_ERR(2000, "数字格式错误"),
    DATE_FORMAT_ERR(2000, "日期格式错误"),
    TIMESTAMP_FORMAT_ERR(2001, "时间戳格式错误"),
    DESENCRYPT_ERR(2002, "DES转化异常"),
    TOKEN_MODULENAME_ERR(2003, "Token模块名称缺失"),
    TOKEN_FUNENAME_ERR(2004, "Token方法名称缺失"),
    TOKEN_UIDNULL_ERR(2005, "UID缺失"),

    //3000-3999
    PARAM_ISNULL(3000, "传入对象为空"),
    USERNAME_ISNULL(3001, "用户名输入非法"),
    PASSWORD_ISNULL(3002, "用户密码输入非法"),
    USERNAME_LENERROR(3003, "用户名长度最大为10"),
    PASSWORD_LENERROR(3004, "密码长度为6-8位"),
    HAVE_SAMENAME(3005, "存在相同账号"),
    ROLENAME_ERROR(3006, "角色名不合法"),
    ROLECNAME_ERROR(3007, "中文角色名不合法"),
    NOUSER_ERROR(3008, "系统不存在该用户"),
    USERPASSWORD_ERROR(3009, "用户密码不正确"),
    UPDATEPASSWORD_ERROR(3010, "旧密码输入不正确"),
    ROLEHAVEDEL_ERROR(3011, "角色ID对应的角色已经删除"),
    STATIONHAVEDEL_ERROR(3012, "岗位ID对应的岗位已经删除"),
    DEPARTHAVEDEL_ERROR(3013, "部门ID对应的部门已经删除"),
    ROLENAME_SAMENAME(3014, "存在相同角色名"),
    ROLECNAME_SAMENAME(3015, "存在相同角色中文名"),
    USERNAME_PASSWORDEROR(3016,"用户名或密码不正确"),
    TENANT_NAME(3017,"该租户名称已存在"),

    //5000-6000
    PRIMARY_KEY_ERR(5000,"主键错误"),
    PRIMARY_KEY_ISNULL_ERR(5001,"主键为空"),
    MISSING_DATA_ERR(5002,"数据不完整"),
    IP_ERR(5003,"错误的IP格式"),
    DEVICESNAME_REPEAT(5004,"设备名字重复"),
    DEVICESIP_REPEAT(5005,"设备IP重复"),
    FIELDDATA_REPEAT(5006,"表名或字段名重复"),
    TABLENAME_REPEAT(5007,"表名或字段名非法命名"),
    NODEFAILE_REPEAT(5008,"该父节点不能添加子节点，添加子节点会导致表名重复，请先修改父节点英文名"),
    NOChinese(5007,"不能有汉字"),
    NAME_ERR(5008,"非法的姓名格式"),
    TABLE_ERR(5009,"创建表已经存在"),//需要前端处理的code,慎用
    SPARK_ERR(5010,"SPARK数据处理失败"),
    //6000-6999
    NULL_DATA(6000,"数据不存在"),
    UNCOMPLETE_DATA(6001,"数据不完整"),
    CANT_DELETE(6003,"无法删除"),
    UNABLE_TO_CALL(6004,"无法调用"),
    PARAM_ERR(6002,"参数错误"),
    TABLE_NO_EXISTS(6005,"表不存在"),


    /**
    * 用户相关
    * @author      gchen
    * @param 7000-7999
    * @date        2020/3/20 14:47
    */
    BAN_USR(1000,"用户被禁用"),
    NO_LOGIN(7001,"登录超时,请重新登录"),
    NO_PERMISS(7002,"无访问权限"),
    URL_ERROR(7003,"请求异常");






    private int state;
    private String stateInfo;

    ResultEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ResultEnum stateOf(int index) {
        for (ResultEnum state : values()) {
            if (state.state==index) {
                return state;
            }
        }
        return null;
    }
}
