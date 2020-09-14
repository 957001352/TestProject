package com.dhlk.web.basicmodule.controller;

import com.dhlk.entity.basicmodule.Role;
import com.dhlk.web.basicmodule.service.RoleService;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.dhlk.utils.ResultUtils;

import javax.validation.Valid;


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
    public Result save(@ApiParam(name="角色对象",value="传入json格式") @Valid @RequestBody  Role role, BindingResult bindingResult) {
        Result result = ResultUtils.loadResult(bindingResult);
        if (result==null) {
            return roleService.save(role);
        }
        return result;
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @ApiOperation("删除")
    @GetMapping(value = "/delete")
    public Result delete(@ApiParam(name="ids",value="数据id,多个时逗号隔开，如1,2",required=true) @RequestParam(value = "ids") String ids) {
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
    public Result selectRoleByUserId(@RequestParam(value="userId") Integer userId){
        return  roleService.selectRoleByUserId(userId);
    }
    /**
     * 查询用户下面的所有角色
     *  @param roleId 用户id
     * @return
     */
    @ApiOperation("查询角色下面的所有用户")
    @GetMapping("/selectUserByRoleId")
    public Result selectUserByRoleId(@RequestParam(value = "roleId", required = true)Integer roleId,
                                     @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return roleService.selectUserByRoleId(roleId, pageNum, pageSize);
    }
}
