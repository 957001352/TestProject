package com.dhlk.light.service.dao;

import com.dhlk.entity.light.QuestionAnswer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xkliu
 * @date 2020/6/8
 * <p>
 * 问题解决方案Dao
 */
@Repository
public interface QuestionAnswerDao {

    /**
     * 新增
     *
     * @param questionAnswer
     * @return
     */
    Integer insert(QuestionAnswer questionAnswer);

    /**
     * 物理删除
     *
     * @param asList
     * @return
     */
    Integer delete(List<String> asList);
}
