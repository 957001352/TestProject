package com.dhlk.web.light.controller;

import com.alibaba.fastjson.JSON;
import com.dhlk.domain.Result;
import com.dhlk.entity.light.Computer;
import com.dhlk.enums.SystemEnums;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.ComputerService;
import com.dhlk.web.proxy.service.ProxyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 一体机管理
 * @Author gchen
 * @Date 2020/6/4
 */
@RestController
@RequestMapping("/computer")
@Api(description = "一体机管理", value = "computer")
public class ComputerController {
    @Autowired
    private ComputerService computerService;
    @Autowired
    private ProxyService proxyService;
    /**
    * 保存/修改
    * @author      gchen
    * @param computer
    * @return
    * @date        2020/6/4 15:50
    */
    @PostMapping("/save")
    @ApiOperation("新增/修改")
    public Result save(@RequestBody Computer computer){
        return computerService.save(computer);
    }

    /**
     * 批量删除
     * @author gchen
     * @param ids
     * @return
     * @date        2020/6/4 15:50
     */
    @GetMapping("/delete")
    @ApiOperation("删除")
    public Result delete(@RequestParam("ids") String ids){
        return computerService.delete(ids);
    }

    /**
     * 列表查询
     * @author      gchen
     * @param name pageNum pageSize
     * @return
     * @date        2020/6/4 15:50
     */
    @GetMapping("/findList")
    @ApiOperation("列表查询")
    public Result findList(@RequestParam(value = "name",required = false) String name,
                           @RequestParam(value = "pageNum",required = false) Integer pageNum,
                           @RequestParam(value = "pageSize",required = false) Integer pageSize){
        return computerService.findList(name,pageNum,pageSize);
    }

    /**
     * 添加代理
     * @author      gchen
     */
    @GetMapping("/addReseller")
    public Result addReseller(@RequestParam("deviceId") String deviceId,
                              @RequestParam("mac") String mac){
        Result add = proxyService.add(deviceId);
        if(add.getCode() == 0){
            return computerService.addReseller(JSON.toJSONString(add.getData()),mac);
        }
        return add;
    }

    /**
     * 代理跳转
     * @author      gchen
     */
    @GetMapping("/aquireResellerUrl")
    public Result aquireResellerUrl(@RequestParam("deviceId") String deviceId,
                                    @RequestParam("mac") String mac){
        Result add = proxyService.add(deviceId);
        if(add.getCode() == 0){
            return computerService.addReseller(JSON.toJSONString(add.getData()),mac);
        }
        return proxyService.requestIndexUrl(deviceId);
    }
}
