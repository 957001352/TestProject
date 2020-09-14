package com.dhlk.basicmodule.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.NetDevicesSoft;

/**
 * 软件和网络设备关系管理
 */
public interface NetDevicesSoftService {
        /**
         * 新增/修改
         */
        Result save(NetDevicesSoft netDevicesSoft);
        /**
         * 物理删除
         * @param ids
         */
        Result delete(String ids);
        /**
         * 分页查询
         * @param pageNum
         * @param pageSize
         */
        Result findPageList(Integer pageNum, Integer pageSize);

}
