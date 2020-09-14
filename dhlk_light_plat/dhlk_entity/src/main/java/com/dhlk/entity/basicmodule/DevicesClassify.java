package com.dhlk.entity.basicmodule;

import com.dhlk.entity.dm.DmClassifyType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Description
 * @Author gchen
 * @Date 2020/3/26
 */
@Data
@ApiModel(value="devicesClassify",description="类型对象")
public class DevicesClassify {
    @ApiModelProperty(value="新增为空/修改传值")
    private String id;
    @ApiModelProperty(value="分类名称",required=true)
    private String classifyName;//分类名称
    @ApiModelProperty(value="分类描述",required=true)
    private String describe;//描述
    @ApiModelProperty(value="类型ID",required=true)
    private String classifyId;//类型
    @ApiModelProperty(hidden = true)
    private Integer tenantId;//租户
    @ApiModelProperty(value="参数集合",required=true)
    private List<DevicesClassifyDetail> classifyDetails;//设备类型属性明细
    @ApiModelProperty(hidden = true)
    private List<LinkedHashMap<String,Object>> attrSet;
    @ApiModelProperty(hidden = true)
    private Integer nameCount;//参数数量
    @ApiModelProperty(value="参数类型",required=true)
    private DmClassifyType dmClassifyType;//基础类型type

    @ApiModelProperty(hidden = true)
    private String imagePath;//关联文件
}