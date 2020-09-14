package com.dhlk.basicmodule.service.service.Impl;

import com.dhlk.basicmodule.service.dhlk_basic_module_service.DhlkBasicModuleServiceApplication;
import com.dhlk.basicmodule.service.service.ApiClassifyService;
import com.github.pagehelper.PageInfo;
import com.dhlk.domain.Result;
import com.dhlk.entity.api.ApiClassify;
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
public class ApiClassifySerivceImplTest {
    @Autowired
    private ApiClassifyService apiClassifyService;
    /**
     * save
     */
    @Test
    public void save() {
        ApiClassify entity = new ApiClassify();
        entity.setClassName("004");
        entity.setParentId(1);
        Result result=apiClassifyService.save(entity);
        Assert.assertTrue(result.getCode()<0?false:true);
    }
    /**
     * save
     */
    @Test
    public void update() {
        ApiClassify entity = new ApiClassify();
        entity.setId(1);
        entity.setClassName("001");
        entity.setParentId(1);
        Result result=apiClassifyService.save(entity);
        Assert.assertTrue(result.getCode()<0?false:true);
    }
    /**
     * delete
     */
    @Test
    public void delete() {
        Result result=apiClassifyService.delete("3");
        Assert.assertTrue(result.getCode()<0?false:true);
    }

    /**
     * findList
     */
    @Test
    public void findList() {
        Result result=apiClassifyService.findPageList(null,1, 10);
        PageInfo pageInfo=(PageInfo)result.getData();
        List<ApiClassify> list=pageInfo.getList();
        Assert.assertTrue(list.size()>0);
    }
    /**
     * findList
     */
    @Test
    public void findTree() {
        Result result=apiClassifyService.findTreeList();
        List list=(List)result.getData();
        Assert.assertTrue(list.size()>0);
    }
}