package com.dhlk.light.controller;

import com.dhlk.light.service.LightTenantService;
import com.dhlk.light.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/25
 * Time:14:26
 * @Description:
 */
@RestController
@RequestMapping("/dhlkTenantList")
public class LightTenantController {

    @Autowired
    private LightTenantService dhlkLightTenantService;

    /**
     * 查询所有的租户
     *
     * @return List<LightTenant>
     */
    @CrossOrigin
    @GetMapping("/findTenantList")
    public Result findList() {
        return dhlkLightTenantService.findAll();
    }
}
