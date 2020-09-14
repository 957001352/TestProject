package com.dhlk.basicmodule.service.controller;

import com.alibaba.fastjson.JSON;
import com.dhlk.basicmodule.service.service.EventService;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Event;
import com.dhlk.entity.tb.event.TbEvent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Api(value = "EventController", description = "事件")
@RequestMapping(value = "/event")
@RestController
public class EventController {
    @Autowired
    private EventService eventService;

    /**
     * 获取设备的警告信息
     *
     * @param deviceId
     * @return
     */
    @ApiOperation("获取设备的警告信息")
    @GetMapping(value = "/getAlarms")
    public Result getAlarms(@RequestParam(value="deviceId")Integer deviceId,
                            @RequestParam(value="searchStatus",required = false) String searchStatus,
                            @RequestParam(value="status",required = false) String status,
                            @RequestParam(value="limit",required = true) int limit,
                            @RequestParam(value="startTime",required = false) Long startTime,
                            @RequestParam(value="endTime",required = false) Long endTime,
                            @RequestParam(value="ascOrder",required = false, defaultValue = "false") boolean ascOrder,
                            @RequestParam(value="offset",required = false) String offset,
                            @RequestParam(value="fetchOriginator",required = false) Boolean fetchOriginator) throws Exception{
        return eventService.getAlarms(deviceId,searchStatus,status,limit,startTime,endTime,ascOrder,offset,fetchOriginator);
    }
    /**
     * 获取设备的警告信息
     *
     //* @param deviceId
     * @return
     */
    @ApiOperation("保存设备的警告信息")
    @PostMapping(value = "/tbCallEvent")
    public void tbCallEvent(@RequestBody String playload) throws Exception{
        TbEvent tbevent = JSON.parseObject(playload, TbEvent.class);
        Event event=new Event();
        BeanUtils.copyProperties(tbevent,event);
        //解析并保存事件id
        Map mapId = JSON.parseObject( tbevent.getId(), Map.class);
        event.setAlarmId(mapId.get("id").toString());
        //解析并保存tb id
        Map mapTbId = JSON.parseObject( tbevent.getOriginator(), Map.class);
        event.setTbid(mapTbId.get("id").toString());
        eventService.insertEvent(event);
    }


    /**
     * 查询事件列表
     */
    @GetMapping("/findList")
    @RequiresPermissions("dhlk:view")
    @ResponseBody
    public Result findList(@RequestParam(value="tbId")String tbId,
                       @RequestParam(value="searchStatus",required = false) String searchStatus,
                       @RequestParam(value="startTime",required = false) Long startTime,
                       @RequestParam(value="endTime",required = false) Long endTime
                      )
    {
        return eventService.selectEventList(tbId,searchStatus,startTime,endTime);
    }

    /**
     * 删除事件
     */
    @GetMapping( "/delete")
    @RequiresPermissions("event:delete")
    @ResponseBody
    public Result remove(@RequestParam(value="ids")String ids)
    {
        return eventService.deleteEventByIds(ids);
    }
}
