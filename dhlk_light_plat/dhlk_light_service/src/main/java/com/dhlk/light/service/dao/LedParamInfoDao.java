package com.dhlk.light.service.dao;

import com.dhlk.entity.light.LedParamInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @Description:    灯的参数信息
* @Author:         gchen
* @CreateDate:     2020/7/17 14:06
*/
@Repository
public interface LedParamInfoDao {
    /**
     * 新增
     * @param ledParamInfo
     */
    Integer insert(LedParamInfo ledParamInfo);
    /**
     * 修改
     * @param ledParamInfo
     */
    Integer update(LedParamInfo ledParamInfo);
    /**
     * 列表查询
     * @param sns
     */
    List<LedParamInfo> findList(@Param("sns") String[] sns);

    /**
     * 检查sn是否存在
     * @param sn
     */
    Integer isExistSn(@Param("sn")String sn);

    /**
     * 批量插入
     * @param list
     */
    Integer insertList(List<LedParamInfo> list);


    /**
     * 批量更新
     * @param list
     */
    Integer updateList(@Param("list") List<LedParamInfo> list);
}
