package com.dhlk.basicmodule.service.service;

import com.dhlk.entity.dm.DmClassifyType;
import com.dhlk.domain.Result;

/**
 * 设备类型分类
 */
public interface DmClassifyTypeService {
    /**
     * 新增/修改
     * 判断接口名称是否重复
     */
    Result save(DmClassifyType dmClassifyType);
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
