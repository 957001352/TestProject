package com.dhlk.entity.basicmodule;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单管理
 */
@Data
public class Menu implements Serializable {
    private Integer id;

    /** 编码 */
    private String code;

    /** 名称 */
    private String name;

    /** 请求连接 */
    private String url;

    /* 状态  0正常 1禁用 2删除 */
    private Integer status;

    /** 父节点 */
    private Integer parentId;

    private List<Menu> childList = new ArrayList<Menu>();

    private Boolean checked;//角色是否有访问权限

    private String perms;

}
