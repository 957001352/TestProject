package com.dhlk.basicmodule.service.service.Impl;

import com.dhlk.basicmodule.service.dhlk_basic_module_service.DhlkBasicModuleServiceApplication;
import com.dhlk.basicmodule.service.service.OrgService;
import com.github.pagehelper.PageInfo;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Org;
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
public class OrgSerivceImplTest {
    @Autowired
    private OrgService orgServiceImpl;
    /**
     * save
     */
    @Test
    public void save() {
        Org l = new Org();
        l.setName("开发部");
        l.setParentId(2);
        Result result = orgServiceImpl.save(l);
        Assert.assertTrue(result.getCode()<0?false:true);
    }

    /**
     * delete
     */
    @Test
    public void delete() {
        Result result=orgServiceImpl.delete(3);
        Assert.assertTrue(result.getCode()<0?false:true);
    }

    /**
     * findList
     */
    @Test
    public void findList() {
        Result result = orgServiceImpl.findPageList(2,2, 2);
        PageInfo data =(PageInfo) result.getData();
        Assert.assertTrue(1>0);
    }

    /**
     * 机构树查询
     */
    @Test
    public void findTreeList() {
        Result result = orgServiceImpl.findTreeList();
        List data =(List) result.getData();
        Assert.assertTrue(1>0);
    }
}