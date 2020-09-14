package com.dhlk.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 返回给前端的错误信息实体类
 */
@Data
@ApiModel(value = "alarmInfo", description = "错误信息")
public class AlarmInfo {
    @ApiModelProperty(value = "错误信息类型")
    private String type;
    @ApiModelProperty(value = "错误信息")
    private String Info;
    @ApiModelProperty(value = "时间戳")
    private long ts;

    public AlarmInfo() {
    }

    public AlarmInfo(String type, String info, long ts) {
        this.type = type;
        Info = info;
        this.ts = ts;
    }
}
