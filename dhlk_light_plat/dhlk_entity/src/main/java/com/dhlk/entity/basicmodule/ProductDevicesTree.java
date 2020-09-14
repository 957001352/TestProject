package com.dhlk.entity.basicmodule;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


/**
* @Description:    机构模式查看设备树
* @Author:         lpsong
* @CreateDate:     2020/4/7 17:12
*/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDevicesTree<T> {

    private String id;

    private String title;//名称

    private String component;

    private List<ProductDevicesTree<T>> children;//子集

    private String parentId;//父级id

    private Boolean checked;

    private boolean hasParent = false;//是否有父级

    private boolean hasChildren = false;//是否有子集

    private Integer staffNum;//机构内的人数


    public void initChildren(){
        this.children = new ArrayList<>();
    }


    private String tbId;//tdid
    private String name;//设备名称
    @ApiModelProperty(hidden = true)
    private String classifyName;//类型管理
    private String classifyId;//类型管理
    @ApiModelProperty(hidden = true)
    private LinkedHashMap<String,String> classifySet;
    @ApiModelProperty(hidden = true)
    private List<NetDevices> netDevicesList;//网络设备列表
    private String code;
    private String tenantCode;
    private String note;

}
