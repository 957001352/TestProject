package com.dhlk.entity.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * API接口管理
 */
@Data
public class ApiList implements Serializable {
    private Integer id;
    @NotEmpty(message = "接口名称不能为空")
    private String title;//接口标题
    private String version;//版本
    private String content;//接口说明
    @ApiModelProperty(hidden = true)
    private Integer status;//状态  0正常 1禁用
    private Integer classifyId;//接口分类
    @ApiModelProperty(hidden = true)
    private String createTime;



}
