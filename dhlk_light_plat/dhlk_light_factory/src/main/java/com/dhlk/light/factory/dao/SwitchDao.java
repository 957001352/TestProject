package com.dhlk.light.factory.dao;

import com.dhlk.entity.light.Switch;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwitchDao {

    /**
     * 批量插入
     *
     * @param switchs
     * @return
     */
    Integer insertBatch(List<Switch> switchs);

    /**
     * 删除
     * @return
     */
    Integer delete();

    /**
     * 保存
     * @param swich
     * @return
     */
    Integer insert(Switch swich);


    /**
     * 查询
     * @return
     */
    List<Switch> findList();

    /**
     * 删除
     * @param id
     * @return
     */
    Integer deteleById(String id);


    /**
     * 根据Id获取Switch
     * @param id
     * @return
     */
    Switch getSwitchById(Integer id);
    /**
     * 根据区域id获取分组
     * @param areaId
     * @return
     */
    List<Switch> findListByAreaId(@Param("areaId") String areaId);
}
