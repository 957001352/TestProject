package com.dhlk.light.factory.dao;

import com.dhlk.entity.light.Led;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author xkliu
 * @date 2020/6/15
 * <p>
 * 灯Dao
 */
@Repository
public interface LedDao {
    /**
     * 删除区域,物理删除
     *
     * @return
     */
    Integer delete();

    /**
     * 批量插入
     *
     * @param leds
     * @return
     */
    Integer insertBatch(List<Led> leds);

    /**
     * 保存
     * @param led
     * @return
     */
    Integer save(Led led);

    /**
     * 修改
     * @param led
     * @return
     */
    Integer update(Led led);

    /**
     * 删除
     * @param id
     * @return
     */
    Integer deteleById(Integer id);

    Integer deleteLed(Integer id);


    List<Led> findLedsByArea(String id);

    List<Led> findList(@Param("sn") String sn, @Param("areaId") String areaId, @Param("switchId") String switchId, @Param("tenantId") Integer tenantId);

    /**
     * 根据组id查询灯
     */
    List<Led> findLedsBySwitchId(Integer switchId);


    /**
     * 查出所有的灯与开关的对应关系
     */
    List<LinkedHashMap> findGroupLedSns();

    Led findLed(Led led);

    /**
     * 物理删除
     *
     * @return
     */
    Integer deletes();

}