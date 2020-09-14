package com.dhlk.web.light.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.Intensity;
import com.dhlk.entity.light.IntensityInfo;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.IntensityService;
import org.springframework.stereotype.Service;

/**
 * @author xkliu
 * @date 2020/6/30
 */
@Service
public class IntensityServiceFbk implements IntensityService {
    @Override
    public Result save(Intensity intensity) {
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
    public Result intensityContro(InfoBox<IntensityInfo> intensityInfo) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result memoryIntensity() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

}
