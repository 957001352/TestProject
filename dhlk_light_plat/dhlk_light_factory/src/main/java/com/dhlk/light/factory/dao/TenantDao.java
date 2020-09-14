package com.dhlk.light.factory.dao;

import com.dhlk.entity.basicmodule.Tenant;
import com.dhlk.entity.basicmodule.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 租户DAO
 */
@Repository
public interface TenantDao {

    /**
     * 添加租户
     * @param tenant
     * @return
     */
    Integer insert(Tenant tenant);

    /**
     * 批量插入
     *
     * @param user
     * @return
     */
    Integer insertBatch(List<Tenant> user);

    /**
     * 物理删除
     *
     * @return
     */
    Integer delete();

    Tenant findTenantByCode(@Param("code") String code);

    /**
     * 查询所有租户
     * @return
     */
    List<Tenant> findAll();


    Integer findTenantIsExitsById(@Param("id") Integer id);


    Integer  findTenantId();
}