package com.dhlk.light.service.impl;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Question;
import com.dhlk.light.service.dhlk_light_service.DhlkLightServiceApplication;
import com.dhlk.light.service.service.QuestionService;
import com.dhlk.utils.IDUtils;
import com.github.pagehelper.PageInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author xkliu
 * @date 2020/6/10
 * <p>
 * 意见反馈单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkLightServiceApplication.class)
public class QuestionServiceImplTest {

    @Autowired
    private QuestionService questionService;

    @Test
    public void save() {
        Question question = new Question();
        question.setId(IDUtils.getId());
        question.setSn("abs-0111110");
        question.setCreateUser(123);
        question.setTenantId(3);
        question.setContent("问题描述一下");
        question.setLinkMan("lisi");
        question.setPhone("010-11111111");
        question.setTitle("这是问题概述");
        Result result = questionService.save(question);
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }

    @Test
    public void update() {
        Question question = new Question();
        question.setId("154a0894871b41e29363f0349651b497");
        question.setSn("abs-0111110");
        question.setContent("问题描述一下gaigaigagi");
        question.setLinkMan("wu");
        question.setPhone("010-11111111");
        question.setTitle("这是问题概述");
        Result result = questionService.save(question);
        Assert.assertTrue(result.getCode() < 0 ? false : true);

    }

    @Test
    public void findList() {
        Result result = questionService.findList("123","", "2020-06-10 14:35:55", 1, 10);
        PageInfo pageInfo = (PageInfo) result.getData();
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }

    @Test
    public void delete() {
        Result result = questionService.delete("154a0894871b41e29363f0349651b497");
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }

    @Test
    public void findQuestionDetail() {
        Result result = questionService.findQuestionDetail("dc6c5a0ee9e441868610948f4488cd39");
        List<Question> lists = (List<Question>) result.getData();
        for (Question list : lists) {
            System.out.println(list.toString());
        }
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }
}
