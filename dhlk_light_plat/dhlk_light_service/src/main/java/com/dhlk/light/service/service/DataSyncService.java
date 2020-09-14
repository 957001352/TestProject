package com.dhlk.light.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.SyncDataResult;

/**
 * @author xkliu
 * @date 2020/6/18
 */
public interface DataSyncService {

    /**
     *
     * @param code
     * @return
     */
    Result sync(String code);


    /**
     * 同步数据到本地
     * @param syncDataResult
     */
    Result syncDataToLocal(SyncDataResult syncDataResult);
}
