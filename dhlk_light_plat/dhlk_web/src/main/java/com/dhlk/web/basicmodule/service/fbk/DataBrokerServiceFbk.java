package com.dhlk.web.basicmodule.service.fbk;

import com.dhlk.entity.basicmodule.DataBroker;
import com.dhlk.web.basicmodule.service.DataBrokerService;
import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import org.springframework.stereotype.Service;
import com.dhlk.utils.ResultUtils;

/**
 * 生产设备管理
 **/
@Service
public class DataBrokerServiceFbk implements DataBrokerService {


    @Override
    public Result save(DataBroker dataBroker) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findPageList(Integer pageNum, Integer pageSize) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}