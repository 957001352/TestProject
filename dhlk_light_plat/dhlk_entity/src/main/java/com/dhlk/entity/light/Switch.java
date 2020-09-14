package com.dhlk.entity.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description:    开关
 * @Author:         gchen
 * @CreateDate:     2020/6/4 14:15
 */
@Data
@ApiModel(value="switch",description="开关")
public class Switch {
    /** $column.columnComment */
    @ApiModelProperty(value="新增为空/修改传值")
    private Integer id;

    /** 名称 */
    @ApiModelProperty(value="开关名称")
    private String name;

    /** sn码 */
    @ApiModelProperty(value="sn码")
    private String sn;

    /** ip地址 */
    @ApiModelProperty(value="ip地址")
    private String ip;

    /** 区域id */
    @ApiModelProperty(value="区域id")
    private String areaId;

    /** 一体机id */
    @ApiModelProperty(value="一体机id")
    private Integer computerId;

    /** 添加时间 */
    @ApiModelProperty(value="添加时间")
    private String createTime;

    /** 租户id */
    @ApiModelProperty(hidden = true)
    private Integer tenantId;

    /** 一体机 */
    @ApiModelProperty(value="一体机",hidden = true)
    private Computer computer;

    //开关组id
    private Integer groupId;

    //通道id
    private Integer groupNo;
    /** 灯 */
    @ApiModelProperty(value="灯")
    private List<Led> leds;

    /** 组 */
    @ApiModelProperty(value="组")
    private List<SwitchGroup> switchGroups;

    /**
     * flag
     */
    @ApiModelProperty(value="flag")
    private Boolean flag;

    /**
     * 在线灯数量
     */
    @ApiModelProperty(value="onLineCount")
    private int onLineCount;


    /**
     * 开的灯的数量
     */
    @ApiModelProperty(value="onCount")
    private int onCount;


    /**
     * 关的灯的数量
     */
    @ApiModelProperty(value="offCount")
    private int offCount;

    /**
     * 绑定的灯的sn组合
     */
    @ApiModelProperty(value="ledSns")
    private String ledSns;

}
