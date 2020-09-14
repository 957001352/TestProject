package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.DmNetTypeService;
import com.dhlk.entity.dm.DmNetType;
import com.dhlk.domain.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* 网络设备类型
*/
@RestController
@RequestMapping(value = "/dmNetType")
public class DmNetTypeController {
    @Autowired
    private DmNetTypeService dmNetTypeService;

    /**
     * 保存
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    @RequiresPermissions("dmNetType:save")
    public Result save(@RequestBody DmNetType dmNetType) {
        return dmNetTypeService.save(dmNetType);
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @GetMapping(value = "/delete")
    @RequiresPermissions("dmNetType:delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return dmNetTypeService.delete(ids);
    }

    /**
    * 列表查询
    * @return
    */
    @GetMapping("/findList")
    @RequiresPermissions("dhlk:view")
    public Result findList(@RequestParam(value = "name", required = false) String name) {
        return  dmNetTypeService.findList(name);
    }
}
