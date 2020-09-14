package com.dhlk.web.basicmodule.service.fbk;

import com.dhlk.entity.basicmodule.TimeseriesParam;
import com.dhlk.web.basicmodule.service.TelemetryService;
import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import org.springframework.stereotype.Service;
import com.dhlk.utils.ResultUtils;

@Service
public class TelemetryServiceFbk implements TelemetryService {

    @Override
    public Result getAttributesByScope(String tbId) throws Exception {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result getTimeseries(TimeseriesParam timeseriesParam) throws Exception {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
