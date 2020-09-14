package com.dhlk.web.basicmodule.service.fbk;

import com.dhlk.entity.basicmodule.NetFault;
import com.dhlk.web.basicmodule.service.NetFaultService;
import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import org.springframework.stereotype.Service;
import com.dhlk.utils.ResultUtils;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/4/20
 */
@Service
public class NetFaultServiceFbk implements NetFaultService {


    @Override
    public Result save(NetFault netFault) {
            return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result dealFault(Integer id, Integer status) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findList(String tbId,Integer status) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}