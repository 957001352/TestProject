package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.basicmodule.NetDevices;
import com.dhlk.entity.basicmodule.ProductDevices;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description 网络设备
 * @Author lpsong
 * @Date 2020/3/11
 */

@Repository
public interface NetDevicesDao {

    Integer insert(NetDevices NetDevices);

    Integer update(NetDevices NetDevices);

    Integer delete(List<String> id);


    List<NetDevices> findList(String name,Integer tenantId);


    List<NetDevices> findBiList(Integer netId);


    List<ProductDevices> findPruductDevicesList(Integer netDevicesId);

    Integer isEnable(@Param("id") Integer id, @Param("status") Integer status);

    NetDevices findProductNetDevicesById(@Param("id") Integer id);

    List<NetDevices> findTbIdsListbyIds(List<String> ids);

    Integer deleteById(Integer id);
    /*
     * 判断网络设备名称是否重复
     * @return
     */
    Integer isRepeatName(NetDevices NetDevices);
}
