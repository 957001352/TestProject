package com.dhlk.web.light.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.SyncDataResult;
import com.dhlk.web.light.service.DataSyncService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xkliu
 * @date 2020/6/18
 */

@RestController
@RequestMapping(value = "/dataSync")
@Api(description = "云端数据同步接口", value = "DataSyncController")
public class DataSyncController {

    @Autowired
    private DataSyncService dataSyncService;

    /**
     * 数据同步
     * @return result
     */
    @ApiOperation("数据同步")
    @GetMapping("/sync")
    public Result sync(@RequestParam(value = "code", required = false) String code) {
        return  dataSyncService.sync(code);
    }


    /**
     * 同步数据到本地
     * @return result
     */
    @ApiOperation("数据同步")
    @PostMapping("/syncDataToLocal")
    public Result syncDataToLocal(@RequestBody SyncDataResult syncDataResult) {
        return  dataSyncService.syncDataToLocal(syncDataResult);
    }
}
