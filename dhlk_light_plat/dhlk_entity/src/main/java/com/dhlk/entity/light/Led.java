package com.dhlk.entity.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 灯
 * @Author lpsong
 * @Date 2020/6/4
 */
@Data
@ApiModel(value="led",description="灯")
public class Led {
    @ApiModelProperty(value = "新增为空/修改传值")
    private Integer id;

    @ApiModelProperty(value = "sn码")
    private String sn;

    @ApiModelProperty(value = "ip地址")
    private String ip;

    @ApiModelProperty(value = "x坐标")
    private String xaxis;

    @ApiModelProperty(value = "y坐标")
    private String yaxis;

    @ApiModelProperty(value = "亮度")
    private Integer brightness;

    @ApiModelProperty(value = "人感感应亮度")
    private Integer indBright;

    @ApiModelProperty(value = "人感未感应亮度")
    private Integer unindBright;

    @ApiModelProperty(value = "人感感应时间")
    private Integer indTime;

    @ApiModelProperty(value = "人感感应开启状态0-未开启1-开启")
    private Integer indStatus;

    @ApiModelProperty(value = "添加时间",hidden = true)
    private String createTime;

    @ApiModelProperty(value = "租户",hidden = true)
    private Integer tenantId;

    @ApiModelProperty(value = "区域")
    private String areaId;

    @ApiModelProperty(value = "别名")
    private String alias;

    @ApiModelProperty(value = "灯状态 0未删除 1已删除",hidden = true)
    private Integer status;
}