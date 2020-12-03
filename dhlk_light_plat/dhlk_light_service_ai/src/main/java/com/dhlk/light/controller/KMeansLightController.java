package com.dhlk.light.controller;

import com.dhlk.light.service.KMeansLightService;
import com.dhlk.light.util.Result;
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
public class KMeansLightController {
    @Autowired
    private KMeansLightService lightService;

    /**
     * 查询某个租户下聚类之后三类灯的信息
     *
     * @param factoryCode
     * @return
     */
    @CrossOrigin
    @GetMapping("/findList")
    public Result findList(@RequestParam("factoryCode") String factoryCode) {
        return lightService.findAll(factoryCode);
    }

    /**
     * 显示每类灯的占比
     *
     * @param factoryCode
     * @return
     */
    @CrossOrigin
    @GetMapping("/lightProportion")
    public Result lightroportion(@RequestParam("factoryCode") String factoryCode) {
        return lightService.lightProportion(factoryCode);
    }

    /**
     * 重点关注的灯的区域信息
     *
     * @param factoryCode
     * @return
     */
    @CrossOrigin
    @GetMapping("/focusSnAndArea")
    public Result focusSnAndArea(@RequestParam("factoryCode") String factoryCode) {
        return lightService.focusSnAndArea(factoryCode);
    }
}
