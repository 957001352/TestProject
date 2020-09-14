package com.dhlk.basicmodule.service.service.impl;

import com.dhlk.basicmodule.service.dao.PermissionsDao;
import com.dhlk.basicmodule.service.dao.RoleDao;
import com.dhlk.basicmodule.service.service.RoleService;
import com.dhlk.entity.basicmodule.User;
import com.dhlk.util.AuthUserUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Permissions;
import com.dhlk.entity.basicmodule.Role;
import com.dhlk.enums.ResultEnum;
import com.dhlk.enums.SystemEnums;
import com.dhlk.exceptions.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;

import java.util.Arrays;
import java.util.List;
/*
 * Content: 角色管理
 * Author:jlv
 * Date:2020/3/26
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionsDao permissionsDao;

    @Autowired
    private AuthUserUtil authUserUtil;

    @Override
    public Result save(Role role) {
        if(role==null){
            return ResultUtils.error(ResultEnum.PARAM_ERR.getStateInfo());
        }
        if(role.getTenantId() == null){
            role.setTenantId(authUserUtil.tenantId());
        }
        Integer res = null;
        Role tempRole = roleDao.selectRoleByName(role.getName(),authUserUtil.tenantId());
        if (!CheckUtils.isNull(role.getId())) {
            //角色id存在,则更新角色
            if (tempRole==null || (tempRole!=null && tempRole.getId()==role.getId())) {
                res = roleDao.update(role);
                return res > 0 ? ResultUtils.success() : ResultUtils.failure();
            } else return ResultUtils.error("角色已存在");
        } else {
            if (tempRole==null) {
                //角色id不存在,则保存角色
                res = roleDao.insert(role);
                return res > 0 ? ResultUtils.success() : ResultUtils.failure();
            } else return ResultUtils.error("角色已存在");
        }
    }
    @Override
    public Result delete(String roleIds) {
        if(CheckUtils.isNull(roleIds)){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        List<String> list= Arrays.asList(roleIds.split(","));
        //是否为角色分配权限
        List<Permissions> permissions = permissionsDao.selectListByRoleIds(list);
        boolean flag = existRole(list);
        if(permissions.isEmpty() && flag){
            Integer res=roleDao.deleteRoleByIds(list);
            return res > 0 ? ResultUtils.success() : ResultUtils.failure();
        }else{
            return ResultUtils.error("角色下面已存在权限或已分配给用户，不能删除");
        }
    }

    @Override
    public Role selectRoleById(Integer id)  {
        if(!CheckUtils.checkId(id) || id<1){
            new MyException(SystemEnums.PARMS_ILLEGAL);
        }
        return roleDao.selectRoleById(id);
    }

    @Override
    public Result findPageList(Integer pageNum, Integer pageSize) {
        if(!CheckUtils.checkId(pageNum) || !CheckUtils.checkId(pageSize)){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roles = roleDao.findList(null,null,authUserUtil.tenantId(),authUserUtil.userId());
        PageInfo<Role> rolesInfo = new PageInfo<>(roles);
        return ResultUtils.success(rolesInfo);
    }

    @Override
    public Result selectRoleByName(String name){
        if(!CheckUtils.isNull(name)){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        return ResultUtils.success(roleDao.selectRoleByName(name,authUserUtil.tenantId()));
    }

    @Override
    public Result findAllList() {
        List<Role> roles = roleDao.findList(null,null,authUserUtil.tenantId(),authUserUtil.userId());
        return ResultUtils.success(roles);
    }

    @Override
    public Result selectRoleByUserId(Integer userId) {
        if(!CheckUtils.checkId(userId) || userId<1){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        List<Role> roles =roleDao.selectRoleByUserId(userId);
        //当List中元素user对象为空时,清除List中的元素
        if(roles!=null && roles.size()>0 && roles.get(0)==null){
            roles.clear();
        }
        return ResultUtils.success(roles);
    }

    @Override
    public Result selectableRoleByUserId(Integer userId) {
        if(!CheckUtils.checkId(userId) || userId<1){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        List<Role> roles = roleDao.selectableRoleByUserId(userId);
        //当List中元素user对象为空时,清除List中的元素
        if(roles.get(0)==null){
            roles.clear();

        }
        return ResultUtils.success(roles);
    }

    @Override
    public Result selectUserByRoleId(Integer roleId,Integer pageNum, Integer pageSize) {
        if(!CheckUtils.checkId(roleId) || roleId<1){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = roleDao.selectUserByRoleId(roleId);
        PageInfo<User> usersInfo = new PageInfo<>(users);
        return ResultUtils.success(usersInfo);
    }
    /**
     * 用户是否已分配该角色
     * @param roleIdList 角色集合
     */
    private boolean existRole(List<String> roleIdList) {
        boolean flag=true;
        for (String roleId : roleIdList) {
            List<User> users = roleDao.selectUserByRoleId(Integer.parseInt(roleId));
            if (users != null &&  users.size()>0) { //用户拥有该角色
               flag=false;
               break;
            }
        }
        return flag;
    }
}
