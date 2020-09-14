package com.dhlk.basicmodule.service.service.Impl;

import com.dhlk.basicmodule.service.dhlk_basic_module_service.DhlkBasicModuleServiceApplication;
import com.dhlk.basicmodule.service.service.EventService;
import com.dhlk.domain.Result;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkBasicModuleServiceApplication.class)
public class EventServiceImplTest {
    @Autowired
    private EventService eventService;

    @Test
    public void getAttributesByScope() throws Exception {
        Result result=eventService.getAlarms(23,"ANY",null,20,1587346166869L,1587432566869L,false,null,true);
        System.out.println(result.getCode()+"---------------"+result.getData());
    }

}