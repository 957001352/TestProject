package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.basicmodule.Permissions;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PermissionsDao {

    Integer insert(List<Permissions> permissions);

    /**
     * 根据角色删除角色和权限关系
     * @param roleIds
     * @return
     */
    public Integer deleteByRoleIds(List<String> roleIds);

    List<Permissions> selectListByRoleIds(List<String> roleIds);

    public Integer deleteByRoleId(Integer roleId);

}
