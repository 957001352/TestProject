package com.dhlk.light.service.dao;

import com.dhlk.entity.light.Area;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xkliu
 * @date 2020/6/5
 * <p>
 * 施工区域Dao
 */
@Repository
public interface AreaDao {


    /**
     * 根据Id获取Area对象
     * @param id
     * @return
     */
    Area selectTenantById(@Param("id") String id);

    /**
     * 新增
     * @param area
     * @return
     */
    Integer insert(Area area);

    /**
     * 修改
     * @param area
     * @return
     */
    Integer update(Area area);

    /**
     * 列表查询
     * @param tenantId
     * @param attachmentPath
     * @return
     */
    List<Area> findList(@Param("tenantId") Integer tenantId,@Param("attachPath")String attachmentPath);

    /**
     * 物理删除
     * @param asList
     * @return
     */
    Integer delete(List<String> asList);

    /**
     * 查询有灯的区域
     */
    List<Area> findAreasByLed(@Param("tenantId")Integer tenantId,@Param("attachPath") String attachPath);

    /**
     * 根据关联数据删除附件
     * @param lists
     * @return
     */
    Integer deleteByDataId(List<String> lists);

    /**
     * 根据租户Id获取施工区域
     * @param tenantId
     * @return
     */
    List<Area> findListByTenantId(Integer tenantId,@Param("attachPath") String attachPath);

    Integer findAreaRepeat(@Param("name") String name,@Param("tenantId")Integer tenantId);
}
