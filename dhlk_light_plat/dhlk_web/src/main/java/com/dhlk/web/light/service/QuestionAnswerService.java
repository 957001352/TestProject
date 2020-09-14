package com.dhlk.web.light.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.QuestionAnswer;
import com.dhlk.web.light.service.fbk.QuestionAnswerServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xkliu
 * @date 2020/6/8
 * <p>
 * 问题解决方案service
 */
@FeignClient(value = "light-service/questionAnswer", fallback = QuestionAnswerServiceFbk.class)
public interface QuestionAnswerService {

    /**
     * 保存问题解决方案
     *
     * @param questionAnswer
     * @return
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody QuestionAnswer questionAnswer);


    /**
     * 删除问题解决方案
     *
     * @param ids
     * @return
     */
    @GetMapping(value = "/delete")
    Result delete(@RequestParam(value = "ids") String ids);
}
