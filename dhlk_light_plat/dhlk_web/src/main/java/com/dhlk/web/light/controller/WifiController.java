package com.dhlk.web.light.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.LedWifi;
import com.dhlk.entity.light.SwitchWifiInfo;
import com.dhlk.entity.light.Wifi;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.WifiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author xkliu
 * @date 2020/6/30
 */
@RestController
@RequestMapping(value = "/wifi")
@Api(description = "WIFI管理", value = "WifiController")
public class WifiController {

    @Autowired
    private WifiService wifiService;

    /**
     * 新增/修改
     *
     * @param wifi
     * @param bindingResult
     * @return
     */
    @ApiOperation("新增/修改")
    @PostMapping("/save")
    public Result save(@Valid @RequestBody Wifi wifi, BindingResult bindingResult) {
        Result result = ResultUtils.loadResult(bindingResult);
        if (result == null) {
            return wifiService.save(wifi);
        }
        return result;
    }


    /**
     * 数据查询
     *
     * @param
     * @return
     */
    @ApiOperation("数据查询")
    @GetMapping("/find")
    public Result findOne() {
        return wifiService.findOne();
    }

    /**
     * 删除
     *
     * @param ids
     * @return result
     */
    @ApiOperation("物理删除")
    @GetMapping(value = "/delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return wifiService.delete(ids);
    }

    /**
     * wifi设置
     * @return
     */
    @PostMapping(value = "/wifiContro")
    public Result wifiContro(@RequestBody LedWifi ledWifi) {
        return wifiService.wifiContro(ledWifi);
    }

    /**
     * 开关wifi设置
     * @return
     */
    @PostMapping(value = "/switchWifiContro")
    public Result switchWifiContro(@RequestBody InfoBox<SwitchWifiInfo> infoBox) {
        return wifiService.switchWifiContro(infoBox);
    }
}
