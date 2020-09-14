package com.dhlk.web.light.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Question;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author xkliu
 * @date 2020/6/5
 * <p>
 * 意见反馈控制器
 */
@RestController
@RequestMapping(value = "/question")
@Api(description = "在线售后", value = "QuestionController")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 新增/修改意见反馈
     *
     * @param question
     * @param bindingResult
     * @return
     */
    @ApiOperation("新增/修改意见反馈")
    @PostMapping("/save")
    public Result save(@Valid @RequestBody Question question, BindingResult bindingResult) {
        Result result = ResultUtils.loadResult(bindingResult);
        if (result == null) {
            return questionService.save(question);
        }
        return result;
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @ApiOperation("逻辑删除")
    @GetMapping("/delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return questionService.delete(ids);
    }

    /**
     * 意见反馈列表查询
     *
     * @param
     * @return
     */
    @ApiOperation("意见反馈列表查询")
    @GetMapping("/findList")
    public Result findList(@RequestParam(value = "sn", required = false) String sn,
                           @RequestParam(value = "startDate", required = false) String startDate,
                           @RequestParam(value = "endDate", required = false) String endDate,
                           @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return questionService.findList(sn, startDate, endDate, pageNum, pageSize);
    }

    /**
     * 获取企业名称
     *
     * @param
     * @return
     */
    @ApiOperation("获取企业名称")
    @GetMapping("/getCompanyList")
    public Result findList() {
        return questionService.getCompanyList();
    }

    /**
     * 意见反馈详情查询
     *
     * @param
     * @return
     */
    @ApiOperation("意见反馈详情查询")
    @GetMapping("/findQuestionDetail")
    public Result findQuestionDetail(@RequestParam(value = "id") String id) {
        return questionService.findQuestionDetail(id);
    }
}
