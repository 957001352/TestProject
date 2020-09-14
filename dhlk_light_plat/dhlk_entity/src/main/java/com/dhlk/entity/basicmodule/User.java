package com.dhlk.entity.basicmodule;

import com.dhlk.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户管理
 */
@Data
@ApiModel(value="user",description="用户对象")
public class User implements Serializable {

    @ApiModelProperty(value="机构id,多个时用逗号隔开，如 1,2",required = true)
    private String orgId;//机构id
    @ApiModelProperty(value="角色id,多个时用逗号隔开，如 1,2",required = true)
    private String roleIds;//角色id集合，逗号隔开
    @ApiModelProperty(hidden = true)
    private String group;//用户分组条件

    /** $column.columnComment */
    @ApiModelProperty(value="新增为空/修改传值")
    private Integer id;

    /** 姓名 */
    @NameCheck
    @ApiModelProperty(value="用户名称",required = true)
    private String name;

    /** 用户名 默认长度4~20 */
    @LoginNameCheck(maxLength = 20,minLength = 3)
    @ApiModelProperty(value="用户登录名称",required = true)
    private String loginName;

    /** 用户密码 默认长度6~15 */
    @ApiModelProperty(value="用户登录密码",required = true)
    private String password;

    /** 状态  0正常 1禁用 */
    @ApiModelProperty(hidden = true)
    private Integer status;

    /** 是否超级用户 */
    @ApiModelProperty(hidden = true)
    private Integer isAdmin;

    /** 创建时间 */
    @ApiModelProperty(hidden = true)
    private String createTime;

    /** 电话 */
    @MobizeCheck
    @ApiModelProperty(value = "电话号",required = true)
    private String phone;

    /** 邮箱 */
    @EmailCheck
    @ApiModelProperty(value = "邮箱号",required = true)
    private String email;

    /** 用户关联机构最顶级 */
    @ApiModelProperty(hidden = true)
    private Tenant factory;
    /** 租户id */
    @ApiModelProperty(hidden = true)
    private Integer tenantId;

    /**
     * tb登录账号
     */
    @ApiModelProperty(hidden = true)
    private String tbLoginname;

    /**
     * tb登录密码
     */
    @ApiModelProperty(hidden = true)
    private String tbPassword;

}


