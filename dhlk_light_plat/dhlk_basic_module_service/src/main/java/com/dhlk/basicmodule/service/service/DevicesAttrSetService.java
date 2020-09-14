package com.dhlk.basicmodule.service.service;

import com.dhlk.entity.basicmodule.DevicesAttrSet;
import com.dhlk.domain.Result;

/**
 * @Description设备属性管理
 * @Author lpsong
 * @Date 2020/3/26
 */
public interface DevicesAttrSetService {
    /**
     * 新增/修改
     * 同时新增属性和明细
     * 判断属性集合名称不能重复
     */
    Result save(DevicesAttrSet devicesAttrSet);

    /**
     * 物理删除 同时删除设备属性明细表
     *删除时判断该属性是否有与分类绑定，如果已经绑定了则不允许删除
     * @param id
     */
    Result delete(Integer id);

    /**
    * 查询
     * @param name 属性集合名称
    * @return
    */
    Result findList(String name);
}