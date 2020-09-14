package com.dhlk.light.factory.dao;

import com.dhlk.entity.light.MonitoringLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @Description:    监控日志
* @Author:         gchen
* @CreateDate:     2020/7/16 10:15
*/
@Repository
public interface MonitoringLogDao {
    /**
     * 新增
     * @param monitoringLog
     * @return
     */
    Integer insert(MonitoringLog monitoringLog);

    /**
     * 列表查询
     * @param sn status startime endtime
     */
    List<MonitoringLog> findList(@Param("sn") String sn,
                                 @Param("status") String status,
                                 @Param("startime") String startime,
                                 @Param("endtime") String endtime);

    /**
     * 批量插入
     * @param list
     * @return
     */
    Integer insertList(List<MonitoringLog> list);
}
