package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.basicmodule.Role;
import com.dhlk.entity.basicmodule.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoleDao {

    Integer insert(Role role);

    Integer update(Role role);

    Integer deleteRoleByIds(List<String> ids);

    Role selectRoleById(Integer id);

    List<Role> findList(@Param("name") String name,@Param("note") String note,@Param("tenantId") Integer tenantId,@Param("userId") Integer userId);

    Role selectRoleByName(@Param("name") String name,@Param("tenantId") Integer tenantId);

    Integer isRepeatName(Role role);

    List<Role> selectRoleByUserId(Integer userId);

    List<Role> selectableRoleByUserId(Integer userId);

    List<User> selectUserByRoleId(Integer roleId);

    List<Role> findListByTenantId(Integer id);

    Integer deleteByTenantId(Integer id);
}
