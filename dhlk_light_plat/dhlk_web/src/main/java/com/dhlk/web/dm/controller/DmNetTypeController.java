package com.dhlk.web.dm.controller;

import com.dhlk.entity.dm.DmNetType;
import com.dhlk.web.dm.service.DmNetTypeService;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* 网络设备类型
*/
@RestController
@Api(description = "网络设备分类")
@RequestMapping(value = "/dmNetType")
public class DmNetTypeController {
    @Autowired
    private DmNetTypeService dmNetTypeService;

    /**
     * 保存
     * @param
     * @return
     */
    @ApiOperation("新增/修改")
    @PostMapping(value = "/save")
    public Result save(@RequestBody DmNetType dmNetType) {
        return dmNetTypeService.save(dmNetType);
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @ApiOperation("删除")
    @GetMapping(value = "/delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return dmNetTypeService.delete(ids);
    }

    /**
    * 列表查询
    * @return
    */
    @ApiOperation("列表查询")
    @GetMapping("/findList")
    public Result findList(@RequestParam(value = "name", required = false) String name) {
        return  dmNetTypeService.findList(name);
    }
}
