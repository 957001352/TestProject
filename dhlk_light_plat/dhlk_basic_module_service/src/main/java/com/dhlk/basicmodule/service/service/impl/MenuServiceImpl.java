package com.dhlk.basicmodule.service.service.impl;

import com.dhlk.basicmodule.service.dao.MenuDao;
import com.dhlk.basicmodule.service.service.MenuService;
import com.dhlk.entity.basicmodule.TenantMenu;
import com.dhlk.util.AuthUserUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Menu;
import com.dhlk.domain.Tree;
import com.dhlk.enums.ResultEnum;
import com.dhlk.enums.SystemEnums;
import com.dhlk.exceptions.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;
import com.dhlk.utils.TreeUtil;

import java.util.*;
import java.util.stream.Collectors;
/*
 * Content: 菜单管理
 * Author:jlv
 * Date:2020/3/26
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private AuthUserUtil authUserUtil;
    @Override
    public Result save(Menu menu) {
        if(menu==null){
            return ResultUtils.error("菜单不能为空");
        }
        Integer res = null;
        Menu tempMenu = menuDao.selectMenuByCode(menu.getCode());
        if (!CheckUtils.isNull(menu.getId())) {
            //菜单id存在,则更新菜单
            if (tempMenu!=null && tempMenu.getId()==menu.getId()) {
                res = menuDao.update(menu);
                return res > 0 ? ResultUtils.success() : ResultUtils.failure();
            } else  return ResultUtils.error("菜单已存在");
        } else {
            if (tempMenu==null) {

                //菜单id不存在,则保存菜单
                res = menuDao.insert(menu);
                return res > 0 ? ResultUtils.success() : ResultUtils.failure();
            } else return ResultUtils.error("菜单已存在");
        }
    }
    //condition 参数就是执行Cacheable的条件
    //@Caching(evict={@CacheEvict(value = "common", key="'MenuDelete_'+#ids.split(',')",condition="#blog!=null")})
    @Override
    public Result delete(String ids) {
        if(CheckUtils.isNull(ids)){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        List<String> list=Arrays.asList(ids.split(","));
        Integer res = menuDao.deleteMenuByIds(list);
        return res > 0 ? ResultUtils.success() : ResultUtils.failure();
    }
    //unless 参数就是不执行Cacheable的条件
    //@Cacheable(value = "common", key = "'MenuId_'+#id", unless = "#id == null")
    @Override
    public Menu selectMenuById(Integer id) {
        if(!CheckUtils.checkId(id) || id<1){
            new MyException(SystemEnums.PARMS_ILLEGAL);
        }


        return menuDao.selectMenuById(id);
    }

    @Override
    public Menu selectMenuByCode(String code) {
        if(!CheckUtils.isNull(code)){
            new MyException(SystemEnums.PARMS_ILLEGAL);
        }
        return menuDao.selectMenuByCode(code);
    }
    //condition 参数就是执行Cacheable的条件
  /*  @Caching(evict={@CacheEvict(value = "common", key="'MenuParentId_'+#id",condition="#id!=null")
            , @CacheEvict(value = "common", key="'MenuPageNum_'+#pageNum",condition="#pageNum!=null")
            , @CacheEvict(value = "common", key="'MenuPageSize_'+#pageSize",condition="#pageSize!=null")})*/
    @Override
    public Result findPageList(Integer parentId, Integer pageNum, Integer pageSize) {
        if( !CheckUtils.checkId(pageNum) || !CheckUtils.checkId(pageSize)){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Menu> menus = menuDao.findList(parentId,null,null,null,0);
        PageInfo<Menu> menusInfo = new PageInfo<>(menus);
        return new Result(SystemEnums.SUCESS, menusInfo);
    }

    @Override
    public Result isEnable(Integer id, Integer status) {
        if(!CheckUtils.checkId(id) || id<1 || !CheckUtils.checkStatus(status)){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        Menu menu=new Menu();
        menu.setId(id);
        menu.setStatus(status);
        Integer res=menuDao.update(menu);
        return new Result(SystemEnums.SUCESS, res);
    }

    @Override
    public boolean isRepeatCode(Menu menu)  {
        if(!CheckUtils.isNull(menu)){
            new MyException(SystemEnums.PARMS_ILLEGAL);
        }
        return CheckUtils.isNull(menuDao.selectMenuByCode(menu.getCode()));
    }
    //@Cacheable(value = "common", key = "MenuFindTreeList")
    @Override
    public Result findTreeList() {
        List<Menu> list = menuDao.findList(null,null,null,null,0);
        if(list.isEmpty()){
            return ResultUtils.error("没有查询到菜单树");
        }
        List<Menu> treeLi =treeBuilder(list);
        return ResultUtils.success(treeLi);
    }

    @Override
    public Result findTreeByUserId(Integer userId) {
        if(!CheckUtils.checkId(userId) || userId<1){
            return ResultUtils.error("id参数错误");
        }
        List<Menu> list = menuDao.findTreeByUserId(userId);
        if(list.isEmpty()){
            return ResultUtils.error("没有查询到菜单树");
        }
        List<Menu> treeLi=treeBuilder(list);
        return ResultUtils.success(treeLi);
    }

    @Override
    public Result getMenuCheckedListByRoleId(String roleId) {
        if(roleId != null && !CheckUtils.isNumeric(roleId)){
            return ResultUtils.error(ResultEnum.PARAM_ERR.getStateInfo());
        }
        // 根据角色roleId过滤 获取菜单列表
        List<Menu> list= menuDao.getMenuCheckedList(authUserUtil.tenantId(),roleId);
        //过滤选中的菜单id
        List<String> ids = list.stream().filter(menu -> menu.getChecked()).map(menu -> menu.getId().toString()).collect(Collectors.toList());
        //构建菜单树
        List<Tree<Menu>> trees = new ArrayList<>();
        buildTrees(trees, list);
        Tree<Menu> menuTree = TreeUtil.build(trees);
        //封装结果集并返回前端
        Map<String,Object> result=new HashMap<String,Object>();
        result.put("total",list.size());
        result.put("ids",ids);
        result.put("rows",menuTree);
        return ResultUtils.success(result);
    }

    @Override
    public Result insertTenantAndMenu(Integer tenantId, String menuIds) {
        if (CheckUtils.isNull(tenantId)) {
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        //先根据租户id删除对应的租户
        Integer res = menuDao.deleteByTenantId(tenantId);
        //保存住户拥有的菜单
        if (res >= 0 && !CheckUtils.isNull(menuIds)) {
            List<TenantMenu> tenantMenuList = new ArrayList<>();
            Arrays.asList(menuIds.split(",")).stream().forEach(s -> tenantMenuList.add(new TenantMenu(Integer.parseInt(s),tenantId)));
            Integer res1=menuDao.insertTenantAndMenu(tenantMenuList);
            return ResultUtils.success();
        }else  return ResultUtils.failure();
    }

    @Override
    public Result getMenuCheckedListByTeanantId(Integer tenantId) {
        if(!CheckUtils.checkId(tenantId) || tenantId<1){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        // 根据租户tenantId过滤 获取菜单列表
        List<Menu> list= menuDao.getMenuCheckedListByTeanantId(tenantId);
        //过滤选中的菜单id
        List<String> ids = list.stream().filter(menu -> menu.getChecked()).map(menu -> menu.getId().toString()).collect(Collectors.toList());
        //构建菜单树
        List<Tree<Menu>> trees = new ArrayList<>();
        buildTrees(trees, list);
        Tree<Menu> menuTree = TreeUtil.build(trees);
        //封装结果集并返回前端
        Map<String,Object> result=new HashMap<String,Object>();
        result.put("total",list.size());
        result.put("ids",ids);
        result.put("rows",menuTree);
        return ResultUtils.success(result);
    }


    /**
     *适配转换1
     * @param list
     */
    private List<Menu> treeBuilder(List<Menu> list) {
        List<Menu> treeLi = new ArrayList<Menu>();
        for (Menu pojo : list) {
            if (pojo.getParentId().intValue() == 0) {
                treeBuilder(pojo, list);
                treeLi.add(pojo);
            }
        }
        return treeLi;
    }
    /**
     *  适配转换2
     * @param menu
     * @param list
     */
    private void treeBuilder(Menu menu, List<Menu> list) {
        for (Menu child : list) {
            if (menu.getId().intValue() == child.getParentId()) {
                treeBuilder(child, list);
                menu.getChildList().add(child);
            }
        }
    }
   private void buildTrees(List<Tree<Menu>> trees, List<Menu> menus) {
       menus.forEach(menu->{
           Tree<Menu> tree = new Tree<>();
           tree.setId(menu.getId().toString());
           tree.setParentId(menu.getParentId().toString());
           tree.setTitle(menu.getName());
           tree.setChecked(menu.getChecked());
           trees.add(tree);
       });
   }
}
