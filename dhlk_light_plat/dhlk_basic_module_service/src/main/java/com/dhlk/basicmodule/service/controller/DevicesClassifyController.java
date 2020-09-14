package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.DevicesClassifyService;
import com.dhlk.entity.basicmodule.DevicesClassify;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
    @PostMapping(value = "/save")
    @RequiresPermissions("devicesClassify:save")
    public Result save(@RequestBody DevicesClassify devicesClassify) {
        return devicesClassifyService.save(devicesClassify);
    }
    /**
     * 删除
     * @param id
     * @return result
     */
    @GetMapping(value = "/delete")
    @RequiresPermissions("devicesClassify:delete")
    public Result delete(@RequestParam(value = "id") String id) {
        return devicesClassifyService.delete(id);
    }

    /**
     * 查询
     * @param name 设备分配名称
     */
    @GetMapping("/findList")
    @RequiresPermissions("dhlk:view")
    public Result findList(@RequestParam(value = "name", required = false) String name) {
        return  devicesClassifyService.findList(name);
    }
}
