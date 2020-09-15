package com.dhlk.light.factory.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.domain.Result;
import com.dhlk.entity.light.GraphicStatistics;
import com.dhlk.entity.light.LedPower;
import com.dhlk.entity.light.OriginalPower;
import com.dhlk.light.factory.dao.GraphicStatisticsDao;
import com.dhlk.light.factory.dao.OriginalPowerDao;
import com.dhlk.light.factory.service.GraphicStatisticsService;
import com.dhlk.light.factory.util.LedConst;
import com.dhlk.service.RedisService;
import com.dhlk.utils.DateUtils;
import com.dhlk.utils.ResultUtils;
import com.dhlk.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: dhlk.light.plat
 * @description: 图表统计业务层
 * @author: wqiang
 * @create: 2020-07-28 11:06
 **/

@Service
@Transactional
public class GraphicStatisticsServiceImpl implements GraphicStatisticsService {

    @Autowired
    private GraphicStatisticsDao graphicStatisticsDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private OriginalPowerDao originalPowerDao;


    /**
     * 今日与昨日能耗对比接口
     *
     * @return
     */
    @Override
    public Result getTodayEnergyPKYesterdayEnergy() {
        Map<String, Object> map = new HashMap<>();
        map.put("todayEnergy", graphicStatisticsDao.getTodayEnergyByHourGroup());
        map.put("yesterdayEnergy", graphicStatisticsDao.getYesterdayEnergyByHourGroup());
        return ResultUtils.success(map);
    }

    /**
     * 获取当月每天消耗的能耗
     *
     * @param date
     * @return
     */
    @Override
    public Result getEveryDayEnergyByCurrentMonth(String date) {
        return ResultUtils.success(graphicStatisticsDao.getEveryDayEnergyByCurrentMonth(date));
    }

    /**
     * 获取系统运行时长
     *
     * @return
     */
    @Override
    public Result getSystemRunTime() {

        OriginalPower originalPower = originalPowerDao.selectOriginalPowerByTenantId(null);
        if (originalPower != null && !StringUtils.isEmpty(originalPower.getSystemRunTime())) {
            return ResultUtils.success(DateUtils.getDistanceTime(Long.valueOf(originalPower.getSystemRunTime()), DateUtils.getLongCurrentTimeStamp()));
        }
        return ResultUtils.success("0分");
    }


    /**
     * 今日、昨日消耗能耗
     *
     * @return
     */
    @Override
    public Result getTodayEnergyAndYesdayEnergy() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("todayEnergy", graphicStatisticsDao.getTodayEnergy());
        resultMap.put("yesterdayEnergy", graphicStatisticsDao.getYesterdayEnergy());
        return ResultUtils.success(resultMap);
    }

    /**
     * 获取所有灯消耗总能耗
     *
     * @return
     */
    @Override
    public Result getEnergyTotal() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("energyTotal", graphicStatisticsDao.getEnergyTotal());
        return ResultUtils.success(resultMap);
    }

    @Override
    public Result getLightTotalAndOnlineCount() {
        Map<String, Object> resultMap = new HashMap<>();

        //灯在线计数器
        int lightOnLineCount = 0;

        //获取灯总数
        resultMap.put("lightTotal", graphicStatisticsDao.getLightTotal());

        List<String> snList = graphicStatisticsDao.getAllLightSn();
        //从redis中获取灯在线数
        if (snList != null && snList.size() > 0) {
            for (String sn : snList) {
                if (redisService.hasKey(LedConst.REDIS_POWER + sn)) {
                    LedPower ledPower = JSON.parseObject(redisService.get(LedConst.REDIS_POWER + sn).toString(), LedPower.class);
                    //1在线 0离线
                    if ("1".equals(ledPower.getStatus())) {
                        lightOnLineCount++;
                    }
                }
            }
        }
        resultMap.put("lightOnLineCount", lightOnLineCount);
        return ResultUtils.success(resultMap);
    }


    /**
     * 灯节约总电数
     *
     * @return
     */
    @Override
    public Result lightEconomyTotal() {

        //所有灯节能的总能耗
        float lightEnergyTotal = 0F;
        //灯原始功率
        Float originalPower = graphicStatisticsDao.selectPower();
        //灯总能耗
        Float totalEnergyNow = graphicStatisticsDao.searchTotalEnergy();
        //灯总时长
        float lightOnlineTime = graphicStatisticsDao.selectTotalOnlineTime();
        //租户原始功率* 在线时长 - 现在灯消耗的能耗
        if (originalPower != null) { //企业原始不配置的话，界面显示节约电量显示0
            lightEnergyTotal = (originalPower / 1000) * lightOnlineTime - totalEnergyNow;
        }
        if(lightEnergyTotal < 0){
            lightEnergyTotal = 0F;
        }

        return ResultUtils.success(lightEnergyTotal);
    }


}
