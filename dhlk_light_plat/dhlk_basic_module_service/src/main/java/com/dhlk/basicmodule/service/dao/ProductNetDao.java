package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.basicmodule.NetDevices;
import com.dhlk.entity.basicmodule.ProductDevices;
import com.dhlk.entity.basicmodule.ProductNet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description 生产和网络设备关系配置
 * @Author lpsong
 * @Date 2020/3/11
 */
@Repository
public interface ProductNetDao {

    Integer insert(ProductNet productNets);


    Integer  insertBatch(List<ProductNet> productNets);

    Integer update(ProductNet productNets);

    Integer delete(List<String> ids);

    Integer  deleteByNetId(Integer netId);

    Integer  findListByNetId(String netId);

    List<NetDevices> findList(@Param("name") String name, @Param("type") Integer type, @Param("tenantId") Integer tenantId);

    List<NetDevices> findBiList(@Param("netId") Integer netId, @Param("tenantId") Integer tenantId);


    List<ProductDevices> findProductList(@Param("netId") Integer netId, @Param("tenantId") Integer tenantId);


    List<NetDevices> findNotBiList(Integer tenantId);


    List<ProductDevices> findNotProductList(Integer tenantId);


   Integer findBiIsBand(List<String> ids);


    Integer findProductIsBand(List<String> ids);
}