package com.dhlk.basicmodule.service.service;

import com.dhlk.entity.dm.DmNetType;
import com.dhlk.domain.Result;

/**
 * 网络设备分类
 */
public interface DmNetTypeService {
    /**
     * 新增/修改
     * 判断接口名称是否重复
     */
    Result save(DmNetType dmNetType);
    /**
     * 物理删除
     * @param ids
     */
    Result delete(String ids);
    /**
     * 列表查询
     */
    Result findList(String name);

}
