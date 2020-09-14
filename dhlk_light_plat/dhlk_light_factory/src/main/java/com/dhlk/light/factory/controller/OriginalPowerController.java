package com.dhlk.light.factory.controller;

import com.dhlk.domain.Result;
import com.dhlk.light.factory.service.OriginalPowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 本地灯亮度设置
 */
@RestController
@RequestMapping(value = "/originalPower")
public class OriginalPowerController {

    @Autowired
    private OriginalPowerService originalPowerService;


    /**
     * 亮度设置
     *
     * @return
     */
    @GetMapping("/preBrightness")
    public Result preBrightness(@RequestParam(value = "preBrightness") Integer preBrightness) {
        return originalPowerService.preBrightness(preBrightness);
    }


}
