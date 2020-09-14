package com.dhlk.web.light.controller;

import com.dhlk.domain.Result;
import com.dhlk.web.light.service.LightQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/6/8
 */
@RestController
@RequestMapping(value = "/lightQuery")
@Api(description = "统计汇总", value = "lightQuery")
public class LightQueryController {
    @Autowired
    private LightQueryService lightQueryService;
    /**
    * 照明设备安装情况查询
    * @param province 省份
    * @return
    */
    @ApiOperation("照明设备安装情况查询")
    @GetMapping(value = "/ledIntallQuery")
    public Result ledIntallQuery(@RequestParam(value = "province", required = false) String province) {
        return lightQueryService.ledIntallQuery(province);
    }
    /**
     * 按省份统计安灯查询
     * @param
     * @return
     */
    @ApiOperation("按省份统计安灯查询")
    @GetMapping(value = "/provinceQuery")
    public Result provinceQuery(@RequestParam(value = "province", required = false) String province) {
        return lightQueryService.provinceQuery(province);
    }
    /**
     * 最新购买企业查询
     * @param province 省份
     * @param province 默认显示前多少条
     * @return
     */
    @ApiOperation("最新购买企业查询")
    @GetMapping(value = "/lastCompanyQuery")
    public Result lastCompanyQuery(@RequestParam(value = "province", required = false) String province,
                                   @RequestParam(value = "limit", required = false,defaultValue = "10") Integer limit) {
        return lightQueryService.lastCompanyQuery(province,limit);
    }

    @ApiOperation("节能企业排行查询")
    @GetMapping(value = "/energyComRanking")
    public Result energyComRanking(@RequestParam(value = "province", required = false) String province,
                                   @RequestParam(value = "limit", required = false,defaultValue = "10") Integer limit) {
        return lightQueryService.energyComRanking(province,limit);
    }

    @ApiOperation("节约碳排放")
    @GetMapping(value = "/thriftCarbonEmission")
    public Result thriftCarbonEmission(@RequestParam(value = "province", required = false) String province){
        return lightQueryService.thriftCarbonEmission(province);
    }


}