package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.RoleService;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Role;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* 角色管理
*/
@RestController
@RequestMapping(value = "/role")
@Api(description = "角色管理")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 保存
     * @param role
     * @return
     */
    @ApiOperation("新增/修改")
    @PostMapping(value = "/save")
    @RequiresPermissions("role:save")
    public Result save(@RequestBody Role role) {
        return roleService.save(role);
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @ApiOperation("删除")
    @GetMapping(value = "/delete")
    @RequiresPermissions("role:delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return roleService.delete(ids);
    }

    /**
    * 列表查询
     * @param pageNum
     * @param pageSize
    * @return
    */
    @ApiOperation("分页查询")
    @GetMapping("/findPageList")
    @RequiresPermissions("dhlk:view")
    public Result findPageList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return  roleService.findPageList(pageNum,pageSize);
    }
    /**
     * 列表查询
     * @return
     */
    @ApiOperation("查询所有")
    @GetMapping("/findAllList")
    @RequiresPermissions("dhlk:view")
    public Result findAllList(){
        return  roleService.findAllList();
    }

    /**
     * 查询用户下面的所有角色
     *  @param userId 用户id
     * @return
     */
    @ApiOperation("查询用户下面的所有角色")
    @GetMapping("/selectRoleByUserId")
    @RequiresPermissions("dhlk:view")
    public Result selectRoleByUserId(@RequestParam(value = "userId", required = true)Integer userId){
        return  roleService.selectRoleByUserId(userId);
    }
    /**
     * 查询用户下面的所有角色
     *  @param roleId 角色id
     * @return
     */
    @ApiOperation("查询角色下面的所有用户")
    @GetMapping("/selectUserByRoleId")
    @RequiresPermissions("dhlk:view")
    public Result selectUserByRoleId(@RequestParam(value = "roleId", required = true)Integer roleId,
                                     @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize){
        return  roleService.selectUserByRoleId(roleId,pageNum,pageSize);
    }
}
