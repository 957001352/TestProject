package com.dhlk.light.service.dao;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Led;
import com.dhlk.entity.light.LedFault;
import com.dhlk.light.service.dhlk_light_service.DhlkLightServiceApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
* @Description:    测试LedFaultDao层代码
* @Author:         gchen
* @CreateDate:     2020/6/10 11:38
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkLightServiceApplication.class)
public class LedFaultDaoTest {
    @Autowired
    private LedFaultDao ledFaultDao;
    @Test
    public void insert() {
        LedFault fault = new LedFault();
        fault.setLedSn("123456");
        fault.setFaultCode("测试故障代码");
        Integer insert = ledFaultDao.insert(fault);
        Assert.assertTrue(insert==1?true:false);
    }
}
