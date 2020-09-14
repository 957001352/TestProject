package com.dhlk.light.service.dao;

import com.dhlk.domain.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/6/8
 */
@Repository
public interface LightQueryDao {


    List<String> ledIntallQuery(String province);

    List<LinkedHashMap<String,Object>> provinceQuery(String province);

    List<LinkedHashMap<String,Object>> lastCompanyQuery(@Param("province") String province, @Param("limit") Integer limit);

    List<LinkedHashMap<String,Object>> energyComRanking(@Param("province") String province, @Param("limit") Integer limit,@Param("dayNum") Integer dayNum);

    /**
     * 查询碳排放
     * @param runDays  灯运行的天数
     * @return
     */
    BigDecimal thriftCarbonEmission(@Param("runDays") Integer runDays,List<String> tenantIds);

    /**
     * 企业原始消耗能耗
     * @param runDays
     * @param tenantIds
     * @return
     */
    BigDecimal companyConsumeEnergy(@Param("runDays") Integer runDays,List<Integer> tenantIds);

    /**
     * 灯现在消耗能耗
     * @param tenantIds
     * @return
     */
    BigDecimal lightConsumeEnergy(@Param("tenantIds") List<Integer> tenantIds);

    /**
     * 查询灯运行天数
     * @return
     */
    Integer findLightRunDays();

    /**
     * 根据省份下所有的用户
     */
    List<Integer> findTenantIds(@Param("province") String province);
}
