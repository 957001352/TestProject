package com.dhlk.light.service.dao;

import com.dhlk.entity.api.ApiList;
import com.dhlk.entity.light.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 灯
 * @Author lpsong
 * @Date 2020/6/4
 */
@Repository
public interface LedDao {

    Integer insert(Led led);

    Integer update(Led led);

    Integer delete(Integer id);

    Integer deleteLed(Integer id);


    List<Led> findList(@Param("sn") String sn,@Param("areaId") String areaId,@Param("switchId") String switchId,@Param("tenantId") Integer tenantId);

    Integer findSnIsRepart(@Param("sn") String sn);

    //Integer insertPower(LedPower ledPower);

    Integer insertPower(List<LedPower> list);

    /**
     * 插入本地汇总的人感数据
     * @param list
     * @return
     */
    Integer insertPeopleFell(List<CloudPeopleFeelStatistics> list);


    Integer insertOnline(LedOnline ledOnline);

    Integer insertOnlineList(List<LedOnline> list);

    Integer setLedBrightness(@Param("ledIds")String[] ledIds, @Param("brightness")String brightness);

    /**
     * 查询区域内所有的灯
     */
    List<Led> findLedsByArea(String id);

    /**
     * 根据开关查所有的灯
     */
    List<Led> findLedsBySwitchId(String switchId);

    List<Led> findListByTenantId(@Param("tenantId") Integer tenantId);

    Integer findSnRepart(@Param("sn") String sn,@Param("id") Integer id);

    List<Led> findListByAreaId(@Param("areaId") String areaId);

    List<String> findSnListByTenantId(@Param("tenantId") Integer tenantId);

    List<String> findSnAll();

    Led findLed(Led led);
}