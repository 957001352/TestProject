package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.MetaTableService;
import com.dhlk.entity.hive.MetaTable;
import com.dhlk.domain.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* hive元数据表管理
*/
@RestController
@RequestMapping(value = "/metaTable")
public class MetaTableController {
    @Autowired
    private MetaTableService metaTableService;

    /**
     * 保存
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    @RequiresPermissions("metaTable:save")
    public Result save(@RequestBody MetaTable metaTable) {
        return metaTableService.save(metaTable);
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @GetMapping(value = "/delete")
    @RequiresPermissions("metaTable:delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return metaTableService.delete(ids);
    }

    /**
    * 列表查询
     * @param pageNum
     * @param pageSize
    * @return
    */
    @GetMapping("/findPageList")
    @RequiresPermissions("dhlk:view")
    public Result findPageList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return  metaTableService.findPageList(pageNum, pageSize);
    }
}
