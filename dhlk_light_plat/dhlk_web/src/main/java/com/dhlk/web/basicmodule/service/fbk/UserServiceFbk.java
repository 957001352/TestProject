package com.dhlk.web.basicmodule.service.fbk;

import com.dhlk.entity.basicmodule.User;
import com.dhlk.web.basicmodule.service.UserService;
import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import org.springframework.stereotype.Service;
import com.dhlk.utils.ResultUtils;

@Service
public class UserServiceFbk implements UserService {
    @Override
    public Result save(User user) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findList(String name) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result updatePassword(Integer id, String password) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findOrg(Integer id) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result isEnable(Integer id, Integer status) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findListByTenantId(String tenantId) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
