package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.DmClassifyTypeService;
import com.dhlk.entity.dm.DmClassifyType;
import com.dhlk.domain.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* 设备类型分类
*/
@RestController
@RequestMapping(value = "/dmClassifyType")
public class DmClassifyTypeController {
    @Autowired
    private DmClassifyTypeService dmClassifyTypeService;

    /**
     * 保存
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    @RequiresPermissions("dmClassifyType:save")
    public Result save(@RequestBody DmClassifyType dmClassifyType) {
        return dmClassifyTypeService.save(dmClassifyType);
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @GetMapping(value = "/delete")
    @RequiresPermissions("dmClassifyType:delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return dmClassifyTypeService.delete(ids);
    }

    /**
    * 列表查询
    * @return
    */
    @GetMapping("/findList")
    @RequiresPermissions("dhlk:view")
    public Result findList(@RequestParam(value = "name", required = false) String name) {
        return  dmClassifyTypeService.findList(name);
    }
}
