package com.dhlk.entity.basicmodule;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户管理
 */
@Data
public class UserRole implements Serializable {
    private Integer userId;//用户id
    private Integer roleId;//角色id
}
