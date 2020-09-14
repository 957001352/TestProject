package com.dhlk.basicmodule.service.service.Impl;

import com.dhlk.basicmodule.service.dhlk_basic_module_service.DhlkBasicModuleServiceApplication;
import com.dhlk.basicmodule.service.service.NetDevicesService;
import com.dhlk.entity.basicmodule.NetDevices;
import com.dhlk.domain.Result;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/12
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkBasicModuleServiceApplication.class)
public class NetDevicesSerivceImplTest {
    @Autowired
    private NetDevicesService netDevicesService;
    /**
     * save
     */
    @Test
    public void save() throws Exception {
        NetDevices entity = new NetDevices();
        entity.setName("网络设备");
        entity.setIp("192.168.1.1");
        entity.setLicense("x001");
        entity.setTypeId(0);
        entity.setNote("网络设备");
        Result result=netDevicesService.save(entity);
        Assert.assertTrue(result.getCode()<0?false:true);
    }
    /**
     * update
     */
    @Test
    public void update() throws Exception {
        NetDevices entity = new NetDevices();
        entity.setId(1);
        entity.setName("网络设备");
        entity.setIp("192.168.1.1");
        entity.setLicense("001");
        entity.setTypeId(0);
        entity.setNote("网络设备");
        entity.setStatus(0);
        Result result=netDevicesService.save(entity);
        Assert.assertTrue(result.getCode()<0?false:true);
    }
    /**
     * delete
     */
    @Test
    public void delete() throws Exception {
        Result result=netDevicesService.delete("1");
        Assert.assertTrue(result.getCode()<0?false:true);
    }

    /**
     * findList
     */
    @Test
    public void findList() {
        Result result=netDevicesService.findList("string");
        List<NetDevices> list= (List<NetDevices>) result.getData();
        Assert.assertTrue(list.size()>0);
    }
}