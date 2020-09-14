package com.dhlk.web.basicmodule.service.fbk;

import com.dhlk.entity.basicmodule.Org;
import com.dhlk.web.basicmodule.service.OrgService;
import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import org.springframework.stereotype.Service;
import com.dhlk.utils.ResultUtils;

@Service
public class OrgServiceFbk implements OrgService {
    @Override
    public Result save(Org org) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(Integer id) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findPageList(Integer parentId, Integer pageNum, Integer pageSize) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findTreeList() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findPageUserByOrgId(Integer orgId, Integer pageNum, Integer pageSize) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
