package com.dhlk.basicmodule.service.service.Impl;

import com.dhlk.basicmodule.service.dhlk_basic_module_service.DhlkBasicModuleServiceApplication;
import com.dhlk.basicmodule.service.service.TelemetryService;
import com.dhlk.domain.Result;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkBasicModuleServiceApplication.class)
public class TelemetryServiceImplTest {
    @Autowired
    private TelemetryService telemetryService;
    @Test
    public void getTimeseries() throws Exception {
        Result result=telemetryService.getTimeseries(20,"heartbeat,temperature",1587372900000L,	1587373320000L,1000L,20,"COUNT");
        System.out.println(result.getCode()+"---------------"+result.getData());
    }
    @Test
    public void getAttributesByScope() throws Exception {
        Result result=telemetryService.getAttributesByScope("");
        System.out.println(result.getCode()+"---------------"+result.getData());
        List<String> list=new ArrayList<String>();
    }
}