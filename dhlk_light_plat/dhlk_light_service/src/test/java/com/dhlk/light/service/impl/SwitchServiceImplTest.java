package com.dhlk.light.service.impl;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Computer;
import com.dhlk.entity.light.Switch;
import com.dhlk.light.service.dhlk_light_service.DhlkLightServiceApplication;
import com.dhlk.light.service.service.SwitchService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkLightServiceApplication.class)
public class SwitchServiceImplTest {
    @Autowired
    private SwitchService switchService;
    /**
     * save保存
     */
    @Test
    public void insert() {
        Switch aSwitch = new Switch();
        aSwitch.setIp("192.168.2.215");
        aSwitch.setCreateTime("2015-2-15 12:30:00");
        aSwitch.setName("测试");
        aSwitch.setSn("123456");
        aSwitch.setAreaId("1");
        aSwitch.setComputerId(2);
        Result save = switchService.save(aSwitch);
        System.out.println(save.getCode());
        Assert.assertTrue(save.getCode()==0?true:false);
    }

    /**
     * update修改
     */
    @Test
    public void update() {
        Switch aSwitch = new Switch();
        aSwitch.setIp("192.168.2.215");
        aSwitch.setCreateTime("2015-2-15 12:30:00");
        aSwitch.setName("测试123");
        aSwitch.setSn("123456");
        aSwitch.setAreaId("1");
        aSwitch.setComputerId(2);
        aSwitch.setId(3);
        Result save = switchService.save(aSwitch);
        System.out.println(save);
        Assert.assertTrue(save.getCode()==0?true:false);
    }

    /**
     * delete删除
     */
    @Test
    public void delete() {
        Result save = switchService.delete("3");
        System.out.println(save);
        Assert.assertTrue(save.getCode()==0?true:false);
    }

    /**
     * findList列表查询
     */
    @Test
    public void findList() {
        Result save = switchService.findList(null,null,null);
        List<Switch> data = (List<Switch>) save.getData();
        for (Switch c:data) {
            System.out.println(c.toString());
        }
        Assert.assertTrue(save.getCode()==0?true:false);
    }
}
