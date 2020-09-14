package com.dhlk.light.service.impl;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.QuestionAnswer;
import com.dhlk.light.service.dhlk_light_service.DhlkLightServiceApplication;
import com.dhlk.light.service.service.QuestionAnswerService;
import com.dhlk.utils.IDUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xkliu
 * @date 2020/6/10
 * <p>
 * 问题解决方案单元测试
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DhlkLightServiceApplication.class)
public class QuestionAnswerServiceImplTest {

    @Autowired
    private QuestionAnswerService questionAnswerService;

    @Test
    public void save() {
        QuestionAnswer questionAnswer = new QuestionAnswer();
        questionAnswer.setId(IDUtils.getId());
        questionAnswer.setAnswer("解决方案的的得到的哒哒哒哒哒哒多多多多多多多多多多多多多多多多多多多多多多多多多");
        questionAnswer.setDealUser(123);
        questionAnswer.setQuestionId("154a0894871b41e29363f0349651b497");
        Result result = questionAnswerService.save(questionAnswer);
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }


    @Test
    public void delete() {
        Result result = questionAnswerService.delete("b596063a0ef54be3874f4dc3056b735b");
        Assert.assertTrue(result.getCode() < 0 ? false : true);
    }
}
