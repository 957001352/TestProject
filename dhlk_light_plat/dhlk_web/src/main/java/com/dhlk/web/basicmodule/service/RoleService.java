package com.dhlk.web.basicmodule.service;

import com.dhlk.entity.basicmodule.Role;
import com.dhlk.web.basicmodule.service.fbk.RoleServiceFbk;
import com.dhlk.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 角色管理
 */
@FeignClient(value = "basicmodule-service/role", fallback = RoleServiceFbk.class)
public interface RoleService {
    /**
     * 新增/修改
     * 新增角色基本信息和角色菜单关系(权限维护)
     * 判断角色名称是否重复
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody Role role);

    /**
     * 物理删除
     * 判断如果角色id在有权限表有数据则不允许删除
     * @param ids
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    Result delete(@RequestParam("ids")String ids);
    /**
     * 根据角色id查询角色信息
     * @param id
     */
    @RequestMapping(value = "/selectRoleById", method = RequestMethod.GET)
    Result selectRoleById(@RequestParam("id")Integer id);
    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/findPageList", method = RequestMethod.GET)
    Result findPageList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 根据角色名称查询角色
     * @param name
     */
    //@RequestMapping(value = "/selectRoleByName", method = RequestMethod.GET)
    @GetMapping("/selectRoleByName")
    Result selectRoleByName(@RequestParam("name")String name) ;

    /**
     * 查询所有
     */
    @GetMapping("/findAllList")
    Result findAllList();
    /**
     * 查询用户下面的所有角色
     */
    @GetMapping("/selectRoleByUserId")
    Result selectRoleByUserId(@RequestParam("userId")Integer userId);

    //查询角色下的所有用户
    @GetMapping("/selectUserByRoleId")
    Result selectUserByRoleId(@RequestParam("roleId")Integer roleId,@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);
}
