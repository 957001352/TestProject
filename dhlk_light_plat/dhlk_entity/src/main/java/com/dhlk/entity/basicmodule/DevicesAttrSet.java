package com.dhlk.entity.basicmodule;

import com.dhlk.annotation.SetNameCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/26
 */
@Data
@ApiModel(value="devicesAttrSet",description="参数对象")
public class DevicesAttrSet {
    @ApiModelProperty(value="新增为空/修改传值")
    private  Integer id;
    @SetNameCheck(message = "请填写正确的参数名称")
    @ApiModelProperty(value="名称",required=true)
    private  String name;//名称
    @ApiModelProperty(value="描述",required=true)
    private  String describe;//描述
    @ApiModelProperty(hidden = true)
    private Integer tenantId;//租户
    @ApiModelProperty(value="属性Id",required=true)
    private  String attrSetId;//别名
    @ApiModelProperty(value="变量集合",required=true)
    private  List<DevicesAttrDetail> attrDetails;//属性明细
}