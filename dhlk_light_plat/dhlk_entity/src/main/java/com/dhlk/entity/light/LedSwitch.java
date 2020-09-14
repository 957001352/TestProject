package com.dhlk.entity.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:    开关-灯
 * @Author:         gchen
 * @CreateDate:     2020/6/4 14:15
 */
@Data
@ApiModel(value="lightSwitch",description="开关-灯")
public class LedSwitch {
    /** $column.columnComment */
    @ApiModelProperty(value="新增")
    private Integer id;

    /** 开关id */
    @ApiModelProperty(value="开关id")
    private Integer switchId;

    /** 灯id */
    @ApiModelProperty(value="灯id")
    private Integer ledId;

    @ApiModelProperty(value="开关组id")
    private Integer groupId;

    public LedSwitch() {
    }

    public LedSwitch(Integer switchId, Integer ledId, Integer groupId) {
        this.switchId = switchId;
        this.ledId = ledId;
        this.groupId = groupId;
    }
}
