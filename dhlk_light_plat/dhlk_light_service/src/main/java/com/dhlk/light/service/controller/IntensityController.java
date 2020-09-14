package com.dhlk.light.service.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.Intensity;
import com.dhlk.entity.light.IntensityInfo;
import com.dhlk.entity.light.Wifi;
import com.dhlk.light.service.service.IntensityService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xkliu
 * @date 2020/6/30
 * <p>
 * 光感控制器
 */
@RestController
@RequestMapping(value = "/intensity")
@Api(description = "光感管理", value = "IntensityController")
public class IntensityController {

    @Autowired
    private IntensityService intensityService;

    /**
     * 保存
     *
     * @param intensity
     * @return
     */
    @PostMapping("/save")
    //@RequiresPermissions("intensity:save")
    public Result save(@RequestBody Intensity intensity) {
        return intensityService.save(intensity);
    }


    /**
     * 人感数据查询
     *
     * @param
     * @return
     */
    @GetMapping("/findOne")
   // @RequiresAuthentication
    public Result findOne() {
        return intensityService.findOne();
    }

    /**
     * 删除
     *
     * @param ids
     * @return result
     */
    @GetMapping(value = "/delete")
    //@RequiresAuthentication
    public Result delete(@RequestParam(value = "ids") String ids) {
        return intensityService.delete(ids);
    }

    /**
     * 光感控制
     * @return
     */
    @PostMapping(value = "/intensityContro")
//    @RequiresAuthentication
    public Result intensityContro(@RequestBody InfoBox<IntensityInfo> intensityInfo) {
        return intensityService.intensityContro(intensityInfo);
    }


    /**
     *记忆光感强度
     */
    @GetMapping(value = "/memoryIntensity")
    public Result memoryIntensity() {
        return intensityService.memoryIntensity();
    }

}

