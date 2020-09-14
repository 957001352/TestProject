package com.dhlk.light.service.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Switch;
import com.dhlk.light.service.service.SwitchService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 智慧开关
 * @Author gchen
 * @Date 2020/6/4
 */
@RestController
@RequestMapping("/switch")
public class SwitchController {
    @Autowired
    private SwitchService switchService;
    /**
     * 保存/修改
     * @author      gchen
     * @param swich
     * @return
     * @date        2020/6/4 15:50
     */
    @PostMapping("/save")
    @RequiresPermissions("switch:save")
    public Result save(@RequestBody Switch swich){
        return switchService.save(swich);
    }

    /**
     * 批量删除
     * @author gchen
     * @param ids
     * @return
     * @date        2020/6/4 15:50
     */
    @GetMapping("/delete")
    @RequiresPermissions("switch:delete")
    public Result delete(@RequestParam("ids") String ids){
        return switchService.delete(ids);
    }

    /**
     * 保存/修改
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
        return switchService.findList(name,pageNum,pageSize);
    }
    @GetMapping("/findGroupList")
    @RequiresAuthentication
    public Result findGroupList(@RequestParam(value = "switchId",required = false) Integer switchId){
        return switchService.findGroupList(switchId);
    }


}