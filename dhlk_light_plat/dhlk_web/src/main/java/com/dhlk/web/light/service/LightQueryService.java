package com.dhlk.web.light.service;

import com.dhlk.domain.Result;
import com.dhlk.web.light.service.fbk.LedServiceFbk;
import com.dhlk.web.light.service.fbk.LightQueryServiceFbk;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/6/8
 */
@FeignClient(value = "light-service/lightQuery", fallback = LightQueryServiceFbk.class)
public interface LightQueryService {

    @GetMapping(value = "/ledIntallQuery")
    Result ledIntallQuery(@RequestParam(value = "province", required = false) String province);
    /**
     * 按省份统计安灯查询
     * @param
     * @return
     */
    @GetMapping(value = "/provinceQuery")
    Result provinceQuery(@RequestParam(value = "province", required = false) String province);
    /**
     * 最新购买企业查询
     * @param province 省份
     * @param province 默认显示前多少条
     * @return
     */
    @GetMapping(value = "/lastCompanyQuery")
    Result lastCompanyQuery(@RequestParam(value = "province", required = false) String province,
                            @RequestParam(value = "limit", required = false,defaultValue = "10") Integer limit);

    /**
     * 节能企业排行
     * @param province
     * @param limit
     * @return
     */
    @GetMapping(value = "/energyComRanking")
    Result energyComRanking(@RequestParam(value = "province", required = false) String province,
                            @RequestParam(value = "limit", required = false,defaultValue = "10") Integer limit);

    @GetMapping(value = "/thriftCarbonEmission")
    Result thriftCarbonEmission(@RequestParam(value = "province", required = false) String province);
}
