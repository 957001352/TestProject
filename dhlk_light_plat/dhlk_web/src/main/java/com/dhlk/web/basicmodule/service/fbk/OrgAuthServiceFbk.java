package com.dhlk.web.basicmodule.service.fbk;

import com.dhlk.entity.basicmodule.OrgAuth;
import com.dhlk.web.basicmodule.service.OrgAuthService;
import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import org.springframework.stereotype.Service;
import com.dhlk.utils.ResultUtils;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/17
 */
@Service
public class OrgAuthServiceFbk implements OrgAuthService {

    @Override
    public Result save(OrgAuth orgAuth) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findPageList(Integer pageNum, Integer pageSize) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result authCenter(String key) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findAuthKey(String orgCode) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

}