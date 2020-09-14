package com.dhlk.light.service.dao;

import com.dhlk.entity.light.Area;
import com.dhlk.entity.light.SyncDataResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gchen
 * @date 2020/8/12
 * <p>
 * 云端同步到本地的数据Dao
 */
@Repository
public interface SyncDataDao {
    /**
     * 新增
     * @param syncDataResult
     */
    Integer insert(SyncDataResult syncDataResult);

    /**
     * 物理删除
     * @param id
     */
    Integer delete(@Param("id") Integer id);

}
