package com.dhlk.light.service.dao;

import com.dhlk.entity.basicmodule.Tenant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xkliu
 * @date 2020/6/4
 * <p>
 * 企业列表Dao
 */
public interface CompanyListDao {

    /**
     * 查询企业列表信息
     *
     * @param name
     * @param address
     * @return
     */
    List<Tenant> findCompanyList(@Param("name") String name, @Param("address") String address);

    /**
     * 查询企业是否已被删除
     * @param id
     * @return
     */
    Integer isCompanyExist(@Param("id") Integer id);

}
