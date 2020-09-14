package com.dhlk.light.service.service.impl;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Tenant;
import com.dhlk.entity.light.Question;
import com.dhlk.enums.ResultEnum;
import com.dhlk.light.service.dao.QuestionDao;
import com.dhlk.light.service.service.QuestionService;
import com.dhlk.light.service.util.HeaderUtil;
import com.dhlk.util.AuthUserUtil;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.DateUtils;
import com.dhlk.utils.IDUtils;
import com.dhlk.utils.ResultUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author xkliu
 * @date 2020/6/5
 * <p>
 * 意见反馈service实现类
 */
@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AuthUserUtil athUser;

    @Autowired
    private HeaderUtil headerUtil;

    @Value("${attachment.path}")
    private String attachmentPath;

    @Override
    public Result save(Question question) {
        Integer flag = 0;

        //Question 对象为null的时候进行新增,否则修改
        if (CheckUtils.isNull(question.getId())) {
            question.setId(IDUtils.getId());
            question.setSn(DateUtils.getNowTime());
            question.setCreateUser(athUser.userId());
            question.setTenantId(headerUtil.tenantId());
            flag = questionDao.insert(question);
        } else {
            Question questionInfo = questionDao.selectTenantById(question.getId());
            if (questionInfo == null) {
                return ResultUtils.failure();
            }
            flag = questionDao.update(question);
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result findList(String sn, String startDate, String endDate, Integer pageNum, Integer pageSize) {
        if (!CheckUtils.checkId(pageNum) || !CheckUtils.checkId(pageSize)) {
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Question> questions = questionDao.findList(sn, headerUtil.tenantId(), startDate, endDate);
        PageInfo<Question> questionsPage = new PageInfo<>(questions);
        return ResultUtils.success(questionsPage);
    }

    @Override
    public Result delete(String ids) {
        if (!CheckUtils.isNull(ids)) {
            List<String> lists = Arrays.asList(ids.split(","));
            for (String str : lists) {
                Question question = questionDao.selectTenantById(str);
                if (question == null) {
                    return ResultUtils.error("该售后信息数据不存在");
                }
            }
            if (questionDao.delete(lists) > 0) {
                return ResultUtils.success();
            }
        }
        return ResultUtils.failure();
    }

    @Override
    public Result getCompanyList() {
        List<Tenant> tenants = questionDao.getCompanyList(headerUtil.tenantId());
        return ResultUtils.success(tenants);
    }

    @Override
    public Result findQuestionDetail(String id) {
        if(CheckUtils.isNull(id)){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        List<Question> question = questionDao.findQuestionDetail(id, attachmentPath);
        return ResultUtils.success(question);
    }


}
