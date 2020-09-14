package com.dhlk.web.light.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Construction;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.ConstructionService;
import org.springframework.stereotype.Service;

/**
 * @author xkliu
 * @date 2020/6/3
 * <p>
 * 施工信息服务调用失败时的实现类
 */
@Service
public class ConstructionServiceFbk implements ConstructionService {

    @Override
    public Result save(Construction construction) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findList(String implPeople, String startDate, String endDate,Integer pageNum, Integer pageSize) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
