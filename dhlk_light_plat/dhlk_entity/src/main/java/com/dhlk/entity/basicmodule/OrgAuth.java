package com.dhlk.entity.basicmodule;

import lombok.Data;

import java.io.Serializable;

/**
 * 厂区访问秘钥
 */
@Data
public class OrgAuth implements Serializable {
    private Integer id;
    private String auth;//秘钥
    private Integer tenantId;//租户
}
