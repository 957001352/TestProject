package com.dhlk.light.entity;

import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/25
 * Time:14:09
 * @Description:
 */
@Data
public class LightTenant {
    /**
     * 租户ID
     */
    private Integer id;

    /**
     * 租户编码
     */
    private String code;

    /**
     * 租户名称
     */
    private String name;

    /**
     * 企业联系电话
     */
    private String telephone;

    /**
     * 联系地址
     */
    private String adress;

    /**
     * 企业邮箱
     */
    @Pattern(regexp = "^([a-z0-9A-Z]+[-|_\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "企业邮箱格式不正确")
    private String companyEmail;

    /**
     * 联系人
     */
    private String linkman;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    @Pattern(regexp = "^([a-z0-9A-Z]+[-|_\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "邮箱格式不正确")
    private String email;

    /**
     * 有效期起
     */
    private String startTime;

    /**
     * 有效期止
     */
    private String endTime;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 最大设备数
     */
    private Integer devices;

    /**
     * 状态 0正常 2 删除
     */
    private Integer status;

    /**
     * 是否系统默认租户  0是 1否
     */
    private Integer isSystem;

    /**
     * tb登录账号
     */
    private String tbLoginname;

    /**
     * tb登录密码
     */
    private String tbPassword;

    /**
     * 机构ID
     */
    private Integer orgId;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 附件Id
     */
    private String fileId;

    /**
     * 附件路径
     */
    private String imagePath;

    /**
     * 详细地址，用来和联系地址拼接字符串
     */
    private String detailAddress;

    private String expire;
    /**
     * td租户id
     */
    private String tbId;

    /**
     * 客户code
     */
    private String customer;
}
