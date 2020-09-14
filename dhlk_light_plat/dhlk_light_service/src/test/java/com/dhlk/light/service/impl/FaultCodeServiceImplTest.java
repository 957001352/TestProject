package com.dhlk.light.service.impl;/**
 * @创建人 wangq
 * @创建时间 2020/6/10
 * @描述
 */

import com.dhlk.domain.Result;
import com.dhlk.entity.light.FaultCode;
import com.dhlk.light.service.dhlk_light_service.DhlkLightServiceApplication;
import com.dhlk.light.service.service.FaultCodeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: dhlk.multi.tenant
 * @description: 故障代码 单元测试
 * @author: wqiang
 * @create: 2020-06-10 09:42
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkLightServiceApplication.class)
public class FaultCodeServiceImplTest {
    @Autowired
    FaultCodeService faultCodeService;

    @Test
    public void save() {
        FaultCode faultCode = new FaultCode();
        faultCode.setId(2);
        faultCode.setCode("002");
        faultCode.setContent("单元测试故障代码内容2");
        faultCode.setName("故障代码2");
        Result result = faultCodeService.save(faultCode);
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }

    @Test
    public void findList() {
        Result result = faultCodeService.findList("故障代码1", "001", 1, 10);
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }

    @Test
    public void delete() {
        Result result = faultCodeService.delete("1");
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }
}
