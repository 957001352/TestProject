package com.dhlk.light.dao;

import com.dhlk.light.entity.LightTenant;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/25
 * Time:14:17
 * @Description:
 */
@Repository
public interface LightTenantDao {

    /**
     * 查询所有租户
     * @return
     */
    List<LightTenant> findAll();
}
