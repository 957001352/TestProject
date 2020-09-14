package com.dhlk.web.light.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.LightQueryService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/6/8
 */
@Service
public class LightQueryServiceFbk implements LightQueryService {

    @Override
    public Result ledIntallQuery(String province) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result provinceQuery(String province) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result lastCompanyQuery(String province, Integer limit) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result energyComRanking(String province, Integer limit) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result thriftCarbonEmission(String province) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}