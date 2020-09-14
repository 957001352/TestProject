package com.dhlk.web.light.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.LedWifi;
import com.dhlk.entity.light.SwitchWifiInfo;
import com.dhlk.entity.light.Wifi;
import com.dhlk.web.light.service.fbk.WifiServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xkliu
 * @date 2020/6/30
 */
@FeignClient(value = "light-service/wifi", fallback = WifiServiceFbk.class)
public interface WifiService {

    /**
     * 保存
     *
     * @param wifi
     * @return
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody Wifi wifi);

    /**
     * 查询一条
     */
    @GetMapping("/findOne")
    Result findOne();

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    Result delete(@RequestParam(value = "ids") String ids);

    /**
     * wifi设置
     * @return
     */
    @PostMapping(value = "/wifiContro")
    Result wifiContro(@RequestBody LedWifi ledWifi);

    /**
     * 开关wifi设置
     * @return
     */
    @PostMapping(value = "/switchWifiContro")
    Result switchWifiContro(@RequestBody InfoBox<SwitchWifiInfo> infoBox);
}
