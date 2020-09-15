package com.dhlk.light.factory.dao;

import org.apache.ibatis.annotations.Param;
import org.hibernate.validator.constraints.EAN;
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


    public BigDecimal getTodayEnergy();


    public BigDecimal getYesterdayEnergy();

    public BigDecimal getEnergyTotal();

    public Integer getLightTotal();

    public List<String> getAllLightSn();

    /**
     * 获取今日按小时统计消耗的能耗
     * @return
     */
    public List<LinkedHashMap<String,Object>> getTodayEnergyByHourGroup();

    /**
     * 获取昨日按小时统计消耗的能耗
     * @return
     */
    public List<LinkedHashMap<String,Object>> getYesterdayEnergyByHourGroup();

    /**
     * 获取当月每天消耗的能耗
     */

    public List<LinkedHashMap<String,Object>> getEveryDayEnergyByCurrentMonth(@Param(value = "date") String date);

    /**
     * 查询原始功率
     * @return
     */
    public Float selectPower();

    /**
     * 灯在线时长（单位，小时）
     * @param ledSn
     * @return
     */
    public Float selectLightOnline(@Param("ledSn") String ledSn);


    public Float searchTotalEnergy();

    public float selectTotalOnlineTime();

}
