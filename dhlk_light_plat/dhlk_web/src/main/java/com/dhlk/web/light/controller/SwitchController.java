package com.dhlk.web.light.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Switch;
import com.dhlk.web.light.service.SwitchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 开关管理
 * @Author gchen
 * @Date 2020/6/4
 */
@RestController
@RequestMapping("/switch")
@Api(description = "开关管理", value = "switch")
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
    @ApiOperation("新增/修改")
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
    @ApiOperation("删除")
    public Result delete(@RequestParam("ids") String ids){
        return switchService.delete(ids);
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
        return switchService.findList(name,pageNum,pageSize);
    }

    @GetMapping("/findGroupList")
    @ApiOperation("开关组列表查询")
    public Result findGroupList(@RequestParam(value = "switchId",required = false) Integer switchId){
        return switchService.findGroupList(switchId);
    }
}
