package com.dhlk.web.basicmodule.service;

import com.alibaba.fastjson.JSONObject;
import com.dhlk.entity.basicmodule.NetDevices;
import com.dhlk.web.basicmodule.service.fbk.NetDevicesServiceFbk;
import com.dhlk.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 生产设备管理
 **/
@FeignClient(value = "basicmodule-service/netDevices", fallback = NetDevicesServiceFbk.class)
public interface NetDevicesService {

    /**
     * 保存
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody NetDevices netDevices)throws Exception;

    /**
     * 删除
     * @param ids
     * @return result
     */
    @GetMapping(value = "/delete")
    Result delete(@RequestParam(value = "ids") String ids)throws Exception;



    @GetMapping("/findList")
    Result findList(@RequestParam(value = "name", required = false) String name);

    @GetMapping("/findPruductDevicesList")
    Result findPruductDevicesList(@RequestParam(value = "netDevicesId", required = true) Integer netDevicesId);
    /**
     * 状态修改启用禁用
     */
    @PostMapping(value = "/isEnable")
    Result isEnable(@RequestParam(value = "id", required = true) Integer id,
                    @RequestParam(value = "status", required = true) Integer status);

    @PostMapping("/findOnLineNetDevices")
    Result findOnLineNetDevices(@RequestBody List<JSONObject>  jsonParam);
}