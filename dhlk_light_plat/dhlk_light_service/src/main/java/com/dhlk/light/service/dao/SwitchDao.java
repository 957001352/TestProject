package com.dhlk.light.service.dao;

import com.dhlk.entity.light.Switch;
import com.dhlk.entity.light.SwitchGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwitchDao {
    Integer insert(Switch swich);

    Integer update(Switch swich);

    Integer delete(@Param("ids") String[] ids);

    List<Switch> findList(@Param("name") String name, @Param("tenantId") Integer tenantId);



    List<SwitchGroup> findGroupList(@Param("switchId") Integer switchId);

    /**
     * 检查sn是否重复
     *
     * @param swich
     * @return
     */
    Integer isRepeatSn(Switch swich);

    /**
     * 检查ip是否重复
     *
     * @param swich
     * @return
     */
    Integer isRepeatIp(Switch swich);

    /**
     * 根据租户Id获取Switch
     *
     * @param tenantId
     * @return
     */
    List<Switch> findListByTenantId(Integer tenantId);

    /**
     * 检查一体机是否与开关绑定
     * @param computerIds
     */
    Integer findListByComputerId(@Param("computerIds") String[] computerIds);

    /**
     * 检查开关名称是否重复
     * @param swich
     */
    Integer isRepeatName(Switch swich);


    /**
     * 检查开关是否存在
     */
    Integer isExist(Integer id);

    /**
     * 检查开关是否存在
     */
    List<Switch> findListByAreaId(@Param("areaId") String areaId,@Param("tenantId") Integer tenantId);
}
