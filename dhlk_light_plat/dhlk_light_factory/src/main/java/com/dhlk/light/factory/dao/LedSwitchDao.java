package com.dhlk.light.factory.dao;

import com.dhlk.entity.light.LedSwitch;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LedSwitchDao {
    /**
     * 批量插入
     *
     * @param ledSwitch
     * @return
     */
    Integer insertBatch(List<LedSwitch> ledSwitch);

    /**
     * 删除
     * @return
     */
    Integer delete();

    /**
     * 保存
     * @param ledSwitch
     * @return
     */
    Integer saveLedSwitch(LedSwitch ledSwitch);

    /**
     * 根据开关id删除某一个开关与灯关系
     *
     * @param switchId
     * @return
     */
    Integer deleteBySwitchId(Integer switchId);


}
