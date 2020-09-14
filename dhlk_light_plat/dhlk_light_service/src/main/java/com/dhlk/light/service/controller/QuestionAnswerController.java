package com.dhlk.light.service.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.QuestionAnswer;
import com.dhlk.light.service.service.QuestionAnswerService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xkliu
 * @date 2020/6/8
 * <p>
 * 问题解决方案控制器
 */
@RestController
@RequestMapping(value = "/questionAnswer")
@Api(value = "QuestionAnswerController", description = "在线售后问题回复")
public class QuestionAnswerController {


    @Autowired
    private QuestionAnswerService questionAnswerService;

    /**
     * 保存
     *
     * @param questionAnswer
     * @return
     */
    @PostMapping(value = "/save")
    @RequiresAuthentication
    public Result save(@RequestBody QuestionAnswer questionAnswer) {
        return questionAnswerService.save(questionAnswer);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    @RequiresAuthentication
    public Result delete(@RequestParam(value = "ids") String ids) {
        return questionAnswerService.delete(ids);
    }
}
