package com.dhlk.basicmodule.service.service.Impl;

import com.alibaba.fastjson.JSON;
import com.dhlk.basicmodule.service.dhlk_basic_module_service.DhlkBasicModuleServiceApplication;
import com.dhlk.basicmodule.service.service.ProductDevicesService;
import com.dhlk.basicmodule.service.util.RestTemplateUtil;
import com.dhlk.entity.basicmodule.ProductDevices;
import com.dhlk.entity.tb.credentials.DeviceCredentials;
import com.dhlk.entity.tb.credentials.DeviceId;
import com.dhlk.entity.tb.credentials.Id;
import com.dhlk.domain.Result;
import com.dhlk.service.RedisService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/12
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkBasicModuleServiceApplication.class)
public class ProductDevicesSerivceImplTest {
    @Autowired
    private ProductDevicesService productDevicesService;


    @Autowired
    private RestTemplateUtil restTemplateUtil;

    /**
     * save
     */
    @Test
    public void save() throws Exception {
        ProductDevices entity = new ProductDevices();
        entity.setName("机器gsgs22hh");
        entity.setOrgId(1);
        entity.setClassifyId("1");
        Result result=productDevicesService.save(entity);
        Assert.assertTrue(result.getCode()<0?false:true);
    }
    /**
     * update
     */
    @Test
    public void update() throws Exception {
        ProductDevices entity = new ProductDevices();
        entity.setId(30);
        entity.setName("机器nnn");
        entity.setOrgId(2);
        entity.setStatus(0);
        Result result=productDevicesService.save(entity);
        Assert.assertTrue(result.getCode()<0?false:true);
    }
    /**
     * delete
     */
    @Test
    public void delete() throws Exception {
        Result result=productDevicesService.delete("28,30");
        Assert.assertTrue(result.getCode()<0?false:true);
    }

    /**
     * findList
     */
    @Test
    public void findList() {
        Result result=productDevicesService.findList("String");
        List<ProductDevices> list= (List<ProductDevices>) result.getData();
        Assert.assertTrue(list.size()>0);
    }
    /**
     * findTbDeviceByDhlkId
     */
    @Test
    public void findTbDeviceByDhlkId() throws Exception {
        Result tbDeviceResult = productDevicesService.findTbDeviceByDhlkId(29);
    }
    /**
     * findTbDeviceByDhlkId
     */
    @Test
    public void test2(){
        String s = restTemplateUtil.loginSucessToken();
        System.out.println(s);
    }
    /**
     * findTbDeviceByDhlkId
     */
    @Test
    public void test3() throws Exception {
        String s = restTemplateUtil.getTbJwtToken();
        System.out.println(s);
    }
    @Test
    public void test5(){
       /* Result result = productDevicesService.testDevice("3");
        List<String> list=(List<String>)result.getData();*/
       /* for(){

        }*/
        /*System.out.println(JSON.toJSONString(list));
        System.out.println(result.getData());*/
    }
    @Test
    public void test6(){
        //根据tb设备id获取tb设备凭据id
        ResponseEntity<Map> iDResponseEntity = restTemplateUtil.getRestTemplate("/api/device/40fc4090-7e23-11ea-8392-6dbee2348266/credentials", Map.class, true);
        //保存设备数据到dhlk数据库
        Map<String, Object> map = iDResponseEntity.getBody();
        Map<String, Object> mapId = (Map<String, Object>) map.get("id");
        String credentialsId = mapId.get("id").toString();

        Id id=new Id(credentialsId);
        DeviceId deviceId=new DeviceId("40fc4090-7e23-11ea-8392-6dbee2348266","DEVICE");
        DeviceCredentials deviceCredentials=new DeviceCredentials(id,deviceId,"ACCESS_TOKEN","gbnm66lll");
        System.out.println(JSON.toJSONString(deviceCredentials));
        System.out.println(JSON.toJSONString(deviceCredentials.toString()));
        ResponseEntity<String> responseEntity = restTemplateUtil.postRestTemplate("/api/device/credentials", deviceCredentials, String.class, true);
        System.out.println(responseEntity.getBody());
    }
}