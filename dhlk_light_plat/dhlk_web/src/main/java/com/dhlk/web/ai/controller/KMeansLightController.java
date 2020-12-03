package com.dhlk.web.ai.controller;

import com.dhlk.domain.Result;
import com.dhlk.web.ai.service.KMeansLightService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/23
 * Time:10:20
 * @Description:
 */
@RestController
@RequestMapping("/kmlightdata")
@Api(description = "kmeans聚类", value = "KMeansLightController")
public class KMeansLightController {
    @Autowired
    private KMeansLightService lightService;

    @CrossOrigin
    @GetMapping("/findList")
    public Result findList(@RequestParam("factoryCode") String factoryCode) {
        return lightService.findAll(factoryCode);
    }

    @CrossOrigin
    @GetMapping("/factoryCodeList")
    public Result factoryCodeList(){
        return lightService.selectFactoryCode();
    }

    @CrossOrigin
    @GetMapping("/lightProportion")
    public Result lightroportion(@RequestParam("factoryCode") String factoryCode) {
        return lightService.lightProportion(factoryCode);
    }
}
