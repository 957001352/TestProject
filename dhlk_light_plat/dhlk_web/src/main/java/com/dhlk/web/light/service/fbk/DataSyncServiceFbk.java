package com.dhlk.web.light.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.SyncDataResult;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.DataSyncService;
import org.springframework.stereotype.Service;

/**
 * @author xkliu
 * @date 2020/6/18
 */
@Service
public class DataSyncServiceFbk implements DataSyncService {

    @Override
    public Result sync(String code) {
        return  ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result syncDataToLocal(SyncDataResult syncDataResult) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
