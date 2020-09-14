package com.dhlk.web.light.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Question;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.QuestionService;
import org.springframework.stereotype.Service;

/**
 * @author xkliu
 * @date 2020/6/5
 * <p>
 * 意见反馈服务调用失败时的实现类
 */
@Service
public class QuestionServiceFbk implements QuestionService {

    @Override
    public Result save(Question question) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findList(String sn,String startDate, String endDate, Integer pageNum, Integer pageSize) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result getCompanyList() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findQuestionDetail(String id) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
