package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.HiveMetaService;
import com.dhlk.domain.Result;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* hive元数据查询
*/
@RestController
@RequestMapping(value = "/hiveMeta")
public class HiveMetaController {
    @Autowired
    private HiveMetaService hiveMetaService;


    /**
    * hive表数据查询
     * @param pageNum
     * @param pageSize
    * @return
    */
    @GetMapping("/findPageList")
    @RequiresAuthentication
    public Result findPageList(@RequestParam(value = "table", required = false) String table,
                               @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return  hiveMetaService.findPageList(table,pageNum, pageSize);
    }
    /**
    * hive数据表头查询
     * @param table
    * @return
    */
    @GetMapping("/findColumnList")
    @RequiresAuthentication
    public Result findColumnList(@RequestParam(value = "table", required = false) String table) {
        return  hiveMetaService.findColumnList(table);
    }
}
