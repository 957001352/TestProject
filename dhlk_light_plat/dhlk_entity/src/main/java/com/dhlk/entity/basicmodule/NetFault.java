package com.dhlk.entity.basicmodule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 网络设备故障信息
 * @Author lpsong
 * @Date 2020/4/20
 */
@Data
public class NetFault {
    @ApiModelProperty(hidden = true)
    private Integer id;
    private String tbId;//tb网络设备Id
    private String type;//故障类型
    private String content;//故障信息
    @ApiModelProperty(hidden = true)
    private Integer status;//处理状态  0 未处理 1 已处理
    @ApiModelProperty(hidden = true)
    private String createTime;//故障产生时间
    @ApiModelProperty(hidden = true)
    private Integer dealUser;//处理人
    @ApiModelProperty(hidden = true)
    private String dealTime;//故障处理时间
}