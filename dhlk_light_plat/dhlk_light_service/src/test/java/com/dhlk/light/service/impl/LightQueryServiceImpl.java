package com.dhlk.light.service.impl;/**
 * @创建人 wangq
 * @创建时间 2020/6/19
 * @描述
 */

import com.dhlk.light.service.dao.LightQueryDao;
import com.dhlk.light.service.dhlk_light_service.DhlkLightServiceApplication;
import com.dhlk.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @program: dhlk.multi.tenant
 *
 * @description: 灯统计
 *
 * @author: wqiang
 *
 * @create: 2020-06-19 14:16
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkLightServiceApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LightQueryServiceImpl {

    @Autowired
    private LightQueryDao lightQueryDao;

    /**
     * 获取节能公司排行
     */
    @Test
    public void energyComRanking(){
        List<LinkedHashMap<String, Object>> linkedHashMaps = lightQueryDao.energyComRanking("北京市", 10, DateUtils.getCurrentMonthDay());
        System.out.println(linkedHashMaps.toString());
    }
}
