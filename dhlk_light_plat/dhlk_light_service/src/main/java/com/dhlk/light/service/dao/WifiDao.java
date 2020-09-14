package com.dhlk.light.service.dao;

import com.dhlk.entity.light.Wifi;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xkliu
 * @date 2020/6/30
 */
@Repository
public interface WifiDao {

    /**
     * 根据Id获取Wifi对象
     *
     * @param id
     * @return
     */
    Wifi selectTenantById(@Param("id") Integer id);

    /**
     * 新增
     *
     * @param wifi
     * @return
     */
    Integer insert(Wifi wifi);

    /**
     * 修改
     *
     * @param wifi
     * @return
     */
    Integer update(Wifi wifi);


    /**
     * 批量删除,物理删除
     *
     * @param asList
     * @return
     */
    Integer delete(List<String> asList);

    /**
     * 根据tenantId物理删除
     *
     * @param tenantId
     * @return
     */
    Integer deleteByTenantId(@Param("tenantId") Integer tenantId);

    /**
     * PeopleFeel
     *
     * @param tenantId
     * @return
     */
    Wifi selectByTenantId(@Param("tenantId") Integer tenantId);

}
