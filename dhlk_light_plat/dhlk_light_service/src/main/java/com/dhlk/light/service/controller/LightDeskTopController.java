package com.dhlk.light.service.controller;/**
 * @创建人 wangq
 * @创建时间 2020/7/1
 * @描述
 */


import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.DeskTop;
import com.dhlk.light.service.service.LightDeskTopService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: dhlk.light.plat
 *
 * @description: 桌面菜单控制器
 *
 * @author: wqiang
 *
 * @create: 2020-07-01 10:18
 **/
@RestController
@RequestMapping(value = "/lightDeskTop")
public class LightDeskTopController {

    @Autowired
   private LightDeskTopService deskTopService;

    /**
     * 新增
     * @param deskTopList
     * @return
     */
    @RequestMapping(value = "/save")
    public Result save(@RequestBody List<DeskTop> deskTopList){
        return deskTopService.save(deskTopList);
    }

    @RequestMapping(value = "/delete")
    public Result delete(@Param("ids") String ids){
        return deskTopService.delete(ids);
    }

    @PostMapping(value = "/findList")
    public Result findList(
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return deskTopService.findList(userId,pageNum,pageSize);
    }

}
