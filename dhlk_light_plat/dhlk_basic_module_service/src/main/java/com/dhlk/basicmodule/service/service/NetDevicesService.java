package com.dhlk.basicmodule.service.service;

import com.dhlk.entity.basicmodule.NetDevices;
import com.dhlk.domain.Result;

/**
 * 网络设备管理
 */
public interface NetDevicesService {
        /**
         * 新增/修改
         * 当生产设备不为空时新增生产设备和网络设备关系表
         */
        Result save(NetDevices netDevices) throws Exception;
        /**
         * 物理删除
         * @param ids
         */
        Result delete(String ids) throws Exception;

        /**
         *列表查询
         * @param name
         * @return
         */
        Result findList(String name);
        /*
         * 网络设备关联的生产设备查询
         * @param netDevicesId
         * @return
         */
        Result findPruductDevicesList(Integer netDevicesId);
        /**
         * 启用停用
         * @param id 主键
         * @param status 0启用 1停用
         */
        Result isEnable(Integer id,Integer status);
}
