package com.dhlk.web.ai.controller;
import com.dhlk.domain.Result;
import com.dhlk.web.ai.service.LightLocationService;
import io.swagger.annotations.Api;
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
@Api(description = "租户下区域的灯", value = "LightLocationController")
public class LightLocationController {

    @Autowired
    private LightLocationService lightLocationService;


    @CrossOrigin
    @GetMapping("/findLightLocationList")
    public Result findList(String tenantId, String area) {
        return lightLocationService.findAll(tenantId, area);
    }

    @CrossOrigin
    @GetMapping("/selectLocationBySn")
    public Result selectBySn(@RequestParam("sn") String sn) {
        return lightLocationService.selectBySn(sn);
    }

    /**
     * 同过租户id查询位置信息
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
