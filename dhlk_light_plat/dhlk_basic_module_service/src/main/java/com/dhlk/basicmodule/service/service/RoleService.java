package com.dhlk.basicmodule.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Role;

public interface RoleService {
    /**
     * 新增/修改
     * 新增角色基本信息和角色菜单关系(权限维护)
     * 判断角色名称是否重复
     */
    Result save(Role role);

    /**
     * 物理删除
     * 判断如果角色id在有权限表有数据则不允许删除
     * @param ids
     */
     Result delete(String ids) ;
    /**
     * 根据角色id查询角色信息
     * @param id
     */
    Role selectRoleById(Integer id);
    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     */
    Result findPageList(Integer pageNum, Integer pageSize);

    /**
     * 根据角色名称查询角色
     * @param name
     */
    Result selectRoleByName(String name) ;

    /**
     * 查询所有
     */
    Result findAllList();
    /**
     * 查询用户下面的所有角色
     */
    Result selectRoleByUserId(Integer userId);
    /**
     * 查询用户可选的角色
     */
    Result selectableRoleByUserId(Integer userId);

    //查询角色下的所有用户
    Result selectUserByRoleId(Integer roleId,Integer pageNum, Integer pageSize);
}
