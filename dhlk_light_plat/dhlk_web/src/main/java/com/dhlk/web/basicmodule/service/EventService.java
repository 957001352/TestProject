package com.dhlk.web.basicmodule.service;

import com.dhlk.domain.Result;
import com.dhlk.web.basicmodule.service.fbk.EventServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 事件Service接口
 *
 * @author jlv
 * @date 2020-04-26
 */
@FeignClient(value = "basicmodule-service/event", fallback = EventServiceFbk.class)
public interface EventService {
    @GetMapping(value = "/getAlarms")
    public Result getAlarms(@RequestParam(value = "deviceId") Integer deviceId,
                            @RequestParam(value = "searchStatus", required = false) String searchStatus,
                            @RequestParam(value = "status", required = false) String status,
                            @RequestParam(value = "limit", required = true) int limit,
                            @RequestParam(value = "startTime", required = false) Long startTime,
                            @RequestParam(value = "endTime", required = false) Long endTime,
                            @RequestParam(value = "ascOrder", required = false, defaultValue = "false") boolean ascOrder,
                            @RequestParam(value = "offset", required = false) String offset,
                            @RequestParam(value = "fetchOriginator", required = false) Boolean fetchOriginator) throws Exception;

    /**
     * 查询事件列表
     *
     *
     * @return 事件集合
     */
    @GetMapping("/findList")
    public Result selectEventList(@RequestParam(value = "tbId") String tbId,
                                  @RequestParam(value = "searchStatus", required = false) String searchStatus,
                                  @RequestParam(value = "startTime", required = false) Long startTime,
                                  @RequestParam(value = "endTime", required = false) Long endTime
    );


    /**
     * 批量删除事件
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @GetMapping( "/delete")
    public Result deleteEventByIds(@RequestParam(value = "ids") String ids);



    }

