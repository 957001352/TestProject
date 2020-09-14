package com.dhlk.basicmodule.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Menu;
import com.dhlk.entity.basicmodule.TenantMenu;

import java.util.List;


public interface MenuService {
    /**
     * 新增/修改
     * 判断菜单code是否重复
     */
    Result save(Menu menu);
    /**
     * 逻辑删除，更改status为2
     * @param ids
     */
    Result delete(String ids);
    /**
     * 根据菜单id查询菜单信息
     * @param id
     */
    Menu selectMenuById(Integer id) ;

    /**
     * 判断菜单code是否存在
     * @param code 菜单code
     * @
     */
    Menu selectMenuByCode(String code) ;

    /**
     * 分页查询
     * @param parentId 父id  非必传
     * @param pageNum
     * @param pageSize
     */
    Result findPageList(Integer parentId, Integer pageNum, Integer pageSize);

    /**
     * 状态修改
     * @param id 主键
     * @param status 0启用 1禁用
     */
    Result isEnable(Integer id, Integer status);

    /*
     * 判断菜单code是否重复
     */
    boolean isRepeatCode(Menu menu) ;

    /**
     * 菜单树查询
     */
    Result findTreeList();


    /**
     * 系统导航栏菜单查询
     * 根据用户id过滤
     */
    Result findTreeByUserId(Integer userId);
    /**
     * 获取菜单列表
     * 根据角色roleId过滤
     */
    Result  getMenuCheckedListByRoleId(String roleId);

    //保存租户拥有的菜单
    Result insertTenantAndMenu(Integer tenantId,String menuIds);

    Result getMenuCheckedListByTeanantId(Integer tenantId);
}
