package com.dhlk.light.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.*;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xkliu
 * @date 2020/6/30
 */
public interface WifiService {
    /**
     * 保存
     *
     * @param
     * @return
     */
    Result save(@RequestBody Wifi wifi);

    /**
     * 单条查询
     */
    Result findOne();

    /**
     * 物理删除
     *
     * @param ids
     * @return
     */
    Result delete(String ids);

    /**
     * 灯wifi设置
     */
    Result wifiContro(LedWifi ledWifi);

    /**
     * 开关wifi设置
     */
    Result switchWifiContro(InfoBox<SwitchWifiInfo> infoBox);
}
