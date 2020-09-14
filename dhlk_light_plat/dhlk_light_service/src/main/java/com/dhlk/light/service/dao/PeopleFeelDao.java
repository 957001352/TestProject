package com.dhlk.light.service.dao;

import com.dhlk.entity.light.OriginalPower;
import com.dhlk.entity.light.PeopleFeel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xkliu
 * @date 2020/6/30
 * <p>
 * 人感Dao
 */
@Repository
public interface PeopleFeelDao {

    /**
     * 根据Id获取PeopleFeel对象
     *
     * @param id
     * @return
     */
    PeopleFeel selectTenantById(@Param("id") Integer id);

    /**
     * 新增
     *
     * @param peopleFeel
     * @return
     */
    Integer insert(PeopleFeel peopleFeel);

    /**
     * 修改
     *
     * @param peopleFeel
     * @return
     */
    Integer update(PeopleFeel peopleFeel);


    /**
     * 批量删除,物理删除
     *
     * @param asList
     * @return
     */
    Integer delete(List<String> asList);

    /**
     * 根据tenantId物理删除
     *
     * @param tenantId
     * @return
     */
    Integer deleteByTenantId(@Param("tenantId") Integer tenantId);

    /**
     * PeopleFeel
     *
     * @param tenantId
     * @return
     */
    PeopleFeel selectByTenantId(@Param("tenantId") Integer tenantId);
}
