package com.dhlk.light.service.dao;

import com.dhlk.entity.light.CloudPeopleFeelStatistics;
import com.dhlk.entity.light.FaultCode;
import com.dhlk.entity.light.LedPower;
import com.dhlk.entity.light.LedPowerStatistics;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @创建人 wangq
 * @创建时间 2020/6/8
 * @描述
 */

@Repository
public interface LedPowerStatisticsDao {


    List<LedPowerStatistics> findList(@Param("startDate") String startDate,
                                      @Param("endDate") String endDate,
                                      @Param("area") String area,
                                      @Param("led_switch") String led_switch,
                                      @Param("tenantId") int tenantId
    );

    /**
     * 新增能耗统计
     * @param ledPower
     * @return
     */
    Integer insert(LedPower ledPower);

    /**
     * 查询人感触发次数
     * @param areaId
     * @param date
     * @return
     */
     List<CloudPeopleFeelStatistics> findPeopleFeelNumber(@Param("areaId") String areaId,
                                                          @Param("date") String date,
                                                          @Param("tenantId") Integer tenantId
     );


    /**
     * 导出统计
     * @param startDate
     * @param endDate
     * @param area
     * @param led_switch
     * @param tenantId
     * @return
     */

    List<LinkedHashMap<String,Object>> exportEnergyStatistics(@Param("startDate") String startDate,
                                               @Param("endDate") String endDate,
                                               @Param("area") String area,
                                               @Param("led_switch") String led_switch,
                                               @Param("tenantId") Integer tenantId
    );

}
