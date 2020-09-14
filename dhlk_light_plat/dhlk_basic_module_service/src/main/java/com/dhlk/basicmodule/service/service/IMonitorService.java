package com.dhlk.basicmodule.service.service;

import com.dhlk.basicmodule.service.dao.MonitorDao;
import com.dhlk.entity.basicmodule.MonitorDetail;

import java.util.List;
import java.util.Map;

public interface IMonitorService {

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
     * 查询首页面的数据结果
     *
     * @param monitorDetail
     * @return
     */
    Map<String, Object> indexList(MonitorDetail monitorDetail);
}