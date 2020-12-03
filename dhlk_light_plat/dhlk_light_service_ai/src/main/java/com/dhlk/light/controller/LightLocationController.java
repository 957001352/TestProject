package com.dhlk.light.controller;

import com.dhlk.light.service.LightLocationService;
import com.dhlk.light.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/26
 * Time:9:16
 * @Description:
 */
@RestController
@RequestMapping("/lightLocation")
public class LightLocationController {

    @Autowired
    private LightLocationService lightLocationService;

    /**
     * 根据区域id查询该区域下的灯，还有区域名
     *
     * @param tenantId 租户id，必须的参数
     * @param areaId   区域id，不传时查询该租户下所有的灯信息
     * @return
     */
    @CrossOrigin
    @GetMapping("/findLightLocationList")
    public Result findList(String tenantId, String areaId) {
        return lightLocationService.findAll(tenantId, areaId);
    }

    /**
     * 通过sn查询灯位置信息
     *
     * @param sn
     * @return
     */
    @CrossOrigin
    @GetMapping("/selectLocationBySn")
    public Result selectBySn(@RequestParam("sn") String sn) {
        return lightLocationService.selectBySn(sn);
    }

    /**
     * 通过租户id查询位置信息，该租户下所有的灯
     *
     * @param id
     * @return
     */
    @CrossOrigin
    @GetMapping("/selectLightByTenantId")
    public Result selectLightByTenantId(@RequestParam("id") Integer id) {
        return lightLocationService.selectByTenantId(id);
    }
}
