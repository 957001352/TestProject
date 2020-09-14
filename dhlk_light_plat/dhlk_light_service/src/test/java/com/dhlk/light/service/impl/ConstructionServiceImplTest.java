package com.dhlk.light.service.impl;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Construction;
import com.dhlk.light.service.dhlk_light_service.DhlkLightServiceApplication;
import com.dhlk.light.service.service.ConstructionService;
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
 * 施工信息单元测试
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkLightServiceApplication.class)
public class ConstructionServiceImplTest {

    @Autowired
    private ConstructionService constructionService;

    @Test
    public void save() {
        Construction construction = new Construction();
        construction.setSignDate("2020-06-10 11:12:14");
        construction.setBusinessPeople("li五");
        construction.setPackCount(10000);
        construction.setStartDate("2010-10-20 12:12:12");
        construction.setEndDate("2028-10-19 11:59:59");
        construction.setImplPeople("哈哈哈哈哈");
        Result result = constructionService.save(construction);
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }

    @Test
    public void update() {
        Construction construction = new Construction();
        construction.setId(24);
        construction.setSignDate("2020-06-10 11:12:14");
        construction.setBusinessPeople("li五改了改了");
        construction.setPackCount(9999999);
        construction.setStartDate("2010-10-20 12:12:12");
        construction.setEndDate("2028-10-19 11:59:59");
        construction.setImplPeople("改改");
        Result result = constructionService.save(construction);
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }

    @Test
    public void findList() {
        Result result = constructionService.findList("", "2020-06-09 12:12:12", "2020-06-30 11:59:59", 1, 10);
        PageInfo pageInfo = (PageInfo) result.getData();
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }

    @Test
    public void delete() {
        Result result = constructionService.delete("23,24");
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }

}
