package com.dhlk.web.hive.service;

import com.dhlk.entity.hive.MetaTable;
import com.dhlk.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 生产设备管理
 **/
@FeignClient(value = "dhlk-hive/hive")
public interface HiveService {

    /**
     * hive数据表查询
     * @return
     */
    @GetMapping("/findTableList")
    Result findTableList();

    /**
     * createTable
     * @return
     */
    @PostMapping(value = "/createTable")
    Result createTable(@RequestBody List<MetaTable> tableList);

    /**
     * dropTable
     * @return
     */
    @PostMapping(value = "/dropTable")
    Result dropTable(@RequestBody List<MetaTable> tableList);
}
