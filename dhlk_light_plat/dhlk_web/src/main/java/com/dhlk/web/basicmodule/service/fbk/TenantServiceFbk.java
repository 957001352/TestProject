package com.dhlk.web.basicmodule.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Tenant;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.basicmodule.service.TenantService;
import org.springframework.stereotype.Service;

@Service
public class TenantServiceFbk implements TenantService {

    @Override
    public Result save(Tenant tenant) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findList(String name, String startTime, String endTime, String expire, Integer pageNum, Integer pageSize) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(Integer ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result exportExcel(String expire, String startTime, String endTime, String name) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findTenantAdminList(Integer tenantId, Integer pageNum, Integer pageSize) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findTenantRepeat(String name,Integer id) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findTenantByCode(String code) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

}
