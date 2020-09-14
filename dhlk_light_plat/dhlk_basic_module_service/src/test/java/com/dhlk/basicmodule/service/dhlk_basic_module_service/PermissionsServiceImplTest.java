package com.dhlk.basicmodule.service.dhlk_basic_module_service;

import com.dhlk.basicmodule.service.service.PermissionsService;
import com.dhlk.domain.Result;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkBasicModuleServiceApplication.class)
public class PermissionsServiceImplTest {
    @Autowired
    private PermissionsService permissionsService;
    @Test
    public void insert() {

    }

    @Test
    public void deleteByRoleId() {
        //删除
        Result result = permissionsService.deleteByRoleIds("28");
        System.out.println(result.getCode()+"----------"+result.getData()+"------------------"+result.getMsg());
        Assert.assertTrue(result.getCode()>0);
    }
}