package com.dhlk.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 返回给前端的服务信息实体类
 */
@Data
@ApiModel(value = "serverInfo", description = "服务信息")
public class ServerInfo {
    @ApiModelProperty(value = "服务名称")
    private String name;
    @ApiModelProperty(value = "服务开启时间")
    private String startTime;

    public ServerInfo() {
    }

    public ServerInfo(String name) {
        this.name = name;
    }

    public ServerInfo(String name, String startTime) {
        this.name = name;
        this.startTime = startTime;
    }
}
