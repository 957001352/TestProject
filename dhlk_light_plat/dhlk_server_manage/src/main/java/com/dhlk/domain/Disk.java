package com.dhlk.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 磁盘信息实体类
 */
@Data
@ApiModel(value = "disk", description = "磁盘信息")
public class Disk {
    @ApiModelProperty(value = "磁盘占用率")
    private String usePercent;
    @ApiModelProperty(value = "磁盘IO 读速率")
    private float disk_reads;
    @ApiModelProperty(value = "磁盘IO 写速率")
    private float disk_writes;

    private String devName; //盘符名称

    private String useage;
    private String diskTotal; //磁盘总大小
    private String diskFree; //剩余大小
    private String diskAvail; //可用大小
    private String diskAvailRate; //资源利用率

    public Disk() {
    }

    public Disk(String devName, String diskTotal, String diskFree, String diskAvail, String diskAvailRate) {
        this.devName = devName;
        this.diskTotal = diskTotal;
        this.diskFree = diskFree;
        this.diskAvail = diskAvail;
        this.diskAvailRate = diskAvailRate;
    }
}















