package com.dhlk.basicmodule.service.controller;

import com.alibaba.fastjson.JSONObject;
import com.dhlk.basicmodule.service.service.NetDevicesService;
import com.dhlk.basicmodule.service.service.TelemetryService;
import com.dhlk.entity.basicmodule.NetDevices;
import com.dhlk.domain.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.dhlk.utils.ResultUtils;

import java.util.*;


/**
* 网络设备管理
*/
@RestController
@RequestMapping(value = "/netDevices")
public class NetDevicesController {
    @Autowired
    private NetDevicesService netDevicesService;
    @Autowired
    private TelemetryService telemetryService;

    /**
     * 保存
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    @RequiresPermissions("netDevices:save")
    public Result save(@RequestBody NetDevices netDevices) throws Exception {
        return netDevicesService.save(netDevices);
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @GetMapping(value = "/delete")
    @RequiresPermissions("netDevices:delete")
    public Result delete(@RequestParam(value = "ids") String ids) throws Exception {
        return netDevicesService.delete(ids);
    }

    /**
    *列表查询
     * @param name
    * @return
    */
    @GetMapping("/findList")
    @RequiresPermissions("dhlk:view")
    public Result findList(@RequestParam(value = "name", required = false) String name) {
        return  netDevicesService.findList(name);
    }
    /*
    * 网络设备关联的生产设备查询
     * @param netDevicesId
    * @return
    */
    @GetMapping("/findPruductDevicesList")
    @RequiresPermissions("dhlk:view")
    public Result findPruductDevicesList(@RequestParam(value = "netDevicesId", required = true) Integer netDevicesId) {
        return  netDevicesService.findPruductDevicesList(netDevicesId);
    }
    /**
     * 状态修改启用禁用
     */
    @PostMapping(value = "/isEnable")
    @RequiresPermissions("netDevices:isEnable")
    public Result isEnable(@RequestParam(value = "id", required = true) Integer id,
                           @RequestParam(value = "status", required = true) Integer status) {
        return netDevicesService.isEnable(id,status);
    }

    @PostMapping("/findOnLineNetDevices")
    @RequiresPermissions("dhlk:view")
    public Result findOnLineNetDevices(@RequestBody List<JSONObject> jsonParam) throws Exception {
        List<JSONObject> result = new ArrayList<>();
        if (jsonParam != null && jsonParam.size() > 0) {
            for (int i = 0; i < jsonParam.size(); i++) {
                JSONObject res = (JSONObject) jsonParam.get(i);
                Result data = telemetryService.getAttributesByScope(res.get("tbId").toString());
                if (data.getCode() == 0) {
                    JSONObject object = (JSONObject) data.getData();
                    if (object.get("active").toString().equals("true")) {
                        res.put("onLine", true);
                    } else {
                        res.put("onLine", false);
                    }
                }
                result.add(res);
            }
        }
        return ResultUtils.success(result);
    }
}
