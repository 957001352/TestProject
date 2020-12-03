package com.dhlk.light.factory.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.light.factory.service.GroupService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupService groupService;
    /**
     * 查询
     * @author      gchen
     * @return
     * @date        2020/6/4 15:50
     */
    @GetMapping("/findList")
    //@RequiresAuthentication
    public Result findList(){
        return groupService.findList();
    }

    /**
     * 查询灯的改变状态
     * @author      gchen
     * @return
     * @date        2020/6/4 15:50
     */
    @PostMapping("/ledStatusIsChange")
    //@RequiresAuthentication
    public Result ledStatusIsChange(@RequestBody InfoBox<String> infoBox){
//            @RequestParam("sns")String sns,@RequestParam("status")String status){
        return groupService.ledStatusIsChange(infoBox.getSns(),infoBox.getT());
    }

    /**
     * 查询
     * @author      gchen
     * @return
     * @date        2020/6/4 15:50
     */
    @GetMapping("/findGroupList")
    //@RequiresAuthentication
    public Result findGroupList(@RequestParam("areaId") String areaId){
        return groupService.findGroupList(areaId);
    }
}
