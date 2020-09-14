package com.dhlk.light.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dhlk.domain.Result;
import com.dhlk.entity.light.LedPower;
import com.dhlk.light.service.dao.GraphicStatisticsDao;
import com.dhlk.light.service.dao.LedDao;
import com.dhlk.light.service.dao.LightQueryDao;
import com.dhlk.light.service.service.GraphicStatisticsService;
import com.dhlk.light.service.service.LightQueryService;
import com.dhlk.light.service.util.LedConst;
import com.dhlk.utils.DateUtils;
import com.dhlk.utils.ResultUtils;
import com.dhlk.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/6/8
 */
@Service
public class LightQueryServiceImpl implements LightQueryService {

    @Autowired
    private LightQueryDao lightQueryDao;

    @Autowired
    private GraphicStatisticsService graphicStatisticsService;

    @Autowired
    private GraphicStatisticsDao graphicStatisticsDao;

    @Override
    public Result ledIntallQuery(String province) {
        return ResultUtils.success(lightQueryDao.ledIntallQuery(province));
    }

    @Override
    public Result provinceQuery(String province) {
        return ResultUtils.success(lightQueryDao.provinceQuery(province));
    }

    @Override
    public Result lastCompanyQuery(String province, Integer limit) {
        return ResultUtils.success(lightQueryDao.lastCompanyQuery(province, limit));
    }

    /**
     * 节能企业排行
     *
     * @param province
     * @param limit
     * @return
     */
    @Override
    public Result energyComRanking(String province, Integer limit) {
        List<LinkedHashMap<String, Object>> list = lightQueryDao.energyComRanking(province, limit, DateUtils.getCurrentMonthDay());
        if (list != null && list.size() > 0) {
            return ResultUtils.success(list.stream().filter(k -> Double.parseDouble(k.get("energy").toString()) > 0).collect(Collectors.toList()));
        }
        return ResultUtils.success(null);
    }

    /**
     * 碳排放、节省资金、总能耗
     * @param province
     * @return
     */
    @Override
    public Result thriftCarbonEmission(String province) {

        Map<String, Object> resultMap = new HashMap<>();
        //所有租户的原始总能耗
        Float orEnergySum = 0F;
        //所有租户的现在总能耗
        Float nowEnergySum = 0F;
        List<Integer> tenantIds = lightQueryDao.findTenantIds(province);
        if (tenantIds != null && tenantIds.size() > 0) {
            for (Integer tenantId : tenantIds) {
                //map单租户的原始能耗和单租户总能耗
                Map map = graphicStatisticsService.findOrEnergyAndTotalEnergyNow(tenantId);
                if (map != null) {
                    orEnergySum += Float.parseFloat(map.get("originalEnergy").toString());
                    nowEnergySum += Float.parseFloat(map.get("totalEnergyNow").toString());
                }
            }
            //节约碳排放
            Double crbonEmission = (orEnergySum - nowEnergySum) * 0.99;
            //节省钱
            Double spareMoney = (orEnergySum - nowEnergySum) * 0.7;
            if(crbonEmission < 0){
                crbonEmission = 0d;
            }
            if(spareMoney < 0 ){
                spareMoney = 0d;
            }

            resultMap.put("crbonEmission", crbonEmission);
            resultMap.put("spareMoney", spareMoney);
            resultMap.put("lightEnergyNow", nowEnergySum);//总能耗
            return ResultUtils.success(resultMap);
        }

        return ResultUtils.success(null);
    }

}