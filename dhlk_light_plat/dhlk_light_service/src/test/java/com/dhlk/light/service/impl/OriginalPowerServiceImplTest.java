package com.dhlk.light.service.impl;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.OriginalPower;
import com.dhlk.light.service.dhlk_light_service.DhlkLightServiceApplication;
import com.dhlk.light.service.service.OriginalPowerService;
import com.github.pagehelper.PageInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xkliu
 * @date 2020/6/10
 * <p>
 * 企业原照明功率维护单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkLightServiceApplication.class)
public class OriginalPowerServiceImplTest {

    @Autowired
    private OriginalPowerService originalPowerService;


    @Test
    public void save() {
        OriginalPower originalPower = new OriginalPower();
        originalPower.setLedCount(100);
        originalPower.setLedOpentime(0.1F);
        originalPower.setLedPower(6.14F);
        Result result = originalPowerService.save(originalPower);
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }

    @Test
    public void update() {
        OriginalPower originalPower = new OriginalPower();
        originalPower.setId(11);
        originalPower.setLedCount(111);
        originalPower.setLedOpentime(0.899896F);
        originalPower.setLedPower(89.141254F);
        Result result = originalPowerService.save(originalPower);
        Assert.assertTrue(result.getCode() < 0 ? false : true);

    }

    @Test
    public void find() {
        Result result = originalPowerService.findOne();
        OriginalPower originalPower = (OriginalPower) result.getData();
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }

    @Test
    public void delete() {
        Result result = originalPowerService.delete("7");
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }


}
