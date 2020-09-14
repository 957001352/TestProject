package com.dhlk.web.basicmodule.service;

import com.dhlk.entity.basicmodule.Menu;
import com.dhlk.domain.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 菜单管理
 */
@FeignClient(value = "basicmodule-service/menu")
public interface MenuService {
    /**
     * 新增/修改
     * 判断菜单code是否重复
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody Menu menu);
    /**
     * 逻辑删除，更改status为2
     * @param ids
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    Result delete(@RequestParam(value = "ids") String ids);
    /**
     * 根据菜单id查询菜单信息
     * @param id
     */
    @RequestMapping(value = "/selectMenuById", method = RequestMethod.GET)
    Menu selectMenuById(@RequestParam(value = "id")Integer id);

    /**
     * 判断菜单code是否存在
     * @param code 菜单code
     * @
     */
    @RequestMapping(value = "/selectMenuByCode", method = RequestMethod.GET)
    Menu selectMenuByCode(@RequestParam(value = "code")String code);

    /**
     * 分页查询
     * @param parentId 父id  非必传
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping(value = "/findPageList", method = RequestMethod.GET)
    Result findPageList(@RequestParam("parentId") Integer parentId, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 状态修改
     * @param id 主键
     * @param status 0启用 1禁用
     */
    @PostMapping(value = "/isEnable")
    Result isEnable(@RequestParam("id") Integer id, @RequestParam("status") Integer status);

    /*
     * 判断菜单code是否重复
     */
    @RequestMapping(value = "/isRepeatCode", method = RequestMethod.GET)
    boolean isRepeatCode(Menu menu) ;

    /**
     * 菜单树查询
     */
    @RequestMapping(value = "/findTreeList", method = RequestMethod.GET)
    Result findTreeList();


    /**
     * 系统导航栏菜单查询
     * 根据用户id过滤
     */
    @RequestMapping(value = "/findTreeByUserId", method = RequestMethod.GET)
    Result findTreeByUserId(@RequestParam(value = "userId")Integer userId);
    /**
     * 获取资源列表
     * 根据角色roleId过滤
     */
    @RequestMapping(value = "/getMenuCheckedListByRoleId", method = RequestMethod.GET)
    Result  getMenuCheckedListByRoleId(@RequestParam(value = "roleId") String roleId);

    /**
     * 保存租户拥有的菜单
     * @param tenantId
     * @param menuIds
     * @return
     */
    @PostMapping(value = "/saveTenantAndMenu")
    public Result saveTenantAndMenu(@RequestParam(value = "tenantId")Integer tenantId,@RequestParam(value = "menuIds",required=false)String menuIds);

    /* 获取资源列表
     根据租户tenantId过滤*/
    @GetMapping("/getMenuCheckedListByTeanantId")
    public Result  getMenuCheckedListByTeanantId(@RequestParam(value="tenantId", required = true) Integer tenantId);
}
