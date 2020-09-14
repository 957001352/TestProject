package com.dhlk.web.light.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.Intensity;
import com.dhlk.entity.light.IntensityInfo;
import com.dhlk.web.light.service.fbk.IntensityServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xkliu
 * @date 2020/6/30
 */
@FeignClient(value = "light-service/intensity", fallback = IntensityServiceFbk.class)
public interface IntensityService {

    /**
     * 保存
     *
     * @param intensity
     * @return
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody Intensity intensity);

    /**
     * 查询一条
     */
    @GetMapping("/findOne")
    Result findOne();

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    Result delete(@RequestParam(value = "ids") String ids);

    /**
     * 光感控制
     * @return
     */
    @PostMapping(value = "/intensityContro")
//    @RequiresAuthentication
    Result intensityContro(@RequestBody InfoBox<IntensityInfo> intensityInfo);

    @GetMapping(value = "/memoryIntensity")
    Result memoryIntensity();
}


