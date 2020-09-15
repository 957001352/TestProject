package com.dhlk.light.service.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @program: dhlk.light.plat
 * @description: 图表统计Dao
 * @author: wqiang
 * @create: 2020-07-28 09:57
 **/

@Repository
public interface GraphicStatisticsDao {


    public BigDecimal getTodayEnergy(Integer tenantId);


    public BigDecimal getYesterdayEnergy(Integer tenantId);

    public BigDecimal getEnergyTotal(Integer tenantId);

    public Integer getLightTotal(Integer tenantId);

    /**
     * 获取租户下所有灯
     * @param tenantId
     * @return
     */
    public List<String> getAllLightSn(Integer tenantId);

    /**
     * 获取全国所有灯
     * @return
     */
    public  List<String> getAllLightSnNation();

    /**
     * 获取今日按小时统计消耗的能耗
     * @return
     */
    public List<LinkedHashMap<String,Object>> getTodayEnergyByHourGroup(Integer tenantId);

    /**
     * 获取昨日按小时统计消耗的能耗
     * @return
     */
    public List<LinkedHashMap<String,Object>> getYesterdayEnergyByHourGroup(Integer tenantId);

    /**
     * 获取当月每天消耗的能耗
     */

    public List<LinkedHashMap<String,Object>> getEveryDayEnergyByCurrentMonth(@Param(value = "date") String date,@Param(value = "tenantId") Integer tenantId);


    /**
     * 查询单租户原始功率
     * @return
     */
    public Float selectPower(@Param( value= "tenantId")  Integer tenantId);

    /**
     * 现在灯消耗的总能耗
     */

    public Float searchTotalEnergyBySn(@Param("ledSn") String ledSn);

    /**
     * 获取租户下所有灯消耗能耗
     * @param tenantId
     * @return
     */
    public Float searchTotalEnergyByTenantId(@Param( value= "tenantId")  Integer tenantId);


    /**
     * 获取租户下所有灯在线时长
     * @param tenantId
     * @return
     */
    public Float selectTotalOnlineTimeByTenantId(@Param( value= "tenantId")  Integer tenantId);
}
