package com.dhlk.web.light.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.SyncDataResult;
import com.dhlk.web.light.service.fbk.DataSyncServiceFbk;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xkliu
 * @date 2020/6/18
 */
@FeignClient(value = "light-service/dataSync", fallback = DataSyncServiceFbk.class)
public interface DataSyncService {

    /**
     * 数据同步
     * @param code
     * @return
     */
    @GetMapping("/sync")
    Result sync(@RequestParam(value = "code", required = false) String code);

    /**
     * 同步数据到本地
     * @return result
     */
    @PostMapping("/syncDataToLocal")
    Result syncDataToLocal(@RequestBody SyncDataResult syncDataResult);
}
