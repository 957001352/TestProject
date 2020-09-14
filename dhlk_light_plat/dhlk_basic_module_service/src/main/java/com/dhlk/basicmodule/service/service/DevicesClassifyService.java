package com.dhlk.basicmodule.service.service;

import com.dhlk.entity.basicmodule.DevicesClassify;
import com.dhlk.domain.Result;

/**
 * @Description 设备分类管理
 * @Author lpsong
 * @Date 2020/3/26
 */
public interface DevicesClassifyService {
    /**
     * 新增/修改
     * 判断分类名称不能重复
     * 同时新增属性和明细
     */
    Result save(DevicesClassify devicesClassify);

    /**
     * 物理删除 同时删除类型明细表
     * 判断如果设备类型已经绑定则不允许删除
     * @param id
     */
    Result delete(String id);

    /*
    * 查询
     * @param name 设备分配名称
    * @return
    */
    Result findList(String name);
}