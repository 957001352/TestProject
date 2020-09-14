package com.dhlk.basicmodule.service.dhlk_basic_module_service;

import com.dhlk.basicmodule.service.service.SysLogService;
import com.dhlk.domain.Result;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: jzhao
 * @Date: 2020/4/13 17:35
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkBasicModuleServiceApplication.class)
public class SysLogServiceImplTest {
    @Autowired
    private SysLogService sysLogService;

    @Test
    public void findList() {
        Result result = sysLogService.findLogFile();
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }
}
