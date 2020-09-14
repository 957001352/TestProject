package com.dhlk.web.dm.controller;

import com.dhlk.entity.dm.DmClassifyType;
import com.dhlk.web.dm.service.DmClassifyTypeService;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* 设备类型分类
*/
@RestController
@Api(description = "设备类型分类")
@RequestMapping(value = "/dmClassifyType")
public class DmClassifyTypeController {
    @Autowired
    private DmClassifyTypeService dmClassifyTypeService;

    /**
     * 保存
     * @param
     * @return
     */
    @ApiOperation("新增/修改")
    @PostMapping(value = "/save")
    public Result save(@RequestBody DmClassifyType dmClassifyType) {
        return dmClassifyTypeService.save(dmClassifyType);
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @ApiOperation("删除")
    @GetMapping(value = "/delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return dmClassifyTypeService.delete(ids);
    }

    /**
    * 列表查询
    * @return
    */
    @ApiOperation("列表查询")
    @GetMapping("/findList")
    public Result findList(@RequestParam(value = "name", required = false) String name) {
        return  dmClassifyTypeService.findList(name);
    }
}
