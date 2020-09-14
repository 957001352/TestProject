package com.dhlk.web.light.service.fbk;/**
 * @创建人 wangq
 * @创建时间 2020/6/5
 * @描述
 */

import com.dhlk.domain.Result;
import com.dhlk.entity.light.FaultCode;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.FaultCodeService;
import org.springframework.stereotype.Service;

/**
 * @program: dhlk.multi.tenant
 *
 * @description: 故障代码service
 *
 * @author: wqiang
 *
 * @create: 2020-06-05 09:09
 **/
@Service
public class FaultCodeServiceFBK implements FaultCodeService {
    @Override
    public Result save(FaultCode faultCode) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findList(String name, String code, Integer pageNum, Integer pageSize) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
