package com.dhlk.basicmodule.service.service.Impl;

import com.dhlk.basicmodule.service.dhlk_basic_module_service.DhlkBasicModuleServiceApplication;
import com.dhlk.basicmodule.service.service.LoginLogService;
import com.github.pagehelper.PageInfo;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.LoginLog;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/12
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkBasicModuleServiceApplication.class)
public class LoginLogSerivceImplTest {
    @Autowired
    private LoginLogService loginLogService;
    /**
     * save
     */
    @Test
    public void save() {
        LoginLog l = new LoginLog();
        l.setIp("192.168.2.226");
        Result result = loginLogService.save(l);
        Assert.assertTrue(result.getCode()<0?false:true);
    }

    /**
     * findTree
     */
    @Test
    public void findList() {
        Result result = loginLogService.findPageList("fchai", "192.168.2.226", "2020-01-02", "2020-05-02", 2, 2);
        PageInfo data =(PageInfo) result.getData();
        Assert.assertTrue(1>0);
    }
}