package com.dhlk.web.light.service;

import com.dhlk.domain.Result;
import com.dhlk.web.light.service.fbk.FaultCodeServiceFBK;
import com.dhlk.web.light.service.fbk.LedPowerStatisticsServiceFBK;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @创建人 wangq
 * @创建时间 2020/6/8
 * @描述
 */

@FeignClient(value = "light-service/ledPowerStatistics", fallback = LedPowerStatisticsServiceFBK.class)
public interface LedPowerStatisticsService {


    /**
     * 能耗查询
     * @param endDate
     * @param area
     * @param led_switch
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/findList")
    Result findList(
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "area", required = false) String area,
            @RequestParam(value = "led_switch", required = false) String led_switch,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize);


    @GetMapping("/findLedRealInfo")
    Result findRealEnergyByLedId( @RequestParam(value = "ledId", required = false) String ledId);

    /**
     * 查询人感触发次数
     * @param areaId
     * @param date
     * @return
     */
    @GetMapping("/findPeopleFeelNumber")
    Result findPeopleFeelNumber( @RequestParam(value = "areaId", required = true)String areaId,
                                 @RequestParam(value = "date", required = true)String date);


    @GetMapping("/exportEnergyStatistics")
    Result exportEnergyStatistics(
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "area", required = false) String area,
            @RequestParam(value = "led_switch", required = false) String led_switch,
            @RequestParam(value = "tenantId", required = true) Integer tenantId
    );
}