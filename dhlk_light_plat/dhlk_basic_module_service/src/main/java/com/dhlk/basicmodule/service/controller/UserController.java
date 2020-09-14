package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.UserService;
import com.dhlk.entity.basicmodule.User;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* 用户管理
*/
@RestController
@Api(value = "UserController", description = "用户管理")
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 保存
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    @RequiresPermissions("user:save")
    public Result save(@RequestBody User user) {
        Result result = userService.save(user);
        return result;
    }
    /**
     * 批量删除
     * @param ids
     * @return result
     */
    @GetMapping(value = "/delete")
    @RequiresPermissions("user:delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return userService.delete(ids);
    }

    /**
    * 列表查询
     * @param name name为模糊查询，name如果为null或者为空就是全表扫描
    * @return
    */
    @GetMapping("/findList")
    @RequiresAuthentication
    public Result findList(@RequestParam(value = "name", required = false) String name) {
        return  userService.findList(name);
    }
    /**
     * 修改密码
     * @param id
     * @param password
     * @return
     */
    @GetMapping("/updatePassword")
    @RequiresPermissions("user:updatePassword")
    public Result updatePassword(@RequestParam(value = "id") Integer id,
                            @RequestParam(value = "password") String password) {
        return  userService.updatePassword(id,password);
    }

    /**
     * 查询用户所属的机构
     * @param id
     * @return
     */
    @GetMapping("/findOrg")
    @RequiresPermissions("dhlk:view")
    public Result findOrg(@RequestParam(value = "id") Integer id) {
        return  userService.findOrg(id);
    }

    /**
     * 改变用户状态
     * @param id
     * @return
     */
    @GetMapping("/isEnable")
    @RequiresPermissions("user:isEnable")
    public Result isEnable(@RequestParam(value = "id") Integer id,
                           @RequestParam(value = "status") Integer status) {
        return  userService.isEnable(id,status);
    }

    /**
     * 根据租户Id列表查询
     * @return
     */
    @GetMapping("/findListByTenantId")
    @RequiresAuthentication
    public Result findListByTenantId(@RequestParam(value = "tenantId", required = false) String tenantId) {
        return  userService.findListByTenantId(tenantId);
    }
}
