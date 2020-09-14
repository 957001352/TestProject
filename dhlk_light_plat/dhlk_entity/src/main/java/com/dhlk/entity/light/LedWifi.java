package com.dhlk.entity.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xkliu
 * @date 2020/7/10
 */
@Data
@ApiModel(value="ledWifi",description="灯wifi配置")
public class LedWifi {

    @ApiModelProperty(value="sns")
    private String sns;

    @ApiModelProperty(value="wifiInfo对象")
    private LedWifiInfo ledWifiInfo;
}
