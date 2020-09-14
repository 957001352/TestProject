package com.dhlk.web.basicmodule.controller;

import com.dhlk.entity.basicmodule.TimeseriesParam;
import com.dhlk.web.basicmodule.service.TelemetryService;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "TelemetryController", description = "遥测数据")
@RequestMapping(value = "/telemetry")
@RestController
public class TelemetryController {
    @Autowired
    private TelemetryService telemetryService;

    /**
     * 获取设备的服务端属性
     *
     * @param tbId
     * @return
     */
    @ApiOperation("获取设备的服务端属性")
    @GetMapping(value = "/getAttributesByScope")
    //@RequiresPermissions("menu:save")
    public Result getAttributesByScope(@RequestParam(value="tbId",required = false) String tbId) throws Exception {
        return telemetryService.getAttributesByScope(tbId);
    }
    /**
     * 获取设备的历史遥测数据
     * @return
     */
    @ApiOperation("获取设备的历史遥测数据")
    @PostMapping(value = "/getTimeseries")
    public Result getTimeseries(@RequestBody TimeseriesParam timeseriesParam) throws Exception {
        return telemetryService.getTimeseries(timeseriesParam);
    }
}
