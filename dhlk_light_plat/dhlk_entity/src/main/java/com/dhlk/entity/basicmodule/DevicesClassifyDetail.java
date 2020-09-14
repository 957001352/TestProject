package com.dhlk.entity.basicmodule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/26
 */
@Data
@ApiModel(value="devicesClassifyDetail",description="类型与参数绑定关系表")
public class DevicesClassifyDetail {
    @ApiModelProperty(value="新增为空/修改传值")
    private Integer id;
    @ApiModelProperty(value="类型对象id",required=true)
    private Integer attrSetId;//属性集合
    @ApiModelProperty(hidden = true)
    private String attrSubName;//属性简称
    @ApiModelProperty(value="参数对象id",required=true)
    private Integer attrDetailId;//属性明细
    @ApiModelProperty(value="设备分类对象id",required=true)
    private String devicesClassifyId;//设备分类
}