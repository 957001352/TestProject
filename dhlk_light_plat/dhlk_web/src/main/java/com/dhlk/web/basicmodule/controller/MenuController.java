package com.dhlk.web.basicmodule.controller;

import com.dhlk.entity.basicmodule.Menu;
import com.dhlk.web.basicmodule.service.MenuService;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.dhlk.utils.ResultUtils;

import javax.validation.Valid;


/**
* 菜单管理
*/
@RestController
@RequestMapping(value = "/menu")
@Api(description = "菜单管理")
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 新增/修改
     * @param menu
     * @return
     */
    @ApiOperation("新增/修改")
    @PostMapping(value = "/save")
    public Result save(@Valid  @RequestBody Menu menu, BindingResult bindingResult) {
        Result result = ResultUtils.loadResult(bindingResult);
        if (result==null) {
            return menuService.save(menu);
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
    public Result delete(@RequestParam(value = "ids") String ids) {
        return menuService.delete(ids);
    }

    /**
    * 列表查询
     * @param parentId
     * @param pageNum
     * @param pageSize
    * @return
    */
    @ApiOperation("分页查询")
    @GetMapping("/findPageList")
    public Result findPageList(@RequestParam(value = "parentId", required = false) Integer parentId,
                           @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return  menuService.findPageList(parentId,pageNum, pageSize);
    }

    /**
     * 菜单状态修改
     * @param id
     * @param status
     * @return
     */
    @ApiOperation("菜单状态修改")
    @PostMapping("/isEnable")
    public Result isEnable(Integer id, Integer status){
        return  menuService.isEnable(id,status);
    }
    /**
     * 菜单树查询
     * @return
     */
    @ApiOperation("菜单树查询")
    @GetMapping("/findTreeList")
    public Result findTreeList() {
        return menuService.findTreeList();
    }

    /**
     * 系统导航栏菜单查询
     * 根据用户id过滤
     * @param userId
     * @return
     */
    @ApiOperation("根据用户Id过滤菜单树")
    @GetMapping("/findTreeByUserId")
    public Result findTreeByUserId(@RequestParam(value="userId") Integer userId){
        return menuService.findTreeList();
    }
    /**
     *获取资源列表
     * 根据角色roleId过滤
     * @param roleId
     * @return
     */
    @ApiOperation("根据角色roleId获取资源列表")
    @GetMapping("/getMenuCheckedListByRoleId")
    public Result  getMenuCheckedListByRoleId(@RequestParam(value="roleId", required = false) String roleId){
        return menuService.getMenuCheckedListByRoleId(roleId);
    }
    /**
     * 保存租户拥有的菜单
     * @param tenantId
     * @param menuIds
     * @return
     */
    @ApiOperation("新增/修改")
    @PostMapping(value = "/saveTenantAndMenu")
    public Result saveTenantAndMenu(@RequestParam(value = "tenantId")Integer tenantId,@RequestParam(value = "menuIds",required=false)String menuIds) {
        return menuService.saveTenantAndMenu(tenantId,menuIds);
    }
    /**
     *获取资源列表
     * 根据租户tenantId过滤
     * @param tenantId
     * @return
     */
    @ApiOperation("根据租户tenantId获取菜单列表")
    @GetMapping("/getMenuCheckedListByTeanantId")
    public Result  getMenuCheckedListByTeanantId(@RequestParam(value="tenantId", required = true) Integer tenantId){
        return menuService.getMenuCheckedListByTeanantId(tenantId);
    }

}
