package com.dhlk.light.service.service.impl;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Question;
import com.dhlk.entity.light.QuestionAnswer;
import com.dhlk.light.service.dao.QuestionAnswerDao;
import com.dhlk.light.service.dao.QuestionDao;
import com.dhlk.light.service.service.QuestionAnswerService;
import com.dhlk.util.AuthUserUtil;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.IDUtils;
import com.dhlk.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * @author xkliu
 * @date 2020/6/8
 * <p>
 * 问题解决方案service实现类
 */
@Service
@Transactional
public class QuestionAnswerServiceImpl implements QuestionAnswerService {

    @Autowired
    private AuthUserUtil athUser;

    @Autowired
    private QuestionAnswerDao questionAnswerDao;

    @Autowired
    private QuestionDao questionDao;


    @Override
    public Result save(QuestionAnswer questionAnswer) {
        String questionId = questionAnswer.getQuestionId();
        if (!CheckUtils.isNull(questionId)) {
            Question question = questionDao.selectTenantById(questionId);
            if(question == null){
                return ResultUtils.error("该问题信息不存在");
            }
        }
        questionAnswer.setId(IDUtils.getId());
        questionAnswer.setDealUser(athUser.userId());
        Integer flag = questionAnswerDao.insert(questionAnswer);
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result delete(String ids) {
        if (!CheckUtils.isNull(ids)) {
            if (questionAnswerDao.delete(Arrays.asList(ids.split(","))) > 0) {
                return ResultUtils.success();
            }
        }
        return ResultUtils.failure();
    }
}
