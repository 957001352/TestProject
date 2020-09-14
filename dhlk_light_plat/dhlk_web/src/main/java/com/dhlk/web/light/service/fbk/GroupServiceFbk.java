package com.dhlk.web.light.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Switch;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.GroupService;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceFbk implements GroupService {
    @Override
    public Result save(Switch swich) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String id) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findList(String areaId) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
