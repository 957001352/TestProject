package com.dhlk.light.service.impl;/**
 * @创建人 wangq
 * @创建时间 2020/6/10
 * @描述
 */

import com.dhlk.domain.Result;
import com.dhlk.light.service.dhlk_light_service.DhlkLightServiceApplication;
import com.dhlk.light.service.service.LedPowerStatisticsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: dhlk.multi.tenant
 * @description: 能耗统计 单元测试
 * @author: wqiang
 * @create: 2020-06-10 10:28
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkLightServiceApplication.class)
public class LedPowerStatisticsServiceImplTest {

    @Autowired
    LedPowerStatisticsService ledPowerStatisticsService;


    @Test
    public void findList() {
        Result result = ledPowerStatisticsService.findList("2020-06-10", "", "", "", 1, 10);
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }

    @Test
    public void findRealEnergyByLedId(){
        Result result =  ledPowerStatisticsService.findRealEnergyByledId("001");
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }
}
