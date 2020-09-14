package com.dhlk.basicmodule.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.User;

import java.util.Map;
import java.util.Set;

/**
 * 用户管理
 */
public interface UserService {
    /**
     * 新增/修改
     * 新增用户基本信息和用户角色关系
     * 判断loginName是否重复
     */
    Result save(User user);

    /**
     * 物理删除
     * 同时删除用户角色关系表
     * @param ids
     */
    Result delete(String ids);
    /**
     * 分页查询
     * 只查询普通用户
     * @param name 姓名
     */
    Result findList(String name);

    /**
     * 状态修改
     * @param id 主键
     * @param status 0启用 1禁用
     */
    Result isEnable(Integer id,Integer status);

    /**
     * 修改密码
     * @param id 主键
     * @param password
     */
    Result updatePassword(Integer id,String password);


    /**
     * 根据登录名获取用户
     * @param loginName
     */
    User getUserByLoginName(String loginName);


    /**
     * 通过用户名获取用户角色集合
     *
     * @param loginName 用户名
     * @return 角色集合
     */
    Set<String> getRolesByLoginName(String loginName);
    /**
     * 通过用户名获取用户权限集合
     * @param user 用户
     * @return 权限集合
     */
    Map<String,Set> getPermissionsByLoginName(User user);

    //查询用户所在机构
    Result findOrg(Integer id);

    /**
     * 检查用户所在租户是否过期
     * @param userId 用户ID
     */
    Integer checkExpired(Integer userId);

    /**
     * 检查用户所在租户是否被删除
     * @param userId 用户ID
     */
    Integer checkIsDelete(Integer userId);

    /**
     * 根据租户Id查询用户
     * @param tenantId
     * @return
     */
    Result findListByTenantId(String tenantId);
}
