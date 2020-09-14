package com.dhlk.web.basicmodule.service.fbk;


import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.basicmodule.service.EventService;
import org.springframework.stereotype.Service;

@Service
public class EventServiceFbk implements EventService {

    @Override
    public Result getAlarms(Integer deviceId, String searchStatus, String status, int limit, Long startTime, Long endTime, boolean ascOrder, String offset, Boolean fetchOriginator) throws Exception {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result selectEventList(String tbId, String searchStatus, Long startTime, Long endTime) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result deleteEventByIds(String ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
