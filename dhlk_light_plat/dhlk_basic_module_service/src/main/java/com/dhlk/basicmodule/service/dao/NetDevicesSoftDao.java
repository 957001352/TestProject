package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.basicmodule.NetDevicesSoft;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description 软件设备
 * @Author lpsong
 * @Date 2020/3/11
 */
@Repository
public interface NetDevicesSoftDao {

    Integer insert(NetDevicesSoft netDevicesSoft);

    Integer update(NetDevicesSoft netDevicesSoft);

    Integer delete(List<String> ids);

    Integer deleteByNetDevicesId(Integer id);


    Integer insertBatch(@Param("list") List<NetDevicesSoft> netDevicesSofts,@Param("netDevicesId") Integer netDevicesId);

    List<NetDevicesSoft> findList();
}
