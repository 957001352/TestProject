package com.dhlk.basicmodule.service.service;

import com.dhlk.domain.Result;

public interface PermissionsService {
    /**
     * 根据角色拥有的菜单
     * @param roleId  角色id
     * @param menuIds  菜单ids
     * @return
     */
    Result insert(Integer roleId,String menuIds);
    /**
     * 根据角色id 删除角色和权限关系
     * @param roleIds
     * @return
     */
    Result deleteByRoleIds(String roleIds);


    /**
     * 查询所有权限
     * @param roleIds
     * @return
     */
    public boolean selectListByRoleIds(String roleIds) ;
}
