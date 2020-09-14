package com.dhlk.web.light.service.fbk;

import com.alibaba.fastjson.JSON;
import com.dhlk.domain.Result;
import com.dhlk.entity.light.GraphicStatistics;
import com.dhlk.entity.light.LedPower;

import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.DateUtils;
import com.dhlk.utils.ResultUtils;
import com.dhlk.utils.StringUtils;
import com.dhlk.web.light.service.GraphicStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: dhlk.light.plat
 * @description: 图表统计业务层
 * @author: wqiang
 * @create: 2020-07-28 11:06
 **/

@Service
public class GraphicStatisticsServiceFbk implements GraphicStatisticsService {

   /* @Override
    public Result getLightEnergyAndlightCount() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }*/


    /**
     * 今日与昨日能耗对比接口
     * @return
     */
    @Override
    public Result getTodayEnergyPKYesterdayEnergy() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    /**
     * 获取当月每天消耗的能耗
     * @param date
     * @return
     */
    @Override
    public Result getEveryDayEnergyByCurrentMonth(String date) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    /**
     * 获取系统运行时长
     * @return
     */
    @Override
    public Result getSystemRunTime() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }


    /**
     * 今日、昨日消耗能耗
     * @return
     */
    @Override
    public Result getTodayEnergyAndYesdayEnergy() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    /**
     * 获取所有灯消耗总能耗
     * @return
     */
    @Override
    public Result getEnergyTotal() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result getLightTotalAndOnlineCount() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }


}
