package com.dhlk.entity.light;


import lombok.Data;

/**
 * 开关wifi配置
 */
@Data
public class SwitchWifiInfo {
    private String ssid; //32byte
    private String password; //16byte
    private String ip; ///此处要求是 ip 地址的字符串
}
