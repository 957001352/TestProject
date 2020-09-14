package com.dhlk.entity.light;

import lombok.Data;

/**
 * 灯wifi配置
 */
@Data
public class LedWifiInfo {
    private String ssid; //32byte
    private String password; //16byte
    private String ip; ///此处要求是 ip 地址的字符串
    private Integer wf_dev; //1byte Wifi 模块选择:1byte(0:WG219 模块, 1:E103-W01 模块, 2:汉枫 HF_LPD130 模块)
    private Integer wf_mode; //1byte (1:2.4G, 2:5G, 3:2.4G 和5G)
}
