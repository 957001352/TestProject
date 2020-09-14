package com.dhlk.web.basicmodule.controller;

import com.dhlk.entity.basicmodule.DevicesClassify;
import com.dhlk.web.basicmodule.service.DevicesClassifyService;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* @Description:    设备分类管理
* @Author:         gchen
* @CreateDate:     2020/3/31 11:10
*/
@RestController
@Api(value = "DevicesClassifyController", description = "设备分类管理")
@RequestMapping(value = "/devicesClassify")
public class DevicesClassifyController {

    @Autowired
    private DevicesClassifyService devicesClassifyService;
    /**
     * 新增/修改
     */
    @ApiOperation("新增/修改")
    @PostMapping(value = "/save")
    public Result save(@RequestBody DevicesClassify devicesClassify) {
        return devicesClassifyService.save(devicesClassify);
    }
    /**
     * 删除
     * @param id
     * @return result
     */
    @ApiOperation("删除")
    @GetMapping(value = "/delete")
    public Result delete(@RequestParam(value = "id") String id) {
        return devicesClassifyService.delete(id);
    }

    /**
     * 查询
     * @param name 设备分配名称
     */
    @ApiOperation("列表查询")
    @GetMapping("/findList")
    public Result findList(@RequestParam(value = "name", required = false) String name) {
        return  devicesClassifyService.findList(name);
    }
}
