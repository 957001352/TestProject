package com.dhlk.web.basicmodule.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.MonitorDetail;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.basicmodule.service.MonitorService;
import org.springframework.stereotype.Service;

@Service
public class MonitorServiceFbk implements MonitorService {

    @Override
    public Result indexList(MonitorDetail detail) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}