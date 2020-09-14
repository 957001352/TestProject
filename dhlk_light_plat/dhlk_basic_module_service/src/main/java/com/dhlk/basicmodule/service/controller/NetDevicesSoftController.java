package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.NetDevicesSoftService;
import com.dhlk.entity.basicmodule.NetDevicesSoft;
import com.dhlk.domain.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* 网络设备管理
*/
@RestController
@RequestMapping(value = "/netDevicesSoft")
public class NetDevicesSoftController {
    @Autowired
    private NetDevicesSoftService netDevicesSoftService;

    /**
     * 保存
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    @RequiresPermissions("netDevicesSoft:save")
    public Result save(@RequestBody NetDevicesSoft netDevicesSoft) {
        return netDevicesSoftService.save(netDevicesSoft);
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @GetMapping(value = "/delete")
    @RequiresPermissions("netDevicesSoft:delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return netDevicesSoftService.delete(ids);
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
        return  netDevicesSoftService.findPageList(pageNum, pageSize);
    }
}
