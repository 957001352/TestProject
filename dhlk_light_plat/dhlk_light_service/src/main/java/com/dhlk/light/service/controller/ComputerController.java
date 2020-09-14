package com.dhlk.light.service.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.BiProxyServerInfo;
import com.dhlk.entity.light.Computer;
import com.dhlk.light.service.service.ComputerService;
import com.dhlk.utils.ResultUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 一体机管理
 * @Author gchen
 * @Date 2020/6/4
 */
@RestController
@RequestMapping("/computer")
public class ComputerController {
    @Autowired
    private ComputerService computerService;
    /**
    * 保存/修改
    * @author      gchen
    * @param computer
    * @return
    * @date        2020/6/4 15:50
    */
    @PostMapping("/save")
    @RequiresPermissions("computer:save")
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
    @RequiresPermissions("computer:delete")
    public Result delete(@RequestParam("ids") String ids){
        return computerService.delete(ids);
    }

    /**
     * 获取列表
     * @author      gchen
     * @param name pageNum pageSize
     * @return
     * @date        2020/6/4 15:50
     */
    @GetMapping("/findList")
    @RequiresAuthentication
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
    @RequiresAuthentication
    public Result addReseller(@RequestParam("biProxyServerInfo") String biProxyServerInfo,
                              @RequestParam("mac") String mac){
        return computerService.addReseller(biProxyServerInfo,mac);
    }
}
