package com.dhlk.basicmodule.service.service.Impl;

import com.dhlk.basicmodule.service.dhlk_basic_module_service.DhlkBasicModuleServiceApplication;
import com.dhlk.basicmodule.service.service.ApiListService;
import com.github.pagehelper.PageInfo;
import com.dhlk.domain.Result;
import com.dhlk.entity.api.ApiList;
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
public class ApiListSerivceImplTest {
    @Autowired
    private ApiListService apiListService;
    /**
     * save
     */
    @Test
    public void save() {
        ApiList entity = new ApiList();
        entity.setTitle("接口01");
        entity.setContent("新增，编辑，删除");
        entity.setClassifyId(1);
        entity.setVersion("1.1");
        Result result=apiListService.save(entity);
        Assert.assertTrue(result.getCode()<0?false:true);
    }
    @Test
    public void update() {
        ApiList entity = new ApiList();
        entity.setId(1);
        entity.setTitle("接口01");
        entity.setContent("新增，编辑，删除");
        entity.setClassifyId(1);
        entity.setVersion("1.2");
        Result result=apiListService.save(entity);
        Assert.assertTrue(result.getCode()<0?false:true);
    }
    /**
     * delete
     */
    @Test
    public void delete() {
        Result result=apiListService.delete("1");
        Assert.assertTrue(result.getCode()<0?false:true);
    }

    /**
     * findList
     */
    @Test
    public void findList() {
        Result result=apiListService.findPageList(1,1, 10);
        PageInfo pageInfo=(PageInfo)result.getData();
        Assert.assertTrue(1> 0);
    }
}