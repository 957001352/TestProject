package com.dhlk.basicmodule.service.controller;

import com.alibaba.fastjson.JSONObject;
import com.dhlk.basicmodule.service.service.ProductDevicesService;
import com.dhlk.basicmodule.service.service.TelemetryService;
import com.dhlk.entity.basicmodule.NetDevices;
import com.dhlk.entity.basicmodule.ProductDevices;
import com.dhlk.domain.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.dhlk.utils.ResultUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* 生产设备管理
*/
@RestController
@RequestMapping(value = "/productDevices")
public class ProductDevicesController {
    @Autowired
    private ProductDevicesService productDevicesService;

    @Autowired
    private TelemetryService telemetryService;


    /**
     * 保存
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    @RequiresPermissions("productDevices:save")
    public Result save(@RequestBody ProductDevices productDevices) throws Exception {
        return productDevicesService.save(productDevices);
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @GetMapping(value = "/delete")
    @RequiresPermissions("productDevices:delete")
    public Result delete(@RequestParam(value = "ids") String ids) throws Exception {
        return productDevicesService.delete(ids);
    }

    /**
    * 列表查询
    */
    @GetMapping("/findList")
    @RequiresPermissions("dhlk:view")
    public Result findList(@RequestParam(value = "name", required = false) String name) {
        return  productDevicesService.findList(name);
    }
    /**
     * 设备属性查询
     */
    @GetMapping("/findAttrByClassifyById")
    @RequiresPermissions("dhlk:view")
    public Result findAttrByClassifyById(@RequestParam(value = "classifyId", required = true)String classifyId){
        return  productDevicesService.findAttrByClassifyById(classifyId);
    }
    /**
     * 按机构查询设备
     */
    @GetMapping("/findTreeList")
    @RequiresPermissions("dhlk:view")
    public Result findTreeList() {
        return  productDevicesService.findTreeList();
    }


    /**
     * 列表查询
     */
    @GetMapping("/findOnLineDevicesCount")
    @RequiresPermissions("dhlk:view")
    public Result findOnLineDevicesCount(@RequestParam(value = "name", required = false) String name) throws Exception {
        List<ProductDevices> allList= (List<ProductDevices>) productDevicesService.findList(name).getData();
        Integer c=0;
        for(ProductDevices productDevices:allList){
            List<NetDevices> netDevices=productDevices.getNetDevicesList();
            if(netDevices!=null&&netDevices.size()>0){
                 Result result= telemetryService.getAttributesByScope(netDevices.get(0).getTbId());
                 if(result.getCode()==0){
                     JSONObject object=(JSONObject)result.getData();
                     if(object.get("active").toString().equals("true")){
                         c++;
                     }
                 }

            }
        }
        Map<String,Integer> res=new HashMap<>();
        res.put("total",allList.size());
        res.put("online",c);
        return ResultUtils.success(res);
    }
}
