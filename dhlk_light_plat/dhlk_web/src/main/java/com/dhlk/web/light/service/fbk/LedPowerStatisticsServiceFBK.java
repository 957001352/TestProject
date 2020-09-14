package com.dhlk.web.light.service.fbk;/**
 * @创建人 wangq
 * @创建时间 2020/6/8
 * @描述
 */

import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.LedPowerStatisticsService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @program: dhlk.multi.tenant
 *
 * @description: 能耗查询控制器
 *
 * @author: wqiang
 *
 * @create: 2020-06-08 16:56
 **/

@Service
public class LedPowerStatisticsServiceFBK implements LedPowerStatisticsService {
    @Override
    public Result findList(String startDate, String endDate, String area, String led_switch, Integer pageNum, Integer pageSize) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findRealEnergyByLedId(String ledId) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findPeopleFeelNumber(String areaId, String date) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result exportEnergyStatistics(String startDate, String endDate, String area, String led_switch,Integer tenantId) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }


}
