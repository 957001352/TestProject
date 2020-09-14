package com.dhlk.web.dm.service.fbk;

import com.dhlk.entity.dm.DmClassifyType;
import com.dhlk.web.dm.service.DmClassifyTypeService;
import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import org.springframework.stereotype.Service;
import com.dhlk.utils.ResultUtils;

/**
 * 生产设备管理
 **/
@Service
public class DmClassifyTypeServiceFbk implements DmClassifyTypeService {


    @Override
    public Result save(DmClassifyType dmClassifyType) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findList(String name) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}