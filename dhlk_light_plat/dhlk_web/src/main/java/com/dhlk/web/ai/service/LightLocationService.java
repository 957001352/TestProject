package com.dhlk.web.ai.service;


import com.dhlk.domain.Result;
import com.dhlk.web.ai.service.fbk.KMeansLightServiceFbk;
import com.dhlk.web.ai.service.fbk.LightLocationServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/13
 * Time:11:04
 * @Description:
 */
@FeignClient(value = "light-service-ai/lightLocation", fallback = LightLocationServiceFbk.class)
public interface LightLocationService {

    @GetMapping("/findLightLocationList")
    Result findAll(@RequestParam("tenantId") String tenantId, @RequestParam("area") String area);

    @GetMapping("/selectLocationBySn")
    Result selectBySn(@RequestParam("sn")String sn);

    @GetMapping("/selectLightByTenantId")
    Result selectByTenantId(@RequestParam("tenantId")Integer tenantId);
}
