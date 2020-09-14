package com.dhlk.light.factory.dao;

import com.dhlk.entity.light.Area;
import com.dhlk.entity.light.Led;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xkliu
 * @date 2020/6/15
 * <p>
 * 施工区域Dao
 */
@Repository
public interface AreaDao {

    /**
     * 删除区域,物理删除
     *
     * @return
     */
    Integer delete();

    /**
     * 批量插入
     *
     * @param user
     * @return
     */
    Integer insertBatch(List<Area> user);


    /**
     * 查询有灯的区域
     */
    List<Area> findAreasByLed(@Param("tenantId")Integer tenantId,@Param("attachPath")String attachPath);

    /**
     * 保存
     * @param area
     * @return
     */
    Integer save(Area area);

    /**
     * 修改
     * @param area
     * @return
     */
    Integer update(Area area);

    /**
     * 删除
     * @param id
     * @return
     */
    Integer deteleById(String id);

}
