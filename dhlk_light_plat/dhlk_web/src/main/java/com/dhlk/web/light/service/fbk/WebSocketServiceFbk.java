package com.dhlk.web.light.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.WebSocketService;
import org.springframework.stereotype.Service;

@Service
public class WebSocketServiceFbk implements WebSocketService {
    @Override
    public Result getIpPort() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
