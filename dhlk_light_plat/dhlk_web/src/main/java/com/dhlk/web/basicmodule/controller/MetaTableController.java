package com.dhlk.web.basicmodule.controller;

import com.dhlk.entity.hive.MetaTable;
import com.dhlk.web.basicmodule.service.MetaTableService;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* hive元数据表管理
*/
@Api(description = "hive元数据表管理")
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
    @ApiOperation("新增/修改")
    @PostMapping(value = "/save")
    public Result save(@RequestBody MetaTable metaTable) {
        return metaTableService.save(metaTable);
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @ApiOperation("删除")
    @GetMapping(value = "/delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return metaTableService.delete(ids);
    }

    /**
    * 列表查询
     * @param pageNum
     * @param pageSize
    * @return
    */
    @ApiOperation("分页查询")
    @GetMapping("/findPageList")
    public Result findPageList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return  metaTableService.findPageList(pageNum, pageSize);
    }
}
