package com.dhlk.web.light.controller;/**
 * @创建人 wangq
 * @创建时间 2020/6/8
 * @描述
 */

import com.dhlk.domain.Result;
import com.dhlk.utils.ExcelUtil;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.LedPowerStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @program: dhlk.multi.tenant
 *
 * @description: 能耗查询Controller
 *
 * @author: wqiang
 *
 * @create: 2020-06-08 16:58
 **/

@RestController
@RequestMapping(value = "/ledPowerStatistics")
@Api(description = "能耗查询", value = "LedPowerStatisticsController")
public class LedPowerStatisticsController {

    @Autowired
    private LedPowerStatisticsService ledPowerStatisticsService;

    @GetMapping("/findList")
    @ApiOperation("能耗统计")
    public Result findList(@RequestParam(value = "startDate", required = false) String startDate,
                           @RequestParam(value = "endDate", required = false) String endDate,
                           @RequestParam(value = "area", required = false) String area,
                           @RequestParam(value = "led_switch", required = false) String led_switch,
                           @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        return ledPowerStatisticsService.findList(startDate,endDate,area,led_switch,pageNum,pageSize);
    }

    @GetMapping("/findRealEnergyByLedId")
    @ApiOperation("实时获取灯信息")
    public Result findRealEnergyByLedId(@RequestParam(value = "ledId", required = true) String ledId){
        return ledPowerStatisticsService.findRealEnergyByLedId(ledId);
    }

    @GetMapping("/findPeopleFeelNumber")
    @ApiOperation("人感触发次数")
    public Result findPeopleFeelNumber(@RequestParam(value = "areaId", required = true) String areaId,
                                       @RequestParam(value = "date", required = true) String date){
        return ledPowerStatisticsService.findPeopleFeelNumber(areaId,date);
    }

    @GetMapping("/exportEnergyStatistics")
    @ApiOperation("能耗统计导出")
    public void exportEnergyStatistics(HttpServletResponse response,
                                       @RequestParam(value = "startDate", required = false) String startDate,
                                       @RequestParam(value = "endDate", required = false) String endDate,
                                       @RequestParam(value = "area", required = false) String area,
                                       @RequestParam(value = "led_switch", required = false) String led_switch,
                                       @RequestParam(value = "tenantId", required = true) Integer tenantId
    ){
        try {
            List<LinkedHashMap<String, Object>> list = (List<LinkedHashMap<String, Object>>)ledPowerStatisticsService.exportEnergyStatistics(startDate, endDate, area, led_switch,tenantId).getData();
            ExcelUtil.exportExcel(response, Arrays.asList(new String[]{"行号","日期", "分区", "能耗(kW·h)"}), list, "能源统计");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
