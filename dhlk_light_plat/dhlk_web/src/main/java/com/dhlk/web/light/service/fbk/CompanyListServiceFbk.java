package com.dhlk.web.light.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.CompanyListService;
import org.springframework.stereotype.Service;

/**
 * @author xkliu
 * @date 2020/6/4
 * <p>
 * 企业列表服务调用失败时的实现类
 */
@Service
public class CompanyListServiceFbk implements CompanyListService {

    @Override
    public Result findCompanyList(String name, String address, Integer pageNum, Integer pageSize) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result isCompanyExist(Integer id) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
