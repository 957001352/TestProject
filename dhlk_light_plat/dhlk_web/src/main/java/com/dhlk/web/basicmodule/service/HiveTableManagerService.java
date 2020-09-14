package com.dhlk.web.basicmodule.service;

import com.dhlk.web.basicmodule.service.fbk.HiveTableManagerServiceFbk;
import com.dhlk.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * hive元数据表管理
 */
@FeignClient(value = "basicmodule-service/hiveTableManager", fallback = HiveTableManagerServiceFbk.class)
public interface HiveTableManagerService {
        /**
         * 创建表
         */
        @GetMapping(value = "/createTable")
        Result createTable(@RequestParam(value = "conver") Boolean conver);
        /**
         * 删除表
         */
        @GetMapping(value = "/dropTable")
        Result dropTable();

        /**
         * 查询所有表
         */
        @GetMapping("/findTableList")
        Result findTableList();

}
