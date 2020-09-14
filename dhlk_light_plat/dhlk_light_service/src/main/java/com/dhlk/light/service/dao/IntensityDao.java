package com.dhlk.light.service.dao;

import com.dhlk.entity.light.Intensity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xkliu
 * @date 2020/6/30
 */
@Repository
public interface IntensityDao {

    /**
     * 根据Id获取Intensity对象
     *
     * @param id
     * @return
     */
    Intensity selectTenantById(@Param("id") Integer id);

    /**
     * 新增
     *
     * @param intensity
     * @return
     */
    Integer insert(Intensity intensity);

    /**
     * 修改
     *
     * @param intensity
     * @return
     */
    Integer update(Intensity intensity);


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
    Intensity selectByTenantId(@Param("tenantId") Integer tenantId);
}
