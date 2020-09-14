package com.dhlk.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "systemStatus", description = "系统状态")
public class SystemStatus {
    @ApiModelProperty(value = "CPU占用率")
    private Integer cpu_combined;//CPU占用率
    @ApiModelProperty(value = "内存使用率")
    private Float memory_combined;//内存使用率
    @ApiModelProperty(value = "磁盘信息")
    private Disk disk;//磁盘信息
    @ApiModelProperty(value = "上传/下载速率")
    private double[] net_speed;
    @ApiModelProperty(value = "系统信息")
    private SystemInfo systemInfo;
    @ApiModelProperty(value = "报警信息")
    private List<AlarmInfo> alarmInfos;
    @ApiModelProperty(value = "运行服务信息")
    private List<ServerInfo> serverStartInfos;
    @ApiModelProperty(value = "未运行服务信息")
    private List<ServerInfo> serverEndInfos;

}
