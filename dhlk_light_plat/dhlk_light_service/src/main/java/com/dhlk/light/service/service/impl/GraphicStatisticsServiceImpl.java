package com.dhlk.light.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.dhlk.domain.Result;
import com.dhlk.entity.light.GraphicStatistics;
import com.dhlk.entity.light.LedPower;
import com.dhlk.entity.light.OriginalPower;
import com.dhlk.enums.ResultEnum;
import com.dhlk.light.service.dao.GraphicStatisticsDao;
import com.dhlk.light.service.dao.OriginalPowerDao;
import com.dhlk.light.service.service.GraphicStatisticsService;
import com.dhlk.light.service.service.OriginalPowerService;
import com.dhlk.light.service.util.HeaderUtil;
import com.dhlk.light.service.util.LedConst;
import com.dhlk.service.RedisService;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.DateUtils;
import com.dhlk.utils.ResultUtils;
import com.dhlk.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class GraphicStatisticsServiceImpl implements GraphicStatisticsService {

    @Autowired
    private GraphicStatisticsDao graphicStatisticsDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private HeaderUtil headerUtil;
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
        map.put("todayEnergy", graphicStatisticsDao.getTodayEnergyByHourGroup(headerUtil.tenantId()));
        map.put("yesterdayEnergy", graphicStatisticsDao.getYesterdayEnergyByHourGroup(headerUtil.tenantId()));
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
        if (CheckUtils.isNull(date)) {
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        return ResultUtils.success(graphicStatisticsDao.getEveryDayEnergyByCurrentMonth(date, headerUtil.tenantId()));
    }

    /**
     * 获取系统运行时长
     *
     * @return
     */
    @Override
    public Result getSystemRunTime() {

        OriginalPower originalPower = originalPowerDao.selectOriginalPowerByTenantId(headerUtil.tenantId());
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
        resultMap.put("todayEnergy", graphicStatisticsDao.getTodayEnergy(headerUtil.tenantId()));
        resultMap.put("yesterdayEnergy", graphicStatisticsDao.getYesterdayEnergy(headerUtil.tenantId()));
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
        resultMap.put("energyTotal", graphicStatisticsDao.getEnergyTotal(headerUtil.tenantId()));
        return ResultUtils.success(resultMap);
    }

    @Override
    public Result getLightTotalAndOnlineCount() {
        Map<String, Object> resultMap = new HashMap<>();

        //灯在线计数器
        int lightOnLineCount = 0;

        //获取灯总数
        resultMap.put("lightTotal", graphicStatisticsDao.getLightTotal(headerUtil.tenantId()));

        List<String> snList = graphicStatisticsDao.getAllLightSn(headerUtil.tenantId());
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
     * 单租户原始能耗和现在能耗
     *
     * @param tenantId
     * @return
     */
    @Override
    public Map findOrEnergyAndTotalEnergyNow(Integer tenantId) {

        //灯原始功率
        Float originalPower = graphicStatisticsDao.selectPower(tenantId);
        //如果原始功率未配置的话就不计算节省能耗
        if(originalPower == null ){
            return null;
        }

        //获取租户下所有灯消耗的能耗
        Float totalEnergy = graphicStatisticsDao.searchTotalEnergyByTenantId(tenantId);
        //获取租户下所有灯在线时长
        Float totalOnlineTime = graphicStatisticsDao.selectTotalOnlineTimeByTenantId(tenantId);
        //企业原始总能耗
        Float originalTotalEnergy = (originalPower / 1000) * totalOnlineTime;
        Map<String, Object> map = new HashMap<>();
        map.put("originalEnergy", originalTotalEnergy);
        map.put("totalEnergyNow", totalEnergy);

        return map;
    }


}
