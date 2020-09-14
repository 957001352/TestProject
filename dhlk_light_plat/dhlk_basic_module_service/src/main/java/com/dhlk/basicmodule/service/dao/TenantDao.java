package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.basicmodule.Tenant;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 租户DAO
 */
@Repository
public interface TenantDao {

    Tenant selectTenantById(@Param("id") Integer id);

    Integer insert(Tenant tenant);

    Integer update(Tenant tenant);

    List<Tenant> findList(@Param("name") String name, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("expire") String expire, @Param("attachPath") String attachmentPath);

    Integer updateTenantStaus(Integer id);

    List<LinkedHashMap<String, Object>> exportExcel(@Param("expire") String expire, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("name") String name);

    Integer findTenantRepeat(@Param("name") String name,@Param("id") Integer id);

    Tenant findTenantByCode(@Param("code") String code);

    Tenant findFactoryByOrgId(@Param("tenantId") String tenantId);
}