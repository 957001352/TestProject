package com.dhlk.light.service.dao;

import com.dhlk.entity.light.Construction;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xkliu
 * @date 2020/6/4
 * <p>
 * 施工信息Dao
 */
@Repository
public interface ConstructionDao {

    /**
     * 根据Id获取Construction对象
     *
     * @param id
     * @return
     */
    Construction selectTenantById(@Param("id") Integer id);

    /**
     * 新增
     *
     * @param construction
     * @return
     */
    Integer insert(Construction construction);

    /**
     * 修改
     *
     * @param construction
     * @return
     */
    Integer update(Construction construction);

    /**
     * 列表查询
     *
     * @param implPeople
     * @param startDate
     * @param endDate
     * @return
     */
    List<Construction> findList(@Param("implPeople") String implPeople, @Param("startDate") String startDate, @Param("endDate") String endDate,@Param("tenantId") Integer tenantId);

    /**
     * 批量删除,物理删除
     *
     * @param asList
     * @return
     */
    Integer delete(List<String> asList);


}
