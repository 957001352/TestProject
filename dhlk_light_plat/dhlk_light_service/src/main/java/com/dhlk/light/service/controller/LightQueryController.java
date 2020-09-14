package com.dhlk.light.service.controller;

import com.dhlk.domain.Result;
import com.dhlk.light.service.service.LightQueryService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LightQueryController {
    @Autowired
    private LightQueryService lightQueryService;
    /**
    * 照明设备安装情况查询
    * @param province 省份
    * @return
    */
    @GetMapping(value = "/ledIntallQuery")
    @RequiresAuthentication
    public Result ledIntallQuery(@RequestParam(value = "province", required = false) String province) {
        return lightQueryService.ledIntallQuery(province);
    }
    /**
     * 按省份统计安灯查询
     * @param
     * @return
     */
    @GetMapping(value = "/provinceQuery")
    @RequiresAuthentication
    public Result provinceQuery(@RequestParam(value = "province", required = false) String province) {
        return lightQueryService.provinceQuery(province);
    }
    /**
     * 最新购买企业查询
     * @param province 省份
     * @param province 默认显示前多少条
     * @return
     */
    @GetMapping(value = "/lastCompanyQuery")
    @RequiresAuthentication
    public Result lastCompanyQuery(@RequestParam(value = "province", required = false) String province,
                                   @RequestParam(value = "limit", required = false,defaultValue = "10") Integer limit) {
        return lightQueryService.lastCompanyQuery(province,limit);
    }

    /**
     * 节能公司排行
     * @param province 省份
     * @param limit  默认显示前10条
     * @return
     */
    @GetMapping(value = "/energyComRanking")
    @RequiresAuthentication
    public Result energyComRanking(@RequestParam(value = "province", required = false) String province,
                                   @RequestParam(value = "limit", required = false,defaultValue = "10") Integer limit){
        return lightQueryService.energyComRanking(province,limit);
    }

    /**
     * 节约碳排放
     * @return
     */
    @GetMapping(value = "/thriftCarbonEmission")
    public Result thriftCarbonEmission(@RequestParam(value = "province", required = false) String province){
        return lightQueryService.thriftCarbonEmission(province);
    }
}