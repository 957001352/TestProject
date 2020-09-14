package com.dhlk.light.service.impl;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.*;
import com.dhlk.light.service.dhlk_light_service.DhlkLightServiceApplication;
import com.dhlk.light.service.service.LedService;
import com.dhlk.light.service.service.SwitchService;
import com.github.pagehelper.PageInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkLightServiceApplication.class)
public class LedServiceImplTest {
    @Autowired
    private LedService ledService;
    /**
     * save保存
     */
    @Test
    public void insert() {
        Led led = new Led();
        led.setIp("192.168.2.215");
        led.setCreateTime("2015-2-15 12:30:00");
        led.setBrightness(50);
        led.setAreaId("1");
        led.setIndBright(1);
        led.setIndStatus(1);
        led.setIndTime(1123);
        led.setSn("2256");
        led.setUnindBright(123);
        led.setXaxis("12");
        led.setYaxis("21");
        Result save = ledService.save(led);
        System.out.println(save.getCode());
        Assert.assertTrue(save.getCode()==0?true:false);
    }

    /**
     * update修改
     */
    @Test
    public void update() {
        Led led = new Led();
        led.setIp("192.168.2.50");
        led.setId(2);
        Result save = ledService.save(led);
        System.out.println(save);
        Assert.assertTrue(save.getCode()==0?true:false);
    }

    /**
     * delete删除
     */
    @Test
    public void delete() {
        Result save = ledService.delete("2");
        System.out.println(save);
        Assert.assertTrue(save.getCode()==0?true:false);
    }

    /**
     * findList列表查询
     */
    @Test
    public void findList() {
        Result save = ledService.findList(null,null,null);
        List<Led> data = (List<Led>) save.getData();
        for (Led c:data) {
            System.out.println(c.toString());
        }
        Assert.assertTrue(save.getCode()==0?true:false);
    }

    /**
     * findPageList分页查询
     */
    @Test
    public void findPageList() {
        Result save = ledService.findPageList(null,null,null,1,10);
        PageInfo data = (PageInfo) save.getData();
        for (Object c:data.getList()) {
            System.out.println(c.toString());
        }
        Assert.assertTrue(save.getCode()==0?true:false);
    }

    /**
     * savePower能耗统计
     */
    @Test
    public void savePower() {
        List<LedPower> list = new ArrayList<>();
        for (int i = 0; i < 5 ; i++) {
            LedPower ledPower = new LedPower();
            ledPower.setElectric(BigDecimal.ONE);
            ledPower.setLedSn("123465");
            ledPower.setPower(BigDecimal.ONE);
            ledPower.setVoltage(BigDecimal.ONE);
            list.add(ledPower);
        }
        Result save = ledService.savePower(list);
        System.out.println(save.getCode());
        Assert.assertTrue(save.getCode()==0?true:false);
    }

    /**
     * saveOnline在线时长统计
     */
    @Test
    public void saveOnline() {
        LedOnline ledOnline = new LedOnline();
        ledOnline.setOnlineTime(2234);
        ledOnline.setLedSn("123465");
        Result save = ledService.saveOnline(ledOnline);
        System.out.println(save.getCode());
        Assert.assertTrue(save.getCode()==0?true:false);
    }

    /**
     * 增加开关与灯绑定关系
     */
    @Test
    public void saveSwitchBoundLed() {
        Switch aSwitch = new Switch();
        aSwitch.setId(1);
        List<Led> list = new ArrayList();
        Led led = new Led();
        led.setId(1);
        list.add(led);
        aSwitch.setLeds(list);
        Result save = ledService.saveSwitchBoundLed(aSwitch);
        System.out.println(save.getCode());
        Assert.assertTrue(save.getCode()==0?true:false);
    }

    /**
     * 设置灯亮度
     */
    @Test
    public void setLedBrightness() {
        Result save = ledService.setLedBrightness("1","80");
        System.out.println(save.getCode());
        Assert.assertTrue(save.getCode()==0?true:false);
    }

    /**
     * 查询有灯的区域
     */
    @Test
    public void findAreasByLed() {
        Result save = ledService.findAreasByLed();
        List<Area> data = (List<Area>) save.getData();
        System.out.println(save.getCode());
        for (Area a:data
             ) {
            System.out.println(a);
        }
        Assert.assertTrue(save.getCode()==0?true:false);
    }

    /**
     * 开关灯
     */
    @Test
    public void openOrCloseLed() {
        Result save = ledService.openOrCloseLed("1","0");
        System.out.println(save.getCode());
        Assert.assertTrue(save.getCode()==0?true:false);
    }

    /**
     * 获取照明设备故障信息
     */
    @Test
    public void findLedFault() {
        Result save = ledService.findLedFault("123456");
        System.out.println(save.getData());
        System.out.println(save.getCode());
        Assert.assertTrue(save.getCode()==0?true:false);
    }
}
