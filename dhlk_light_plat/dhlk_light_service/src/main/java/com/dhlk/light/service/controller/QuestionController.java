package com.dhlk.light.service.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Question;
import com.dhlk.light.service.service.QuestionService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xkliu
 * @date 2020/6/5
 * <p>
 * 问题反馈控制器
 */
@RestController
@RequestMapping(value = "/question")
@Api(value = "QuestionController", description = "在线售后")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 保存
     *
     * @param question
     * @return
     */
    @PostMapping(value = "/save")
    @RequiresPermissions("question:save")
    public Result save(@RequestBody Question question) {
        return questionService.save(question);
    }


    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    @RequiresPermissions("question:delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return questionService.delete(ids);
    }


    /**
     * 意见反馈列表查询
     *
     * @param
     * @return
     */
    @GetMapping("/findList")
    @RequiresAuthentication
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
    @GetMapping("/getCompanyList")
    @RequiresAuthentication
    public Result findList() {
        return questionService.getCompanyList();
    }

    /**
     * 意见反馈详情查询
     *
     * @param
     * @return
     */
    @GetMapping("/findQuestionDetail")
    @RequiresPermissions("question:findQuestionDetail")
    public Result findQuestionDetail(@RequestParam(value = "id") String id) {
        return questionService.findQuestionDetail(id);
    }
}
