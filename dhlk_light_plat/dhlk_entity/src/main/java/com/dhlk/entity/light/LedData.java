package com.dhlk.entity.light;/**
 * @创建人 wangq
 * @创建时间 2020/6/11
 * @描述
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: dhlk.multi.tenant
 *
 * @description: 灯实时能耗信息实体
 *
 * @author: wqiang
 *
 * @create: 2020-06-11 15:34
 **/
@Data
@ApiModel(value="ledData",description="灯")
public class LedData {

    @ApiModelProperty(value = "灯id")
    private String id;
    @ApiModelProperty(value = "电压")
    private String voltage;
    @ApiModelProperty(value = "电流")
    private String electricity;
    @ApiModelProperty(value = "累计电能")
    private String cumulativePower;
    @ApiModelProperty(value = "继电器状态")
    private String relayState;
    @ApiModelProperty(value = "灯亮度")
    private String lightIntensity;
}
