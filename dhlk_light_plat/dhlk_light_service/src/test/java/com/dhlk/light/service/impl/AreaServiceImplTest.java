package com.dhlk.light.service.impl;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Area;
import com.dhlk.light.service.dhlk_light_service.DhlkLightServiceApplication;
import com.dhlk.light.service.service.AreaService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author xkliu
 * @date 2020/6/10
 * <p>
 * 施工区域单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkLightServiceApplication.class)
public class AreaServiceImplTest {

    @Autowired
    private AreaService areaService;

    @Test
    public void save() {
        Area area = new Area();
        area.setId("abadcdffdfde3dfdcc");
        area.setArea("单元测试一下");
        area.setTenantId(3);
        area.setImagePath("42096df498a3465ea2edb20ca0cb873d.png");
        Result result = areaService.save(area);
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }

    @Test
    public void update() {
        Area area = new Area();
        area.setId("abadcdffdfde3dfdcc");
        area.setArea("单元测试一下,改");
        area.setImagePath("42096df498a346666665ea2edb20ca0cb873dsddssdsd.png");
        Result result = areaService.save(area);
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }

    @Test
    public void findList() {
        Result result = areaService.findList();
        List<Area> lists = (List<Area>) result.getData();
        for (Area list : lists) {
            System.out.println(list.toString());
        }
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }

    @Test
    public void delete() {
        Result result = areaService.delete("abadcdffdfde3dfdcc");
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }
}
