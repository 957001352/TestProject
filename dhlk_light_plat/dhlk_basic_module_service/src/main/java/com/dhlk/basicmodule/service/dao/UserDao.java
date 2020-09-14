package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.basicmodule.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;


@Repository
public interface UserDao {

    Integer insert(User user);

    Integer update(User user);

    Integer delete(String[] id);

    List<User> findList(@Param("name") String name,@Param("tenantId") Integer tenantId);

    User loginCheck(@Param("loginName") String loginName,@Param("password") String passWord);

    Integer isEnable(@Param("id") Integer id, @Param("status") Integer status);

    /*
    * 判断登录账号是否重复
     * @param loginName
    * @return
    */
    Integer isRepeatLoginName(User user);

    /*
     * 根据登陆账号查找用户
     * @param loginName
     * @return
     */
    User findUserByLoginName(String loginName);

    /*
     * 根据用户名查角色
     * @param loginName
     * @return
     */
    Set<String> findRolesByLoginName(String loginName);

    /*
     * 根据用户名查权限
     * @param loginName
     * @return
     */
    List<Menu> findMenusByLoginName(User user);

    /*
     * 根据机构id查询机构下的用户
     * @param orgId
     * @return
     */
    List<User> findUserByOrgId(Integer orgId);


    /*
     * 检查租户是否过期
     * @param orgId
     * @return
     */
    Integer checkExpired(Integer userId);
    /**
     * 根据 tenantId 查询所有的租户管理员
     * @param tenantId
     * @return
     */
    List<User> findTenantAdminList(Integer tenantId);
    /*
     * 检查租户是否被删除
     * @param orgId
     * @return
     */
    Integer checkIsDelete(Integer userId);

    /**
     * 根据租户Id查询用户
     * @param tenantId
     * @return
     */
    List<User> findListByTenantId(@Param("tenantId") String tenantId);


    Integer findUserCountByOrgId(Integer orgId);

    List<LinkedHashMap<Integer,Integer>> findOrgList(Integer tenantId);

    Integer updataStatusByTenantId(Integer tenantId);
}
