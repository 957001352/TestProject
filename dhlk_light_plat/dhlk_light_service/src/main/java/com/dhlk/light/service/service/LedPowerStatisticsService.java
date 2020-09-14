package com.dhlk.light.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.LedPower;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @创建人 wangq
 * @创建时间 2020/6/8
 * @描述
 */

public interface LedPowerStatisticsService {

    /**
     * 获取能耗
     * @param startDate
     * @param endDate
     * @param area
     * @param led_switch
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result findList(String startDate, String endDate, String area, String led_switch, Integer pageNum, Integer pageSize);

    /**
     * 将能耗保存到数据库
     * @param ledPower
     */
    void save(LedPower ledPower);

    /**
     * 更具灯的id获取灯的实时信息（电压、电流、实时能耗）
     * @param ledId
     * @return
     */

    Result findRealEnergyByledId(String ledId);

    /**
     * 查询人感触发次数
     * @param areaId
     * @param date
     * @return
     */
    Result findPeopleFeelNumber(String areaId,String date);

    /**
     * 能源统计导出
     * @param startDate
     * @param endDate
     * @param area
     * @param led_switch
     */
    Result exportEnergyStatistics(String startDate, String endDate, String area, String led_switch,Integer tenantId);

}
