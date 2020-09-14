package com.dhlk.light.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.QuestionAnswer;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xkliu
 * @date 2020/6/8
 * <p>
 * 题解决方案service
 */
public interface QuestionAnswerService {

    /**
     * 保存问题解决方案
     *
     * @param
     * @return
     */
    Result save(@RequestBody QuestionAnswer questionAnswer);

    /**
     * 物理删除
     * @param ids
     * @return
     */
    Result delete(String ids);
}
