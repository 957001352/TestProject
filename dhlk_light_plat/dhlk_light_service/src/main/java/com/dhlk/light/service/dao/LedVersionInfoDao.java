package com.dhlk.light.service.dao;



import com.dhlk.entity.light.LedVersionInfo;
import com.dhlk.entity.light.OriginalPower;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @创建人 wangq
 * @创建时间 2020/6/30
 * @描述
 */

@Repository
public interface LedVersionInfoDao {


    /**
     * 新增
     *
     * @param versionInfo
     * @return
     */
    Integer insert(LedVersionInfo versionInfo);

    /**
     * 修改
     *
     * @param versionInfo
     * @return
     */
    Integer update(LedVersionInfo versionInfo);


    /**
     * 批量删除,物理删除
     *
     * @param asList
     * @return
     */
    Integer delete(List<String> asList);

    /**
     * 查询列表
     *
     * @param name
     * @param creator
     * @param createTime
     * @return
     */
    List<LedVersionInfo> findList(@Param("name") String name, @Param("creator") String creator, @Param("createTime") String createTime,@Param("version") String version,@Param("flag") Integer flag,@Param("id") Integer id);


}
