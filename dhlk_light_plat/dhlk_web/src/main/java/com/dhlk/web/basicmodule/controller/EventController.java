package com.dhlk.web.basicmodule.controller;

import com.dhlk.domain.Result;
import com.dhlk.web.basicmodule.service.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 查询事件列表
     */
    @GetMapping("/findList")
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
    @ResponseBody
    public Result remove(String ids)
    {
        return eventService.deleteEventByIds(ids);
    }
}
