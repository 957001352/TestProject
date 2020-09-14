package com.dhlk.light.service.impl;

import com.dhlk.light.service.dhlk_light_service.DhlkLightServiceApplication;
import com.dhlk.light.service.service.InspectionReportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xkliu
 * @date 2020/6/12
 * <p>
 * 验收报告单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkLightServiceApplication.class)
public class InspectionReportServiceImplTest {

    @Autowired
    private InspectionReportService inspectionReportService;

    @Test
    public void save() {

    }
}
