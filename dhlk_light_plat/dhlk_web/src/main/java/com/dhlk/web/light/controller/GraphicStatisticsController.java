package com.dhlk.web.light.controller;

import com.dhlk.domain.Result;
import com.dhlk.web.light.service.GraphicStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: dhlk.light.plat
 * @description: 图表统计控制器
 * @author: wqiang
 * @create: 2020-07-28 16:16
 **/
@RestController
@RequestMapping(value = "/graphicStatistics")
@Api(description = "图表能源统计", value = "GraphicStatisticsController")
public class GraphicStatisticsController {
    @Autowired
    GraphicStatisticsService graphicStatisticsService;

   /* @ApiOperation("获取今日能耗、昨日能耗、总能耗、灯在线数、离线数")
    @GetMapping("/getLightEnergyAndlightCount")
    public Result getLightEnergyAndlightCount() {
        return graphicStatisticsService.getLightEnergyAndlightCount();
    }*/

    @ApiOperation("今日与昨日能耗对比接口")
    @GetMapping("/getTodayEnergyPKYesterdayEnergy")
    public Result getTodayEnergyPKYesterdayEnergy() {
        return graphicStatisticsService.getTodayEnergyPKYesterdayEnergy();
    }

    @ApiOperation("获取当月每天消耗的能耗")
    @GetMapping("/getEveryDayEnergyByCurrentMonth")
    public Result getEveryDayEnergyByCurrentMonth(@RequestParam(value = "date",required = true)String date) {
        return graphicStatisticsService.getEveryDayEnergyByCurrentMonth(date);
    }

    @ApiOperation("获取系统运行时长")
    @GetMapping("/getSystemRunTime")
    public Result getSystemRunTime() {
        return graphicStatisticsService.getSystemRunTime();
    }

    @ApiOperation("获取今日、昨日消耗能耗")
    @GetMapping("/getTodayEnergyAndYesdayEnergy")
    public Result getTodayEnergyAndYesdayEnergy(){
        return graphicStatisticsService.getTodayEnergyAndYesdayEnergy();
    }

    @ApiOperation("获取所有灯消耗的总能耗")
    @GetMapping("/getEnergyTotal")
    public Result getEnergyTotal(){
        return graphicStatisticsService.getEnergyTotal();
    }

    @ApiOperation("获取总灯数和在线数")
    @GetMapping("/getLightTotalAndOnlineCount")
    public Result getLightTotalAndOnlineCount(){
        return graphicStatisticsService.getLightTotalAndOnlineCount();
    }
}
