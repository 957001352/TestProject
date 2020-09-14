package com.dhlk.light.factory.enums;


/**
 * @Description 灯管理接口
 * @Author lpsong
 * @Date 2020/6/4
 */
public enum LedEnum {
    //0-999 正常
    ONOFF(135, "开关灯"),

    FLASHING(147, "闪一闪"),
    LIGHTNESS(134, "亮度设置"),
    RESTART(133, "灯重启"),
    POWER(0, "灯能耗"),
    PERSONFELL(1, "人感"),
    LIGHTFELL(2, "光感"),
    GRPID(136, "组设置"),
    BACKRESULT(6, "统一返回结果"), //统一返回结果
    LIGHTFELLRETURN(9, "获取光感返回数据"),
    SETLIGHTFELL(146, "设置光感"),

    PEOPLEFELLRETURN(4, "获取人感返回数据"),
    SETPEOPLEFELL(132, "设置人感"),
    GETPEOPLEFELL(131, "获取人感"),
    WIFIRETURN(3, "获取wifi返回数据"),
    SETWIFI(130, "设置wifi"),
    DEVLIGHT(0, "灯"), //设备类型 灯
    DEVSWITCH(1, "开关"), //设备类型 开关

    VERSIONRETURN(8,"获取版本回复"),
    GETVERSION(141, "获取版本"),
    SETVERSION(143,"设置版本"),
    SETVERSIONRETURN(133,"设置版本返回"),


    WIFICONFIG(129, "获取wifi配置");
    private Integer state;
    private String stateInfo;

    LedEnum(Integer state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public Integer getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

}
