package com.dhlk.web.basicmodule.controller;

import com.dhlk.entity.basicmodule.DevicesAttrSet;
import com.dhlk.web.basicmodule.service.DevicesAttrSetService;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.dhlk.utils.ResultUtils;

import javax.validation.Valid;


/**
* @Description:    设备属性管理
* @Author:         gchen
* @CreateDate:     2020/3/31 11:10
*/
@RestController
@Api(value = "DevicesAttrSetController", description = "设备属性管理")
@RequestMapping(value = "/devicesAttrSet")
public class DevicesAttrSetController {

    @Autowired
    private DevicesAttrSetService devicesAttrSetService;
    /**
     * 新增/修改
     */
    @ApiOperation("新增/编辑")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody DevicesAttrSet devicesAttrSet, BindingResult bindingResult) {
        Result result = ResultUtils.loadResult(bindingResult);
        if(result == null){
            return devicesAttrSetService.save(devicesAttrSet);
        }
        return result;
    }
    /**
     * 删除
     * @param id
     * @return result
     */
    @ApiOperation("删除")
    @GetMapping(value = "/delete")
    public Result delete(@RequestParam(value = "id") Integer id) {
        return devicesAttrSetService.delete(id);
    }

    /**
     * 查询
     * @param name 属性集合名称
     */
    @ApiOperation("列表查询")
    @GetMapping("/findList")
    public Result findList(@RequestParam(value = "name", required = false) String name) {
        return  devicesAttrSetService.findList(name);
    }
}
