package com.dhlk.web.basicmodule.controller;

import com.dhlk.web.basicmodule.service.PermissionsService;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public Result delete(@RequestParam(value = "roleIds") String roleIds) {
        return permissionsService.deleteByRoleIds(roleIds);
    }
}
