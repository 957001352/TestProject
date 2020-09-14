package com.dhlk.light.service.dao;

import com.dhlk.entity.light.Led;
import com.dhlk.entity.light.LedSwitch;
import com.dhlk.entity.light.Switch;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;


@Repository
public interface LedSwitchDao {
    Integer saveSwitchLeds(@Param("switchId") Integer switchId,@Param("groupId") Integer groupId, @Param("leds") List<Led> leds);

    /**
     * 根据开关id删除多个灯与开关关系
     *
     * @param switchIds
     * @return
     */
    Integer deleteBySwitchIds(@Param("switchIds") String[] switchIds);

    /**
     * 根据开关id删除某一个开关与灯关系
     *
     * @param switchId
     * @return
     */
    Integer deleteBySwitchId(Integer switchId);

    /**
     * 检查开关与灯是否已绑定
     */
    Integer checkSwitchBond(@Param("ids") String[] ids);

    /**
     * 根据租户Id获取LedSwitch
     *
     * @param switchId
     * @return
     */
    List<LedSwitch> findListBySwitchId(Integer switchId);


    /**
    * 判断灯是否已经被不同的组id绑定
     * @param groupNo
     * @param leds
    * @return
    */
    List<Switch> findGroupIdIsDiffrentBySn(@Param("groupNo") Integer groupNo, @Param("leds") List<Led> leds);

    /**
     * 根据灯id查询是否之前绑定了组id
     * @return
     */
    LedSwitch findGroupIdByLedId(Integer ledId);


    /**
     * 保存
     * @return
     */
    Integer saveLedSwitch(LedSwitch ledSwitch);
}
