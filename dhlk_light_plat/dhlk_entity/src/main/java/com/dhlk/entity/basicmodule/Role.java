package com.dhlk.entity.basicmodule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色管理
 */

@Data
@ApiModel(value="role",description="角色对象")
public class Role implements Serializable {

    @ApiModelProperty(hidden = true)
    private String menuIds;//菜单id集合，逗号隔开

    /** null */
    @ApiModelProperty(value="新增为空/修改传值")
    private Integer id;

    /** 名称 */
    @ApiModelProperty(value="角色名称",required=true,example="系统管理员")
    private String name;

    /** 备注 */
    @ApiModelProperty(value="角色描述",required=true)
    private String note;

    /** 租户id */
    @ApiModelProperty(hidden = true)
    private Integer tenantId;

    /** 是否默认系统角色 是否系统默认角色  0是 1否 */
    @ApiModelProperty(hidden = true)
    private Integer isSystem;
}
