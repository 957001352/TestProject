package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.api.ApiClassify;
import com.dhlk.entity.basicmodule.ProductDevices;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Description 生产设备
 * @Author lpsong
 * @Date 2020/3/11
 */
@Repository
public interface ProductDevicesDao {

    Integer insert(ProductDevices productDevices);

    Integer update(ProductDevices productDevices);

    Integer delete(List<String> ids);

    List<ProductDevices> findList(@Param("name") String name, @Param("tenantId") Integer tenantId, @Param("attachPath") String attachPath);



    List<LinkedHashMap<String,String>> findClassifyByPruductId(Integer productId);

    List<ProductDevices> findTbIdsListbyIds(List<String> ids);

    ProductDevices findProductDevicesById(Integer id);

    Integer deleteById(Integer id);

    //查询设备是否与设备分类绑定 0为未绑定
    Integer isBoundClassify(String classifyId);

    Integer findDevicesCountByTenant(Integer tenantId);

    Integer findTenantById(Integer tenantId);

    List<ProductDevices> findProductDevicesByOrgId(@Param("orgId") String orgId, @Param("attachPath") String attachPath);

    /*
     * 判断生产设备名称是否重复
     * @return
     */
    Integer isRepeatName(ProductDevices productDevices);


    Integer isRepeatCode(String  code);


    Integer findProductDevicesCountByOrgId(Integer orgId);
}
