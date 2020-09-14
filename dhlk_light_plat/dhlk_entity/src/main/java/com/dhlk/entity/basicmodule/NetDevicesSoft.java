package com.dhlk.entity.basicmodule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 软件网络设备关系管理
 */
@Data
@ApiModel(value="netDevicesSoft",description="网络设备软件对象")
public class NetDevicesSoft implements Serializable {
    @ApiModelProperty(value="新增为空/修改传值")
    private Integer id;
    @ApiModelProperty(value="软件名称",required=true)
    private String name;//软件名称
    @ApiModelProperty(value="版本",required=true)
    private String version;//版本
    @ApiModelProperty(value="云端地址",required=true)
    private String url;//云端地址
    @ApiModelProperty(value="端口",required=true)
    private String port;//端口
    @ApiModelProperty(value="网络设备对象id 新增为空/修改传值")
    private Integer netDeviceId;//网络设备

}
