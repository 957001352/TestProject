package com.dhlk.light.dao;

import com.dhlk.light.entity.LightLocation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/26
 * Time:9:04
 * @Description:
 */
@Repository
public interface LightLocationDao {

    /**
     * 查询灯位置信息
     * @return
     */
    List<LightLocation> findAll(String tenantId,String areaId);

    /**
     * 通过sn查询位置信息
     * @param sn
     * @return
     */
    LightLocation selectBySn(String sn);

    /**
     * 通过租户id查询位置信息
     * @param tenantId
     * @return
     */
    List<LightLocation> selectLightByTenant(Integer tenantId);
}
