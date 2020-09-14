package com.dhlk.web.light.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.BiProxyServerInfo;
import com.dhlk.entity.light.Computer;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.ComputerService;
import org.springframework.stereotype.Service;

@Service
public class ComputerServiceFbk implements ComputerService {

    @Override
    public Result save(Computer computer) {
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
    public Result addReseller(String biProxyServerInfo,String mac) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
