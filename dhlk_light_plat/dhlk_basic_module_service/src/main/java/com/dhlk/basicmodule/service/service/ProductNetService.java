package com.dhlk.basicmodule.service.service;

import com.dhlk.domain.Result;

/**
 * 生产和网络设备关系配置
 */
public interface ProductNetService {
        /**
         * 新增/修改
         */
        Result save(String netId,String productIds,Integer type) throws Exception;
        /**
         * 物理删除
         * @param id
         */
        Result delete(String id);

        /**
        * 列表查询
        * @return
        */
        Result findList(String name,Integer type);


        /**
         * 生产设备查询
         * @return
         */
        Result findBiList(Integer netId);

        /**
         * BI控制器查询
         * @return
         */
        Result findComputerList(Integer netId);


        /**
         * 生产设备查询
         * @return
         */
        Result findNotBiList();

        /**
         * BI控制器查询
         * @return
         */
        Result findNotComputerList();
}
