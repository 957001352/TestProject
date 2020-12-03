package com.dhlk.light.util;

/**
 * Content: 自定义的系统枚举类,该类根据需要扩充
 * Author:jpdong
 * Date:2020/3/3
 */
public enum SystemEnums {
    UNKNOWN(-1, "未知错误"),
    SUCESS(0, "成功"),
    NOALLOW(1000, "非法操作"),
    EMPTY_ERROR(1001, "NULL Exception"),
    EXECUTE_ERROR(1002, "执行错误"),

    NUMBER_FORMAT_ERR(1003, "数字格式错误"),
    DATE_FORMAT_ERR(1004, "时间格式错误"),
    PARMS_ILLEGAL(1005,"参数非法"),

    /**
     * 文件上传相关
     */
    NO_EXITS_FILE_SAVEPATH(1006,"未知的文件保存路径"),
    FILE_UPLOAD_FAILURE(1007,"文件上传失败"),
    FILE_UPLOAD_SUCCESS(1008,"文件上传成功"),
    NO_ALLOW_FILETYPE(1009,"非法文件类型"),
    NO_ALLOW_FILESIZE(1009,"文件大小超限"),
    PORT_DEPLETE_ERR(1010,"端口耗尽异常"),
    IO_EXCEPTION(1011, "IO异常"),

    /**
    * 用户登陆相关
    * @author      gchen
    * @date        2020/3/20 15:27
    */

    TOKEN_LOSE_ERR(7001,"token失效"),
    NO_USER(7002,"用户不存在"),
    USERNAME_PASSWORD_ERR(7003,"用户名密码错误"),
    SELECT_VERIFICATION_CODE_ERROR(7004,"获取验证码异常"),
    URL_DECODER_ERROR(7005, "URL解码错误"),
    GET_CAPTCHA_ERROR(7006, "获取验证码失败"),


    DESENCRYPT_ERR(2002, "DES转化异常"),

    PLAT_ERROR(9999999,"平台性错误"),
    MONDAY(1,"星期一"),
    TUESDAY(2,"星期二"),
    WEDNESDAY(3,"星期三"),
    THURSDAY(4,"星期四"),
    FRIDAY(5,"星期五"),
    SATURDAY(6,"星期六"),
    SUNDAY(7,"星期日");


    private Integer state;
    private String stateInfo;

    SystemEnums(Integer state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public Integer getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static SystemEnums stateOf(int index) {
        for (SystemEnums state : values()) {
            if (state.state == index) {
                return state;
            }
        }
        return null;
    }
}
