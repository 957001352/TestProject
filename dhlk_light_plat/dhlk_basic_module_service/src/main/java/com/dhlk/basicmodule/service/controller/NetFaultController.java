package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.NetFaultService;
import com.dhlk.entity.basicmodule.NetFault;
import com.dhlk.domain.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 网络设备故障
 * @Author lpsong
 * @Date 2020/4/20
 */
@RestController
@RequestMapping(value = "/netFault")
public class NetFaultController {

    @Autowired
    private NetFaultService netFaultService;


    @PostMapping(value = "/save")
    @RequiresPermissions("netFault:save")
    public Result save(@RequestBody NetFault netFault) {
        return netFaultService.save(netFault);
    }



    @PostMapping(value = "/dealFault")
    @RequiresPermissions("netFault:dealFault")
    public Result dealFault(@RequestParam(value = "id") Integer id,
                       @RequestParam(value = "status") Integer status) {
        return netFaultService.dealFault(id,status);
    }
    /**
     *列表查询
     * @param status
     * @return
     */
    @GetMapping("/findList")
    @RequiresPermissions("dhlk:view")
    public Result findList(@RequestParam(value = "tbId", required = false) String tbId,
                           @RequestParam(value = "status", required = false) Integer status) {
        return  netFaultService.findList(tbId,status);
    }
}