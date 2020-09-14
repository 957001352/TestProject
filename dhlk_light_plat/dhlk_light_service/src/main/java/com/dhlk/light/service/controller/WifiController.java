package com.dhlk.light.service.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.LedWifi;
import com.dhlk.entity.light.SwitchWifiInfo;
import com.dhlk.entity.light.Wifi;
import com.dhlk.light.service.service.WifiService;
import com.dhlk.light.service.util.LightDeviceUtil;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xkliu
 * @date 2020/6/30
 */
@RestController
@RequestMapping(value = "/wifi")
@Api(description = "WIFI管理", value = "PeopleFeelController")
public class WifiController {

    @Autowired
    private WifiService wifiService;
    /**
     * 保存
     *
     * @param Wifi
     * @return
     */
    @PostMapping("/save")
    //@RequiresPermissions("wifi:save")
    public Result save(@RequestBody Wifi Wifi) {
        return wifiService.save(Wifi);
    }


    /**
     * 人感数据查询
     *
     * @param
     * @return
     */
    @GetMapping("/findOne")
    //@RequiresAuthentication
    public Result findOne() {
        return wifiService.findOne();
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
        return wifiService.delete(ids);
    }

    /**
     * 灯wifi设置
     * @return
     */
    @PostMapping(value = "/wifiContro")
    @RequiresAuthentication
    public Result wifiContro(@RequestBody LedWifi ledWifi) {
        return wifiService.wifiContro(ledWifi);
    }

    /**
     * 开关wifi设置
     * @return
     */
    @PostMapping(value = "/switchWifiContro")
    @RequiresAuthentication
    public Result switchWifiContro(@RequestBody InfoBox<SwitchWifiInfo> infoBox) {
        return wifiService.switchWifiContro(infoBox);
    }

}
