package com.dhlk.web.light.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Switch;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.SwitchService;
import org.springframework.stereotype.Service;

@Service
public class SwitchServiceFbk implements SwitchService {
    @Override
    public Result save(Switch swich) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findList(String name, Integer pageNum, Integer pageSize) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findGroupList(Integer switchId) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
