package com.dhlk.web.light.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Area;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.AreaService;
import org.springframework.stereotype.Service;

/**
 * @author xkliu
 * @date 2020/6/5
 * <p>
 * 施工区域服务调用失败时的实现类
 */
@Service
public class AreaServiceFbk implements AreaService {

    @Override
    public Result save(Area area) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findList() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findListByTenantId(Integer tenantId) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findAreaRepeat(String name) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result update(Area area) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
