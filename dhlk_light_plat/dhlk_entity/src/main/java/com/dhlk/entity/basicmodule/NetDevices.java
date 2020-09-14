package com.dhlk.entity.basicmodule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 网络设备管理
 */
@Data
@ApiModel(value="netDevices",description="网络设备对象")
public class NetDevices implements Serializable {
    @ApiModelProperty(value="新增为空/修改传值")
    private Integer id;
    @ApiModelProperty(value="设备名称",required=true)
    private String name;//设备名称
    @ApiModelProperty(value="设备ip",required=true)
    private String ip;//设备ip
    @ApiModelProperty(value="默认网关",required=true)
    private String gateway;//默认网关
    @ApiModelProperty(value="子网掩码",required=true)
    private String mask;//子网掩码
    @ApiModelProperty(value="SN码",required=true)
    private String license;//SN码
    @ApiModelProperty(value="设备类型",required=true)
    private Integer typeId;//设备类型  1大数据一体机 2 BI控制器
    @ApiModelProperty(hidden = true)
    private String typeName;
    @ApiModelProperty(hidden = true)
    private Integer tenantId;//租户
    @ApiModelProperty(hidden = true)
    private Integer status;// 状态  0正常 1禁用
    @ApiModelProperty(value="设备描述",required=true)
    private String note;//设备描述
    @ApiModelProperty(hidden = true)
    private String createTime;//创建时间
    @ApiModelProperty(value="tb设备id 新增为空/修改传值")
    private String tbId;//tb设备表id
    @ApiModelProperty(value="tb设备凭证 新增为空/修改传值")
    private String credentials;//tb设备凭证
    private List<NetDevicesSoft> softList;//软件列表


    @ApiModelProperty(hidden = true)
    private List<ProductDevices> productDevicesList;//生产设备列表

    @ApiModelProperty(hidden = true)
    private List<NetDevices> netDevicesList;//网络设备列表
}
