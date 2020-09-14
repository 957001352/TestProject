package com.dhlk.web.basicmodule.service.fbk;

import com.dhlk.web.basicmodule.service.ProductNetService;
import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import org.springframework.stereotype.Service;
import com.dhlk.utils.ResultUtils;

/**
 * @Description 生产设备和网络设备关系配置
 * @Author lpsong
 * @Date 2020/4/7
 */
@Service
public class ProductNetServiceFbk implements ProductNetService {


    @Override
    public Result save(String netId, String productIds, Integer type) {
            return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findList(String name,Integer type) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findBiList(Integer netId) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findComputerList(Integer netId) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }


    @Override
    public Result findNotBiList() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findNotComputerList() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}