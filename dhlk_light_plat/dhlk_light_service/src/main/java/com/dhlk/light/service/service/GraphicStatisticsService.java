package com.dhlk.light.service.service;


import com.dhlk.domain.Result;

import java.util.Map;

/**
 * 图表统计
 */
public interface GraphicStatisticsService {



    public Result getLightEnergyAndlightCount();

    /**
     * 今日与昨日能耗对比接口
     * @return
     */
    public Result getTodayEnergyPKYesterdayEnergy();

    /**
     * 获取当月每天消耗的能耗
     * @param date
     * @return
     */
    public Result getEveryDayEnergyByCurrentMonth(String date);

    /**
     * 获取系统运行时长
     */

    public Result getSystemRunTime();

    /**
     * 获取今日、昨日消耗能耗
     * @return
     */

    public Result getTodayEnergyAndYesdayEnergy();

    /**
     * 获取灯总能耗
     */
    public Result getEnergyTotal();

    /**
     * 获取总灯数和在线数
     */

    public Result getLightTotalAndOnlineCount();

    /**
     * 单租户节约的总能耗
     */

    public Map findOrEnergyAndTotalEnergyNow(Integer tenantId);

}
