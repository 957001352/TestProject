package com.dhlk.web.basicmodule.service;

import com.dhlk.entity.basicmodule.Permissions;
import com.dhlk.web.basicmodule.service.fbk.PermissionsServiceFbk;
import com.dhlk.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限管理
 */
@FeignClient(value = "basicmodule-service/permissions", fallback = PermissionsServiceFbk.class)
public interface PermissionsService {
    /**
     * 根据角色拥有的菜单
     * @param roleId  角色id
     * @param menuIds  菜单ids
     * @return
     */
    @PostMapping(value = "/save")
    Result insert(@RequestParam(value = "roleId")Integer roleId,@RequestParam(value = "menuIds")String menuIds);

    /**
     * 根据角色删除角色和菜单关系
     * @param roleIds
     * @return
     */
    @RequestMapping(value = "/deleteByRoleIds", method = RequestMethod.GET)
    Result deleteByRoleIds(@RequestParam("roleIds")String roleIds);


    /**
     * 查询所有权限
     * @param roleId  可为空
     * @param menuId  可为空
     * @return
     */
    @RequestMapping(value = "/selectList", method = RequestMethod.GET)
    List<Permissions> selectList(@RequestParam("roleId")Integer roleId, @RequestParam("menuId")Integer menuId) throws Exception;
}
