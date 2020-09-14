package com.dhlk.web.light.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.QuestionAnswer;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.QuestionAnswerService;
import org.springframework.stereotype.Service;

/**
 * @author xkliu
 * @date 2020/6/8
 * <p>
 * 问题解决方案服务调用失败时的实现类
 */
@Service
public class QuestionAnswerServiceFbk implements QuestionAnswerService {
    @Override
    public Result save(QuestionAnswer questionAnswer) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
