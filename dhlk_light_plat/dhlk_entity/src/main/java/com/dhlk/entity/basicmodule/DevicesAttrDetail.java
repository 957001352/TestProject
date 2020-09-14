package com.dhlk.entity.basicmodule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description
 * @Author gchen
 * @Date 2020/3/26
 */
@Data
@ApiModel(value="devicesAttrSet",description="参数变量对象")
public class DevicesAttrDetail {
    @ApiModelProperty(value="新增为空/修改传值")
    private Integer id;
    @ApiModelProperty(value="变量编码",required=true)
    private String code;//编码
    @ApiModelProperty(value="变量名称",required=true)
    private String attr;//属性名称
    @ApiModelProperty(value="变量类型",required=true)
    private String dataType;//数据类型
    @ApiModelProperty(value="变量长度",required=true)
    private Integer dataLength;//数据长度
    @ApiModelProperty(value="变量单位",required=true)
    private String unit;//单位
    @ApiModelProperty(value="属性对象id 新增为空/修改传值")
    private Integer attrSetId;//属性集合
}