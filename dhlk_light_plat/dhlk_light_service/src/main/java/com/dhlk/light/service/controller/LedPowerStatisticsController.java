package com.dhlk.light.service.controller;/**
 * @创建人 wangq
 * @创建时间 2020/6/8
 * @描述
 */

import com.dhlk.domain.Result;
import com.dhlk.light.service.service.LedPowerStatisticsService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @program: dhlk.multi.tenant
 *
 * @description: 能耗查询控制器
 *
 * @author: wqiang
 *
 * @create: 2020-06-08 15:55
 **/

@RestController
@RequestMapping(value = "/ledPowerStatistics")
@Api(value = "LedPowerStatisticsController", description = "能耗统计")
public class LedPowerStatisticsController {

    @Autowired
    LedPowerStatisticsService ledPowerStatisticsService;

    @GetMapping("/findList")
    @RequiresAuthentication
    public Result findList(@RequestParam(value = "startDate", required = false) String startDate,
                           @RequestParam(value = "endDate", required = false) String endDate,
                           @RequestParam(value = "area", required = false) String area,
                           @RequestParam(value = "led_switch", required = false) String led_switch,
                           @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        return ledPowerStatisticsService.findList(startDate,endDate,area,led_switch,pageNum,pageSize);
    }

    @GetMapping("/findLedRealInfo")
    @RequiresAuthentication
    public Result findRealEnergyByledId(@RequestParam String ledId){
        return ledPowerStatisticsService.findRealEnergyByledId(ledId);
    }

    @GetMapping("/findPeopleFeelNumber")
    //@RequiresAuthentication
    public Result findPeopleFeelNumber(@RequestParam(value = "areaId", required = true) String areaId,
                                       @RequestParam(value = "date", required = true) String date){
        return ledPowerStatisticsService.findPeopleFeelNumber(areaId,date);
    }

    @GetMapping("/exportEnergyStatistics")
    public Result exportEnergyStatistics(@RequestParam(value = "startDate", required = false) String startDate,
                                                                      @RequestParam(value = "endDate", required = false) String endDate,
                                                                      @RequestParam(value = "area", required = false) String area,
                                                                      @RequestParam(value = "led_switch", required = false) String led_switch,
                                                                      @RequestParam(value = "tenantId", required = true) Integer tenantId
    ){
        return ledPowerStatisticsService.exportEnergyStatistics(startDate,endDate,area,led_switch,tenantId);
    }
}
