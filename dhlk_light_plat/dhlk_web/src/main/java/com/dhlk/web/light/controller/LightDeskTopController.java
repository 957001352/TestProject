package com.dhlk.web.light.controller;/**
 * @创建人 wangq
 * @创建时间 2020/7/1
 * @描述
 */

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.DeskTop;
import com.dhlk.web.basicmodule.service.DeskTopService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: dhlk.light.plat
 *
 * @description:
 *
 * @author: wqiang
 *
 * @create: 2020-07-01 10:36
 **/

@RestController
@RequestMapping(value = "/lightDeskTop")
@Api(description = "多租户桌面菜单")
public class LightDeskTopController {

    @Autowired
    private DeskTopService deskTopService;

    /**
     * 新增
     * @param deskTopList
     * @return
     */
    @PostMapping (value = "/save")
    public Result save(@RequestBody List<DeskTop> deskTopList){
        return deskTopService.save(deskTopList);
    }

    @GetMapping(value = "/delete")
    public Result delete(@RequestParam("ids") String ids){
        return deskTopService.delete(ids);
    }

    @GetMapping(value = "/findList")
    public Result findList(
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return deskTopService.findList(userId,pageNum,pageSize);
    }
}
