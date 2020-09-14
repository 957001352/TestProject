package com.dhlk.light.subscribe.enums;


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
    RESTART(133, "重启"),
    UPGRADE(0305, "灯升级"),
    GRPID(136, "组设置"),
    POWER(0, "灯能耗"),
    PERSONFELL(1, "人感"),
    LIGHTFELL(2, "光感"),

    DEVLIGHT(0, "灯"), //设备类型 灯
    DEVSWITCH(1, "开关"), //设备类型 开关
    BACKRESULT(6, "统一返回结果"), //统一返回结果
    LIGHTFELLRETURN(9, "获取设置光感返回数据"),
    GETLIGHTFELL(145, "获取光感数据"),
    SETLIGHTFELL(146, "设置光感"),

    PEOPLEFELLRETURN(4, "获取设置人感返回数据"),
    GETPEOPLEFELL(131, "获取人感数据"),
    SETPEOPLEFELL(132, "设置人感"),

    WIFIRETURN(3, "获取设置wifi返回数据"),
    GETWIFI(129, "获取wifi配置"),
    SETWIFI(130, "设置wifi"),

    VERSIONRETURN(8,"获取版本回复"),
    GETVERSION(141, "获取版本"),

    FIRMWAREUPDATERETURN(2298, "固件升级返回数据"),
    FIRMWAREUPDATE(2297, "固件升级"),

    SWITCHSET(142, "开关配置");

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
