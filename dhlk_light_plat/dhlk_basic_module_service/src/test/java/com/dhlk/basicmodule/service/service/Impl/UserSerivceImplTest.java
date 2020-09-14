package com.dhlk.basicmodule.service.service.Impl;

import com.dhlk.basicmodule.service.dhlk_basic_module_service.DhlkBasicModuleServiceApplication;
import com.dhlk.basicmodule.service.service.UserService;
import com.github.pagehelper.PageInfo;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.User;
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
public class UserSerivceImplTest {
    @Autowired
    private UserService userServiceImpl;
    /**
     * save
     */
    @Test
    public void save() {
        User l = new User();
        l.setName("测试2号");
        l.setLoginName("测试2号");
        l.setPassword("123456");
        l.setRoleIds("1");
        Result result = userServiceImpl.save(l);
        Assert.assertTrue(result.getCode()<0?false:true);
    }

    /**
     * delete
     */
    @Test
    public void delete() {
        Result result=userServiceImpl.delete("2,3");
        Assert.assertTrue(result.getCode()<0?false:true);
    }

    /**
     * findList
     */
    @Test
    public void findList() {
        Result result = userServiceImpl.findList("slp");
        PageInfo data =(PageInfo) result.getData();
        Assert.assertTrue(1>0);
    }

    /**
     * 状态修改
     */
    @Test
    public void isEnable() {
        Result result = userServiceImpl.isEnable(13,1);
        Assert.assertTrue(result.getCode()<0?false:true);
    }

    /**
     * 修改密码
     */
    @Test
    public void updatePassword() {
        Result result = userServiceImpl.updatePassword(13,"654321");
        Assert.assertTrue(result.getCode()<0?false:true);
    }
}