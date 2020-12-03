package com.dhlk.web.ai.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.ai.service.LightLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/26
 * Time:9:10
 * @Description: 灯位置信息service实现类
 */
@Service
public class LightLocationServiceFbk implements LightLocationService {

    /**
     * 所有灯位置信息
     *
     * @return
     */
    @Override
    public Result findAll(String tenantId,String area) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    /**
     * 通过sn查询灯位置信息
     *
     * @param sn
     * @return
     */
    @Override
    public Result selectBySn(String sn) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    /**
     * 通过租户id查询灯位置信息
     *
     * @param tenantId
     * @return
     */
    @Override
    public Result selectByTenantId(Integer tenantId) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
