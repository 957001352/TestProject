package com.dhlk.entity.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="InfoBox",description="包装实体")
public class InfoBox<T> {
    @ApiModelProperty(value="sns")
    private String sns;

    @ApiModelProperty(value="对象")
    private T t;

    @ApiModelProperty(value="设备类型")
    private String dev_type;

    @ApiModelProperty(value="控制参数")
    private Integer controlParam;

    @ApiModelProperty(value="租户id")
    private Integer tenantId;
}
