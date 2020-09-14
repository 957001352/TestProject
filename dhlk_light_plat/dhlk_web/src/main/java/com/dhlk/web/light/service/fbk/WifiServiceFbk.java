package com.dhlk.web.light.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.LedWifi;
import com.dhlk.entity.light.SwitchWifiInfo;
import com.dhlk.entity.light.Wifi;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.WifiService;
import org.springframework.stereotype.Service;

/**
 * @author xkliu
 * @date 2020/6/30
 */
@Service
public class WifiServiceFbk implements WifiService {
    @Override
    public Result save(Wifi wifi) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findOne() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);

    }

    @Override
    public Result delete(String ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result wifiContro(LedWifi ledWifi) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result switchWifiContro(InfoBox<SwitchWifiInfo> infoBox) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

}
