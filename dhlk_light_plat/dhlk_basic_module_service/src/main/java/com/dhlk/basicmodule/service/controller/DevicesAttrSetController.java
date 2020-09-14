package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.DevicesAttrSetService;
import com.dhlk.entity.basicmodule.DevicesAttrSet;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    @PostMapping(value = "/save")
    @RequiresPermissions("devicesAttrSet:save")
    public Result save(@RequestBody DevicesAttrSet devicesAttrSet) {
        return devicesAttrSetService.save(devicesAttrSet);
    }
    /**
     * 删除
     * @param id
     * @return result
     */
    @GetMapping(value = "/delete")
    @RequiresPermissions("devicesAttrSet:delete")
    public Result delete(@RequestParam(value = "id") Integer id) {
        return devicesAttrSetService.delete(id);
    }

    /**
     * 查询
     * @param name 属性集合名称
     */
    @GetMapping("/findList")
    @RequiresPermissions("dhlk:view")
    public Result findList(@RequestParam(value = "name", required = false) String name) {
        return  devicesAttrSetService.findList(name);
    }
}
