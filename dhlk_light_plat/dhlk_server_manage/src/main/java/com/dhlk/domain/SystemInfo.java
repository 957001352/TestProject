package com.dhlk.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统信息
 */
@Data
@ApiModel(value = "serverInfo", description = "系统信息")
public class SystemInfo {
    @ApiModelProperty(value = "类型")
    private String type;
    @ApiModelProperty(value = "版本")
    private String sysVersion;
    @ApiModelProperty(value = "天数")
    private String upDay;
    @ApiModelProperty(value = "小时")
    private String upHour;
    @ApiModelProperty(value = "分钟")
    private String upMin;

    public SystemInfo() {
    }

    public SystemInfo(String type, String sysVersion, String upDay, String upHour,String upMin) {
        this.type = type;
        this.sysVersion = sysVersion;
        this.upDay = upDay;
        this.upHour = upHour;
        this.upMin = upMin;
    }
}
