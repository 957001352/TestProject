package com.dhlk.web.light.service;


import com.dhlk.domain.Result;
import com.dhlk.web.light.service.fbk.FaultCodeServiceFBK;
import com.dhlk.web.light.service.fbk.GraphicStatisticsServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 图表统计
 */
@FeignClient(value = "light-service/graphicStatistics", fallback = GraphicStatisticsServiceFbk.class)
public interface GraphicStatisticsService {



   /* @GetMapping("/getLightEnergyAndlightCount")
    public Result getLightEnergyAndlightCount();*/

    /**
     * 今日与昨日能耗对比接口
     * @return
     */
    @GetMapping("/getTodayEnergyPKYesterdayEnergy")
    public Result getTodayEnergyPKYesterdayEnergy();

    /**
     * 获取当月每天消耗的能耗
     * @param date
     * @return
     */
    @GetMapping("/getEveryDayEnergyByCurrentMonth")
    public Result getEveryDayEnergyByCurrentMonth(@RequestParam(value = "date",required = true) String date);

    /**
     * 获取系统运行时长
     */
    @GetMapping("/getSystemRunTime")
    public Result getSystemRunTime();

    /**
     * 获取今日、昨日消耗能耗
     * @return
     */

    @GetMapping("/getTodayEnergyAndYesdayEnergy")
    public Result getTodayEnergyAndYesdayEnergy();

    /**
     * 获取灯总能耗
     */
    @GetMapping("/getEnergyTotal")
    public Result getEnergyTotal();

    /**
     * 获取总灯数和在线数
     */

    @GetMapping("/getLightTotalAndOnlineCount")
    public Result getLightTotalAndOnlineCount();

}
