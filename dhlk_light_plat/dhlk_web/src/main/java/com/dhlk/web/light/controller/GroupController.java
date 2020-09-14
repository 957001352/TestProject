package com.dhlk.web.light.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Switch;
import com.dhlk.web.light.service.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 灯分组
 * @Author gchen
 * @Date 2020/7/28
 */
@RestController
@RequestMapping("/group")
@Api(description = "灯分组", value = "GroupController")
public class GroupController {
        @Autowired
        private GroupService groupService;
        /**
         * 保存/修改
         * @author      gchen
         * @param swich
         * @return
         * @date        2020/6/4 15:50
         */
        @PostMapping("/save")
        @ApiOperation("保存")
        public Result save(@RequestBody Switch swich){
            return groupService.save(swich);
        }

        /**
         * 删除
         * @author gchen
         * @return
         * @date        2020/6/4 15:50
         */
        @GetMapping("/delete")
        @ApiOperation("删除")
        public Result delete(@RequestParam("id") String id){
            return groupService.delete(id);
        }

        /**
         * 查询
         * @author      gchen
         * @return
         * @date        2020/6/4 15:50
         */
        @GetMapping("/findList")
        @ApiOperation("查询")
        public Result findList(@RequestParam("areaId") String areaId){
            return groupService.findList(areaId);
        }
}
