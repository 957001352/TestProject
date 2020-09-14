package com.dhlk.basicmodule.service.service.impl;

import com.dhlk.basicmodule.service.dao.MonitorDao;
import com.dhlk.basicmodule.service.service.IMonitorService;
import com.dhlk.entity.basicmodule.MonitorDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MonitorServiceImpl implements IMonitorService {

    @Autowired
    private MonitorDao mdao;

    /**
     * 获取设备总数
     *
     * @param monitorDetail
     * @return
     */
    @Override
    public List<MonitorDetail> findDeviceCnt(MonitorDetail monitorDetail) {
        return mdao.findDeviceCnt(monitorDetail);
    }

    /**
     * 获取数据接入总量
     *
     * @param monitorDetail
     * @return
     */
    @Override
    public List<MonitorDetail> findTotalSize(MonitorDetail monitorDetail) {
        return mdao.findTotalSize(monitorDetail);
    }

    /**
     * 获取BI接入总数
     *
     * @param monitorDetail
     * @return
     */
    @Override
    public List<MonitorDetail> findBiCnt(MonitorDetail monitorDetail) {
        return mdao.findBiCnt(monitorDetail);
    }

    @Override
    public List<MonitorDetail> findCountByProvince(MonitorDetail monitorDetail) {
        return mdao.findCountByProvince(monitorDetail);
    }

    @Override
    public List<MonitorDetail> findTenantList(MonitorDetail monitorDetail) {
        return mdao.findTenantList(monitorDetail);
    }


    @Override
    public Map<String, Object> indexList(MonitorDetail monitorDetail) {
        Map<String, Object> resultMap = new HashMap<>();
        List<MonitorDetail> biList = this.findBiCnt(monitorDetail);
        List<MonitorDetail> deviceList = this.findDeviceCnt(monitorDetail);
        List<MonitorDetail> totalList = this.findTotalSize(monitorDetail);
        List<MonitorDetail> cntList = this.findCountByProvince(monitorDetail);
        List<MonitorDetail> tenList = this.findTenantList(monitorDetail);
        List<MonitorDetail> osList = mdao.findOSData(monitorDetail);
        resultMap.put("bi", biList);
        resultMap.put("device", deviceList);
        resultMap.put("totalList", totalList);
        resultMap.put("tenCnt", cntList);
        resultMap.put("tenList", tenList);
        resultMap.put("osList", osList);
        return resultMap;
    }
}
