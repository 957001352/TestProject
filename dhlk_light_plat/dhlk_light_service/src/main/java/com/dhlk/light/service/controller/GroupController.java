package com.dhlk.light.service.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Switch;
import com.dhlk.light.service.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 灯分组
 * @Author gchen
 * @Date 2020/7/28
 */
@RestController
@RequestMapping("/group")
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
//        @RequiresPermissions("group:save")
        public Result save(@RequestBody Switch swich){
            return groupService.save(swich);
        }

        /**
         * 删除分组
         * @author gchen
         * @return
         * @date        2020/6/4 15:50
         */
        @GetMapping("/delete")
//        @RequiresPermissions("group:delete")
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
//        @RequiresAuthentication
        public Result findList(@RequestParam("areaId") String areaId){
            return groupService.findList(areaId);
        }
}
