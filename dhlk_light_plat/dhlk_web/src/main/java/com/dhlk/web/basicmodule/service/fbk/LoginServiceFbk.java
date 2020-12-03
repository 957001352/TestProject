package com.dhlk.web.basicmodule.service.fbk;

import com.dhlk.web.basicmodule.service.LoginService;
import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import org.springframework.stereotype.Service;
import com.dhlk.utils.ResultUtils;

@Service
public class LoginServiceFbk implements LoginService {

    @Override
    public Result login(String loginName, String password,String redisKey,String x,String y) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result logout() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result kaptcha() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result getTbToken() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result getToken() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result checkToken() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
