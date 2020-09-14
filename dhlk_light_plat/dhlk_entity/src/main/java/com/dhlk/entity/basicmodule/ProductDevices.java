package com.dhlk.entity.basicmodule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 生产设备管理
 */
@Data
@ApiModel(value="productDevices",description="工业设备对象")
public class ProductDevices implements Serializable {
    @ApiModelProperty(value="新增为空/修改传值")
    private Integer id;
    @ApiModelProperty(hidden = true)
    private String code;//设备编码
    @ApiModelProperty(value="设备名称",required=true)
    private String name;//设备名称
    @ApiModelProperty(value="机构Id",required=true)
    private Integer orgId;//车间id
    @ApiModelProperty(hidden = true)
    private String orgName;//车间名称
    @ApiModelProperty(hidden = true)
    private Integer tenantId;//租户
    @ApiModelProperty(hidden = true)
    private String  tenantCode;
    @ApiModelProperty(value="类型Id",required=true)
    private String classifyId;//类型管理
    @ApiModelProperty(hidden = true)
    private String classifyName;//类型管理
    @ApiModelProperty(hidden = true)
    private Integer status;// 状态  0正常 1禁用 2 删除
    @ApiModelProperty(hidden = true)
    private String createTime;//创建时间
    private String note;//设备描述
    @ApiModelProperty(value="tb设备id 新增为空/修改传值")
    private String tbId;//tb设备表id
    @ApiModelProperty(value="tb设备凭证 新增为空/修改传值")
    private String credentials;//tb设备凭证
    @ApiModelProperty(hidden = true)
    private List<LinkedHashMap<String,Object>> attrSet;
    @ApiModelProperty(hidden = true)
    private LinkedHashMap<String,String> classifySet;
    @ApiModelProperty(hidden = true)
    private List<NetDevices> netDevicesList;//网络设备列表
}
