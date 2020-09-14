package com.dhlk.light.service.impl;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Area;
import com.dhlk.entity.light.IntensityInfo;
import com.dhlk.entity.light.LedParamInfo;
import com.dhlk.light.service.dhlk_light_service.DhlkLightServiceApplication;
import com.dhlk.light.service.service.AreaService;
import com.dhlk.light.service.service.LedParamInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author gchen
 * @date 2020/7/20
 * <p>
 * 参数列表单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkLightServiceApplication.class)
public class LedParamInfoServiceImplTest {

    @Autowired
    private LedParamInfoService ledParamInfoService;

    @Test
    public void save() {
        IntensityInfo intensityInfo = new IntensityInfo();
        intensityInfo.setIllumi_flr(1);
        intensityInfo.setIllumi_flr_max(2);
        intensityInfo.setIllumi_top(3);
        intensityInfo.setIllumi_top_min(4);
        intensityInfo.setOn_off(0);
        LedParamInfo ledParamInfo = new LedParamInfo(intensityInfo);
        Result result = ledParamInfoService.save(ledParamInfo);
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }

    @Test
    public void update() {
        IntensityInfo intensityInfo = new IntensityInfo();
        intensityInfo.setIllumi_flr(1);
        intensityInfo.setIllumi_flr_max(2);
        intensityInfo.setIllumi_top(3);
        intensityInfo.setIllumi_top_min(52);
        intensityInfo.setOn_off(1);
        LedParamInfo ledParamInfo = new LedParamInfo(intensityInfo);
        ledParamInfo.setSn("00202027111111");
        Result result = ledParamInfoService.update(ledParamInfo);
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }

    @Test
    public void findList() {
        Result result = ledParamInfoService.findList("");
        List<LedParamInfo> lists = (List<LedParamInfo>) result.getData();
        for (LedParamInfo list : lists) {
            System.out.println(list.toString());
        }
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }
}
