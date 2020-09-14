package com.dhlk.entity.basicmodule;

import com.dhlk.annotation.SetNameCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 租户实体类
 */
@Data
@ApiModel(value = "tenant", description = "租户对象")
public class Tenant implements Serializable {
    /**
     * 租户ID
     */
    @ApiModelProperty(value = "新增为空/修改传值",hidden = true)
    private Integer id;

    /**
     * 租户编码
     */
    @ApiModelProperty(value = "租户编码")
    private String code;

    /**
     * 租户名称
     */
    @SetNameCheck(message = "请填写正确的租户名称，2-20位的汉字、数字和字母", maxLength = 20)
    @ApiModelProperty(value = "租户名称", required = true)
    private String name;

    /**
     * 企业联系电话
     */
    @ApiModelProperty(value = "企业联系电话", required = true)
    private String telephone;

    /**
     * 联系地址
     */
    @ApiModelProperty(value = "联系地址", required = true)
    private String adress;

    /**
     * 企业邮箱
     */
    @ApiModelProperty(value = "企业邮箱")
    @Pattern(regexp = "^([a-z0-9A-Z]+[-|_\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "企业邮箱格式不正确")
    private String companyEmail;

    /**
     * 联系人
     */
    @SetNameCheck(message = "请填写正确的联系人姓名，2-20位的字符", maxLength = 20)
    @ApiModelProperty(value = "租户名称", required = true)
    private String linkman;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话", required = true)
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @Pattern(regexp = "^([a-z0-9A-Z]+[-|_\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "邮箱格式不正确")
    private String email;

    /**
     * 有效期起
     */
    @ApiModelProperty(value = "有效期起", required = true)
    private String startTime;

    /**
     * 有效期止
     */
    @ApiModelProperty(value = "有效期止", required = true)
    private String endTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private String createTime;

    /**
     * 最大设备数
     */
    @ApiModelProperty(value = "最大设备数")
    private Integer devices;

    /**
     * 状态 0正常 2 删除
     */
    @ApiModelProperty(hidden = true)
    private Integer status;

    /**
     * 是否系统默认租户  0是 1否
     */
    @ApiModelProperty(hidden = true)
    private Integer isSystem;

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

    /**
     * 机构ID
     */
    @ApiModelProperty(hidden = true)
    private Integer orgId;

    /**
     * 角色ID
     */
    @ApiModelProperty(hidden = true)
    private Integer roleId;

    /**
     * 附件Id
     */
    private String fileId;

    /**
     * 附件路径
     */
    @ApiModelProperty(hidden = true)
    private String imagePath;

    /**
     * 详细地址，用来和联系地址拼接字符串
     */
    private String detailAddress;

    private String expire;
    /**
     * td租户id
     */
    @ApiModelProperty(hidden = true)
    private String tbId;

    /**
     * 客户code
     */
    @ApiModelProperty(hidden = true)
    private String customer;

}
