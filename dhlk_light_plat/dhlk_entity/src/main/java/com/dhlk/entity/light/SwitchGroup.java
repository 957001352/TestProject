package com.dhlk.entity.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xkliu
 * @date 2020/7/9
 * <p>
 * 开关组实体类
 */
@Data
@ApiModel(value = "switchGroup", description = "开关组")
public class SwitchGroup implements Serializable {


    /**
     * id
     */
    @ApiModelProperty(value = "id", hidden = true)
    private Integer id;

    /**
     * 状态默认0
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 组Id
     */
    @ApiModelProperty(value = "组Id")
    private Integer groupId;

    /**
     * 开关Id
     */
    @ApiModelProperty(value = "开关Id", hidden = true)
    private Integer switchId;


    /** 灯 */
    @ApiModelProperty(value="灯")
    private List<Led> leds;
}
