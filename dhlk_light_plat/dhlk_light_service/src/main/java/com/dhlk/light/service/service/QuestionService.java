package com.dhlk.light.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Question;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xkliu
 * @date 2020/6/5
 * <p>
 * 意见反馈service
 */
public interface QuestionService {

    /**
     * 保存意见反馈
     *
     * @param
     * @return
     */
    Result save(@RequestBody Question question);

    /**
     * 列表查询
     *
     * @return
     */
    Result findList(String sn, String startDate, String endDate, Integer pageNum, Integer pageSize);

    /**
     * 逻辑删除
     *
     * @param ids
     * @return
     */
    Result delete(String ids);

    /**
     * 获取企业名称
     *
     * @return
     */
    Result getCompanyList();

    /**
     * 意见反详情查询
     *
     * @param id
     * @return
     */
    Result findQuestionDetail(String id);
}
