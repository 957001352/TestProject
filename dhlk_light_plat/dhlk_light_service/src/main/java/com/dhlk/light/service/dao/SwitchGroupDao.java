package com.dhlk.light.service.dao;

import com.dhlk.entity.light.SwitchGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xkliu
 * @date 2020/7/9
 */
@Repository
public interface SwitchGroupDao {

    /**
     * 根据Id获取Area对象
     * @param id
     * @return
     */
    SwitchGroup selectSwitchGroupById(@Param("id") Integer id);

    /**
     * 保存
     * @param switchGroup
     * @return
     */
    Integer insert(SwitchGroup switchGroup);

    /**
     * 修改
     *
     * @param switchGroup
     * @return
     */
    Integer update(SwitchGroup switchGroup);

    /**
     * 检查是否有重复的组ID名字
     * @param groupId
     * @return
     */
    Integer findSwitchGroupRepeat(@Param("groupId") Integer groupId);

    List<SwitchGroup> findList(List<String> groupIds);

    List<SwitchGroup> findSwitchGroupBySwitchId(@Param("switchId") Integer switchId);
}
