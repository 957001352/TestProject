package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.PermissionsService;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* 权限管理
*/
@RestController
@RequestMapping(value = "/permissions")
@Api(description = "权限管理")
public class PermissionsController {
    @Autowired
    private PermissionsService permissionsService;

    /**
     * 保存
     * @param roleId
     * @param menuIds
     * @return
     */
    @ApiOperation("新增/修改")
    @PostMapping(value = "/save")
    @RequiresPermissions("permissions:save")
    public Result save(@RequestParam(value = "roleId")Integer roleId,@RequestParam(value = "menuIds",required=false)String menuIds) {
        return permissionsService.insert(roleId,menuIds);
    }
    /**
     * 删除
     * @param roleIds
     * @return result
     */
    @ApiOperation("权限删除")
    @GetMapping(value = "/deleteByRoleIds")
    @RequiresPermissions("permissions:delete")
    public Result deleteByRoleIds(@RequestParam(value = "roleIds") String roleIds) {
        return permissionsService.deleteByRoleIds(roleIds);
    }
}
