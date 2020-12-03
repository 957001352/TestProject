package com.dhlk.light.service.service.impl;/**
 * @创建人 wangq
 * @创建时间 2020/6/8
 * @描述
 */

import com.dhlk.domain.Result;
import com.dhlk.entity.light.LedPower;
import com.dhlk.entity.light.LedPowerStatistics;
import com.dhlk.enums.ResultEnum;
import com.dhlk.light.service.dao.LedPowerStatisticsDao;
import com.dhlk.light.service.service.LedPowerStatisticsService;
import com.dhlk.light.service.util.HeaderUtil;
import com.dhlk.light.service.util.LedConst;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ExcelUtil;
import com.dhlk.utils.ResultUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @program: dhlk.multi.tenant
 *
 * @description: 灯能耗service
 *
 * @author: wqiang
 *
 * @create: 2020-06-08 15:52
 **/

@Service
@Repository
public class LedPowerStatisticsServiceImpl implements LedPowerStatisticsService {

    @Autowired
    private LedPowerStatisticsDao ledPowerStatisticsDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HeaderUtil headerUtil;

    @Override
    public Result findList(String startDate, String endDate, String area, String led_switch, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<LedPowerStatistics> list = ledPowerStatisticsDao.findList(startDate, endDate, area, led_switch,headerUtil.tenantId());
        PageInfo<LedPowerStatistics> faultCodePage = new PageInfo<>(list);
        return ResultUtils.success(faultCodePage);
    }

    /**
     * 新增能耗统计
     * @param ledPower
     */

    @Override
    public void save(LedPower ledPower){
        ledPowerStatisticsDao.insert(ledPower);
    }


    /**
     * 更具灯id到redis中查询实时信息
     * @param ledId
     * @return
     */
    @Override
    public Result findRealEnergyByledId(String ledId) {
        if(CheckUtils.isNull(ledId)){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }

        if(!redisTemplate.hasKey(LedConst.REDIS_POWER +ledId)){
           return ResultUtils.error("未查到灯SN："+ledId+"的信息！");
        }
        String value = (String) redisTemplate.opsForValue().get(LedConst.REDIS_POWER+ledId);
        return ResultUtils.success(value);
    }

    @Override
    public Result findPeopleFeelNumber(String areaId, String date) {
        if(CheckUtils.isNull(areaId) || CheckUtils.isNull(date)){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        return ResultUtils.success(ledPowerStatisticsDao.findPeopleFeelNumber(areaId,date,headerUtil.tenantId()));
    }

    /**
     * 导出能源统计
     * @param startDate
     * @param endDate
     * @param area
     * @param led_switch
     */
    @Override
    public Result exportEnergyStatistics(String startDate, String endDate, String area, String led_switch,Integer tenantId) {
        List<LinkedHashMap<String, Object>> list = ledPowerStatisticsDao.exportEnergyStatistics(startDate, endDate, area, led_switch, tenantId);
        return ResultUtils.success(list);
    }


}
