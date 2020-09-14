package com.dhlk.web.light.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.OriginalPower;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.OriginalPowerService;
import org.springframework.stereotype.Service;

/**
 * @author xkliu
 * @date 2020/6/10
 * <p>
 * 企业历史照明功率维护服务调用失败时的实现类
 */
@Service
public class OriginalPowerServiceFbk implements OriginalPowerService {
    @Override
    public Result save(OriginalPower originalPower) {
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
    public Result preBrightness(Integer preBrightness) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
