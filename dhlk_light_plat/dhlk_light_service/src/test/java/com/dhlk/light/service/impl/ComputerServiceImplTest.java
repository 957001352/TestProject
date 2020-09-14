package com.dhlk.light.service.impl;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Computer;
import com.dhlk.light.service.dao.ComputerDao;
import com.dhlk.light.service.dhlk_light_service.DhlkLightServiceApplication;
import com.dhlk.light.service.service.ComputerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkLightServiceApplication.class)
public class ComputerServiceImplTest {
    @Autowired
    private ComputerService computerService;
    /**
     * save保存
     */
    @Test
    public void insert() {
        Computer computer = new Computer();
        computer.setIp("192.168.2.215");
        computer.setCreateTime("2015-2-15 12:30:00");
        computer.setName("wodetian");
        computer.setSn("123456");
        Result save = computerService.save(computer);
        System.out.println(save);
        Assert.assertTrue(save.getCode()==0?true:false);
    }

    /**
     * update修改
     */
    @Test
    public void update() {
        Computer computer = new Computer();
        computer.setId(1);
        computer.setIp("192.168.2.215");
        computer.setCreateTime("2015-2-15 12:30:00");
        computer.setName("haha");
        computer.setSn("12345678");
        Result save = computerService.save(computer);
        System.out.println(save);
        Assert.assertTrue(save.getCode()==0?true:false);
    }

    /**
     * delete删除
     */
    @Test
    public void delete() {
        Result save = computerService.delete("1,2");
        System.out.println(save);
        Assert.assertTrue(save.getCode()==0?true:false);
    }

    /**
     * findList列表查询
     */
    @Test
    public void findList() {
        Result save = computerService.findList(null,null,null);
        List<Computer> data = (List<Computer>) save.getData();
        for (Computer c:data) {
            System.out.println(c.toString());
        }
        Assert.assertTrue(save.getCode()==0?true:false);
    }
}
