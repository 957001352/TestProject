package com.dhlk.basicmodule.service.service.impl;

import com.dhlk.basicmodule.service.dao.OrgDao;
import com.dhlk.basicmodule.service.dao.UserDao;
import com.dhlk.basicmodule.service.service.OrgService;
import com.dhlk.domain.Tree;
import com.dhlk.util.AuthUserUtil;
import com.dhlk.utils.TreeUtil;
import com.dhlk.entity.basicmodule.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Org;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;

@Service
@Transactional
public class OrgServiceImpl implements OrgService {
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthUserUtil authUserUtil;
    @Override
    public Result save(Org org) {
        Integer flag = -1;
        if(org.getTenantId() == null){
            org.setTenantId(authUserUtil.tenantId());
        }

        if(orgDao.isRepeatName(org) > 0){
            return ResultUtils.error("存在同级相同名称部门");
        }
        if(CheckUtils.isNull(org.getId())){
            flag = orgDao.insert(org);
        }else{
            flag = orgDao.update(org);
        }
        return flag>0? ResultUtils.success(org):ResultUtils.failure();
    }

    @Override
    public Result delete(Integer id) {
        if(!CheckUtils.checkId(id)){
            return ResultUtils.error("id输入错误");
        }
        if(orgDao.isTopOrg(id,authUserUtil.tenantId()) > 0)
            return ResultUtils.error("无法删除该部门");
        List<Org> underOrgs = orgDao.findUnderOrgById(id);
        for (Org or:underOrgs) {
            List<User> userByOrgId = userDao.findUserByOrgId(or.getId());
            if(userByOrgId.size() > 0){
                return ResultUtils.error("请先删除机构下的所有用户");
            }
        }

        if(orgDao.delete(underOrgs) > 0){
            return ResultUtils.success();
        }
        return ResultUtils.failure();
    }

    @Override
    public Result findPageList(Integer parentId, Integer pageNum, Integer pageSize) {
        if(!CheckUtils.checkId(parentId)){
            return ResultUtils.error("parentId输入错误");
        }
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Org> pageInfo = new PageInfo<>(orgDao.findList(parentId,authUserUtil.tenantId()));
        return ResultUtils.success(pageInfo);
    }

    @Override
    public Result findTreeList() {
        List<Org> orgs = orgDao.treeList(0,authUserUtil.tenantId());
        List<LinkedHashMap<Integer, Integer>> orgList = userDao.findOrgList(authUserUtil.tenantId());
        for (Org org:orgs) {
            int count = 0;
            for (LinkedHashMap<Integer, Integer> orgId:orgList) {
               if(orgId.get("orgId") != null && org.getId() == orgId.get("orgId")) {
                   count = Integer.parseInt(orgId.get("count") + "");
               }
            }
            org.setStaffNum(count);
        }
        for (Org org:orgs) {
            getStaff(org,orgs);
        }
        List<Tree<Org>> trees = new ArrayList<>();
        buildTrees(trees, orgs);
        Tree<Org> orgTree = TreeUtil.build(trees);
        return ResultUtils.success(orgTree);
    }


    public void getStaff(Org org,List<Org> orgs){
        if(org.getParentId()!=0){
            for (Org org1:orgs) {
                if(org1.getId().equals(org.getParentId())){
                    getStaff(org1,orgs);
                    org1.setStaffNum(org.getStaffNum() +  org1.getStaffNum());
                }
            }
        }
    }

    @Override
    public Result findPageUserByOrgId(Integer orgId,Integer pageNum,Integer pageSize) {
        if(!CheckUtils.checkId(orgId)){
            return ResultUtils.error("orgId输入错误");
        }
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<User> pageInfo = new PageInfo<>(userDao.findUserByOrgId(orgId));
        return ResultUtils.success(pageInfo);
    }


    private void buildTrees(List<Tree<Org>> trees, List<Org> orgs) {
        orgs.forEach(org -> {
            Tree<Org> tree = new Tree<>();
            tree.setId(org.getId().toString());
            tree.setParentId(org.getParentId().toString());
            tree.setTitle(org.getName());
            tree.setStaffNum(org.getStaffNum());
            trees.add(tree);
        });
    }
}
