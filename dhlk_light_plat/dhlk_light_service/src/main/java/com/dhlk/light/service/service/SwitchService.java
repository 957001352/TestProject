package com.dhlk.light.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Switch;

/**
 * 开关
 * gchen
 */
public interface SwitchService {
    /**
     * 新增/修改
     */
    Result save(Switch swich);
    /**
     * 批量删除
     * @param ids
     */
    Result delete(String ids);
    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     */
    Result findList(String name,Integer pageNum, Integer pageSize);


    Result  findGroupList(Integer switchId);
}
