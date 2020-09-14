package com.dhlk.entity.basicmodule;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description 角色菜关系管理（权限管理）
 * @Author lpsong
 * @Date 2020/3/11
 */
@Data
@NoArgsConstructor
public class Permissions implements Serializable {
    private Integer id;

    /** 菜单id */
    private Integer menuId;

    /** 角色id */
    private Integer roleId;

    public Permissions( Integer roleId,Integer menuId) {
        this.menuId = menuId;
        this.roleId = roleId;
    }
}