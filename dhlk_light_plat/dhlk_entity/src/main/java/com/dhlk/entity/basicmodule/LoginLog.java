package com.dhlk.entity.basicmodule;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户登录日志管理
 */
@Data
public class LoginLog implements Serializable {

    /** $column.columnComment */
    private Integer id;

    /** 登录用户 */
    private Integer userId;

    /** 登录地址 */
    private String ip;

    /** 登录时间 */
    private Date loginTime;

    /** 租户id */
    private Integer tenantId;


}
