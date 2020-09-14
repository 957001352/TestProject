package com.dhlk.web.light.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.AppLoginService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class AppLoginServiceFbk implements AppLoginService {
    @Override
    public Result appLogin(String loginName, String password,String tenantCode,String systemRunTime) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result appLogout() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result sycToken(String token) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
