package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.basicmodule.User;
import com.dhlk.entity.basicmodule.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRoleDao {


    Integer saveUserRoles(List<UserRole> UserRoles);

    /**
    * 根据用户id删除多位用户角色关系
     * @param userId
    * @return
    */
    Integer deleteByUserIds(String[] userId);
    /**
     * 根据用户id删除某一位用户角色关系
     * @param userId
     * @return
     */
    Integer deleteByUserId(Integer userId);
}
