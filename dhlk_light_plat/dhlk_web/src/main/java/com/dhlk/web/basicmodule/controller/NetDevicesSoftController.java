package com.dhlk.web.basicmodule.controller;

import com.dhlk.entity.basicmodule.NetDevicesSoft;
import com.dhlk.web.basicmodule.service.NetDevicesSoftService;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* 网络设备管理
*/
@RestController
@RequestMapping(value = "/netDevicesSoft")
@Api(description = "网络软件维护")
public class NetDevicesSoftController {
    @Autowired
    private NetDevicesSoftService netDevicesSoftService;

    /**
     * 保存
     * @param
     * @return
     */
    @ApiOperation("新增/修改")
    @PostMapping(value = "/save")
    public Result save(@RequestBody NetDevicesSoft netDevicesSoft) {
        return netDevicesSoftService.save(netDevicesSoft);
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @ApiOperation("删除")
    @GetMapping(value = "/delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return netDevicesSoftService.delete(ids);
    }

    /**
    * 列表查询
     * @param pageNum
     * @param pageSize
    * @return
    */
    @ApiOperation("分页查询")
    @GetMapping("/findList")
    public Result findPageList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return  netDevicesSoftService.findPageList(pageNum, pageSize);
    }
}
