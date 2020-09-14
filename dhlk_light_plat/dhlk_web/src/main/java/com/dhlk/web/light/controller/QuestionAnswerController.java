package com.dhlk.web.light.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.QuestionAnswer;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.QuestionAnswerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author xkliu
 * @date 2020/6/8
 * <p>
 * 问题解决方案控制器
 */
@RestController
@RequestMapping(value = "/questionAnswer")
@Api(description = "在线售后问题回复", value = "QuestionAnswerController")
public class QuestionAnswerController {

    @Autowired
    private QuestionAnswerService questionAnswerService;

    /**
     * 新增/修改问题解决方案
     *
     * @param questionAnswer
     * @param bindingResult
     * @return
     */
    @ApiOperation("新增/修改问题解决方案")
    @PostMapping("/save")
    public Result save(@Valid @RequestBody QuestionAnswer questionAnswer, BindingResult bindingResult) {
        Result result = ResultUtils.loadResult(bindingResult);
        if (result == null) {
            return questionAnswerService.save(questionAnswer);
        }
        return result;
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @ApiOperation("物理删除")
    @GetMapping("/delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return questionAnswerService.delete(ids);
    }

}
