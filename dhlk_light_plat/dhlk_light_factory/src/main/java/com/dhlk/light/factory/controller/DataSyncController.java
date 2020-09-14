package com.dhlk.light.factory.controller;

import com.alibaba.fastjson.JSON;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.User;
import com.dhlk.entity.light.SyncDataResult;
import com.dhlk.light.factory.service.DataSyncService;
import com.dhlk.systemconst.Const;
import com.dhlk.utils.ResultUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xkliu
 * @date 2020/6/15
 * <p>
 * 数据同步
 */
@RestController
@RequestMapping(value = "/dataSync")
public class DataSyncController {

    @Autowired
    private DataSyncService dataSyncService;
    @Autowired
    private HttpServletRequest request;

    @ApiOperation("数据同步")
    @GetMapping("/sync")
    @RequiresAuthentication
    public Result dataSync(@RequestParam(value = "code", required = false) String code) {
        String token = request.getHeader(Const.TOKEN_HEADER);
        return dataSyncService.dataSync(token,code);
    }


    @ApiOperation("测试数据同步")
    @PostMapping("/syncData")
    public Result syncData(@RequestBody SyncDataResult<String> syncDataResult) {
//        SyncDataResult<String> sss = new SyncDataResult<>();
//        sss.setData(JSON.toJSONString(syncDataResult.getData()));
//        sss.setOperate(syncDataResult.getOperate());
//        sss.setDataId(syncDataResult.getDataId());
//        sss.setId(syncDataResult.getId());
//        sss.setTableName(syncDataResult.getTableName());
//        sss.setTenantId(syncDataResult.getTenantId());
//        sss.setTs(syncDataResult.getTs());
        return ResultUtils.success(dataSyncService.syncData(syncDataResult));
    }


}
