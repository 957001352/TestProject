package com.dhlk.light.service.dao;

import com.dhlk.entity.light.OriginalPower;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xkliu
 * @date 2020/6/10
 * <p>
 * 企业历史照明功率维护Dao
 */
@Repository
public interface OriginalPowerDao {

    /**
     * 根据Id获取OriginalPower对象
     *
     * @param id
     * @return
     */
    OriginalPower selectTenantById(@Param("id") Integer id);

    /**
     * 新增
     *
     * @param originalPower
     * @return
     */
    Integer insert(OriginalPower originalPower);

    /**
     * 修改
     *
     * @param originalPower
     * @return
     */
    Integer update(OriginalPower originalPower);

    /**
     * 查询列表
     *
     * @param ledCount
     * @param ledPower
     * @param ledOpentime
     * @param tenantId
     * @return
     */
    List<OriginalPower> findList(@Param("ledCount") Integer ledCount, @Param("ledPower") Float ledPower, @Param("ledOpentime") Float ledOpentime, @Param("tenantId") Integer tenantId);

    /**
     * 批量删除,物理删除
     *
     * @param asList
     * @return
     */
    Integer delete(List<String> asList);


    /**
     * 根据tenantId获取OriginalPower对象
     *
     * @param tenantId
     * @return
     */
    OriginalPower selectOpByTenantId(@Param("tenantId") Integer tenantId);


    /**
     * 根据tenantId物理删除
     *
     * @param tenantId
     * @return
     */
    Integer deleteByTenantId(@Param("tenantId") Integer tenantId);

    /**
     * 根据租户Id修改OriginalPower
     *
     *
     * @param tenantId
     * @return
     */
    Integer updateOriginalPower(@Param("preBrightness") Integer preBrightness,@Param("tenantId") Integer tenantId);

    /**
     * 根据tenantId获取OriginalPower对象
     *
     * @param tenantId
     * @return
     */
    OriginalPower selectOriginalPowerByTenantId(@Param("tenantId") Integer tenantId);

    Integer setValues(@Param("ledCount")Integer ledCount, @Param("ledOpentime")Float ledOpentime, @Param("ledPower")Float ledPower, @Param("tenantId")Integer tenantId, @Param("preBrightness")Integer preBrightness,@Param("systemRunTime")String systemRunTime);
}
