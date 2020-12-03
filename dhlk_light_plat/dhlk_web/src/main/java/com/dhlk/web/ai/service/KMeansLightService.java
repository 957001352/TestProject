package com.dhlk.web.ai.service;


import com.dhlk.domain.Result;
import com.dhlk.web.ai.service.fbk.KMeansLightServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/23
 * Time:10:18
 * @Description:
 */
@FeignClient(value = "light-service-ai/kmlightdata", fallback = KMeansLightServiceFbk.class)
public interface KMeansLightService {

    @GetMapping("/findList")
    Result findAll(@RequestParam("factoryCode") String factoryCode);

    @GetMapping("/factoryCodeList")
    Result selectFactoryCode();

    @GetMapping("/lightProportion")
    Result lightProportion(@RequestParam("factoryCode") String factoryCode);
}
