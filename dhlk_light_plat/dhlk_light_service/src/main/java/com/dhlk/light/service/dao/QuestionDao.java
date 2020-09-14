package com.dhlk.light.service.dao;

import com.dhlk.entity.basicmodule.Tenant;
import com.dhlk.entity.light.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xkliu
 * @date 2020/6/5
 * <p>
 * 意见反馈Dao
 */
@Repository
public interface QuestionDao {

    /**
     * 根据Id获取Question
     *
     * @param id
     * @return
     */
    Question selectTenantById(@Param("id") String id);

    /**
     * 新增
     *
     * @param question
     * @return
     */
    Integer insert(Question question);

    /**
     * 修改
     *
     * @param question
     * @return
     */
    Integer update(Question question);

    /**
     * 删除,逻辑删除
     *
     * @param asList
     * @return
     */
    Integer delete(List<String> asList);

    /**
     * 列表查询
     *
     * @param sn
     * @param tenantId
     * @param startDate
     * @param endDate
     * @return
     */
    List<Question> findList(@Param("sn") String sn, @Param("tenantId") Integer tenantId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 获取所有的企业名称
     *
     * @return
     */
    List<Tenant> getCompanyList(Integer tenantId);

    /**
     * 获取在线收的详情信息
     *
     * @param id
     * @return
     */
    List<Question> findQuestionDetail(@Param("id") String id,@Param("attachPath") String attachPath);
}
