package com.dhlk.web.basicmodule.service;

import com.dhlk.entity.basicmodule.TimeseriesParam;
import com.dhlk.web.basicmodule.service.fbk.TelemetryServiceFbk;
import com.dhlk.domain.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(value = "basicmodule-service/telemetry", fallback = TelemetryServiceFbk.class)
public interface TelemetryService {

    @GetMapping(value = "/getAttributesByScope")
    public Result getAttributesByScope(@RequestParam(value="tbId",required = false) String tbId) throws Exception;
    /**
     * 获取设备的历史遥测数据
     *
     * @param deviceId
     * @param keys
     * @return
     */
    @ApiOperation("获取设备的历史遥测数据")
    @PostMapping(value = "/getTimeseries")
    Result getTimeseries(@RequestBody TimeseriesParam timeseriesParam) throws Exception ;

}
