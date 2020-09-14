package com.dhlk.web.basicmodule.service.fbk;

import com.dhlk.web.basicmodule.service.ProductDevicesService;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.ProductDevices;
import com.dhlk.enums.ResultEnum;
import org.springframework.stereotype.Service;
import com.dhlk.utils.ResultUtils;

/**
 * 生产设备管理
 **/
@Service
public class ProductDevicesServiceFbk implements ProductDevicesService {


    @Override
    public Result save(ProductDevices productDevices) throws Exception{
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String ids)throws Exception {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findList(String name) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findAttrByClassifyById(String classifyId) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findTreeList() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findOnLineDevicesCount(String name) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}