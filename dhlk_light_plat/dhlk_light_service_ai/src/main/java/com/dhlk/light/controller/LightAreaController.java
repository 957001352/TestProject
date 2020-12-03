package com.dhlk.light.controller;

import com.dhlk.light.service.LightAreaService;
import com.dhlk.light.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/26
 * Time:15:10
 * @Description:
 */
@RestController
@RequestMapping("/lightArea")
public class LightAreaController {

    @Autowired
    private LightAreaService lightAreaService;

    /**
     * 同过租户id查询位置信息
     *
     * @param id
     * @return
     */
    @CrossOrigin
    @GetMapping("/selectLightAreaByTenantId")
    public Result selectLightAreaByTenantId(@RequestParam("id") Integer id) {
        return lightAreaService.selectAreaByTenantId(id);
    }
}
