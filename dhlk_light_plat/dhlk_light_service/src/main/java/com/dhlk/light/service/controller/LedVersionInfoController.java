package com.dhlk.light.service.controller;

import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.LedVersionInfo;
import com.dhlk.light.service.service.LedVersionInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.dhlk.domain.Result;

/**
 * @program: dhlk.light.plat
 *
 * @description: 灯版本信息控制器
 *
 * @author: wqiang
 *
 * @create: 2020-06-30 11:27
 **/
@RestController
@RequestMapping(value = "/ledVersionInfo")
@Api(value = "LedVersionInfoController", description = "灯版本信息")
public class LedVersionInfoController {

    @Autowired
    LedVersionInfoService ledVersionInfoService;

    @PostMapping(value = "/save")
    public Result save(@RequestBody LedVersionInfo versionInfo) {
        return ledVersionInfoService.save(versionInfo);
    }

    @GetMapping(value = "/delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return ledVersionInfoService.delete(ids);
    }


    @PostMapping(value = "/findList")
    public Result findList(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "creator", required = false) String creator,
            @RequestParam(value = "createTime", required = false) String createTime,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return ledVersionInfoService.findList(name,creator,createTime,pageNum,pageSize);
    }
    /**
     * 固件升级
     */
    @PostMapping(value = "/firmwareUpgrade")
    public Result firmwareUpgrade(@RequestBody InfoBox<LedVersionInfo> infoBox) {
        return ledVersionInfoService.firmwareUpgrade(infoBox);
    }
}
