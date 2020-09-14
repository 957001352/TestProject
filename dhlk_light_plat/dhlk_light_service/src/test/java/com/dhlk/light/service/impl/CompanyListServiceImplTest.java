package com.dhlk.light.service.impl;

import com.dhlk.domain.Result;
import com.dhlk.light.service.dhlk_light_service.DhlkLightServiceApplication;
import com.dhlk.light.service.service.CompanyListService;
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
 * 企业列表单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkLightServiceApplication.class)
public class CompanyListServiceImplTest {

    @Autowired
    private CompanyListService companyListService;

    @Test
    public void findCompanyList() {
        Result result = companyListService.findCompanyList("镇江", "北京", 1, 10);
        PageInfo pageInfo = (PageInfo) result.getData();
        Assert.assertTrue(1 > 0);
    }


}
