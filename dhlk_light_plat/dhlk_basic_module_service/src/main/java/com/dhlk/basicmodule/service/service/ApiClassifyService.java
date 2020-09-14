package com.dhlk.basicmodule.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.api.ApiClassify;

import java.util.List;
import java.util.Map;

/**
 * API分类管理
 */
public interface ApiClassifyService {
    /**
     * 新增/修改
     * 判断分类名称是否重复
     */
    Result save(ApiClassify apiClassify);

    /**
     * 物理删除
     * 判断有子节点则不允许删除
     * @param ids
     */
    Result delete(String ids);
    /**
     * 分页查询
     * @param parentId 父id 非必传
     * @param pageNum
     * @param pageSize
     */
    Result findPageList(Integer parentId, Integer pageNum, Integer pageSize);


    /**
     * API分类树查询
     */
    Result findTreeList();

}
