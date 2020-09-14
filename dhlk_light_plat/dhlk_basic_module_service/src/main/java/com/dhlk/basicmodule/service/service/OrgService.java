package com.dhlk.basicmodule.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Org;

/**
 * 机构管理
 */
public interface OrgService {
    /**
     * 新增/修改
     */
    Result save(Org org);


    /**
     * 逻辑删除，更改status为2
     * @param id
     */
    Result delete(Integer id);
    /**
     * 分页查询
     * @param parentId 父id
     * @param pageNum
     * @param pageSize
     */
    Result findPageList(Integer parentId,Integer pageNum, Integer pageSize);

    /**
     * 机构树查询
     */
    Result findTreeList();

    /**
     * 查询机构下的所有用户
     */
    Result findPageUserByOrgId(Integer orgId,Integer pageNum,Integer pageSize);
}
