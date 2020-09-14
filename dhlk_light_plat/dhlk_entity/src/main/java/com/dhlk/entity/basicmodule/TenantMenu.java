package com.dhlk.entity.basicmodule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 租户拥有菜单的实体类
 */
@Data
public class TenantMenu implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     *菜单ID
     */
    private Integer menuId;

    /**
     * 租户ID
     */
    private Integer TenantId;

    public TenantMenu() {
    }

    public TenantMenu(Integer menuId, Integer tenantId) {
        this.menuId = menuId;
        TenantId = tenantId;
    }
}
