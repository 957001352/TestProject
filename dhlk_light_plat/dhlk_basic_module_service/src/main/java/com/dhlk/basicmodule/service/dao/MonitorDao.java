package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.basicmodule.MonitorDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitorDao {

    /**
     * 查询设备总数
     *
     * @param monitorDetail
     * @return
     */
    List<MonitorDetail> findDeviceCnt(MonitorDetail monitorDetail);

    /**
     * 查询BI接入总数
     *
     * @param monitorDetail
     * @return
     */
    List<MonitorDetail> findTotalSize(MonitorDetail monitorDetail);

    /**
     * 查询总的数据量
     *
     * @param monitorDetail
     * @return
     */
    List<MonitorDetail> findBiCnt(MonitorDetail monitorDetail);

    /**
     * 查询总的数据量
     *
     * @param monitorDetail
     * @return
     */
    List<MonitorDetail> findCountByProvince(MonitorDetail monitorDetail);

    /**
     * 查询总的数据量
     *
     * @param monitorDetail
     * @return
     */
    List<MonitorDetail> findTenantList(MonitorDetail monitorDetail);

    /**
     * 查询系統數據
     *
     * @param monitorDetail
     * @return
     */
    List<MonitorDetail> findOSData(MonitorDetail monitorDetail);

}