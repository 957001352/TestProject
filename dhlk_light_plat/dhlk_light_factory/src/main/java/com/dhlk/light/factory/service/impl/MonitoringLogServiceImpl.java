package com.dhlk.light.factory.service.impl;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Computer;
import com.dhlk.entity.light.MonitoringLog;
import com.dhlk.light.factory.dao.MonitoringLogDao;
import com.dhlk.light.factory.service.MonitoringLogService;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitoringLogServiceImpl implements MonitoringLogService {
    @Autowired
    private MonitoringLogDao monitoringLogDao;
    @Override
    public Result findList(String sn, String status, String startime,
                           String endtime,Integer pageNum,Integer pageSize) {
        if (CheckUtils.isIntNumeric(pageNum) && CheckUtils.isIntNumeric(pageSize)) {
            PageHelper.startPage(pageNum, pageSize);
            PageInfo<MonitoringLog> monitoringLogs = new PageInfo<>(monitoringLogDao.findList(sn,status,startime,endtime));
            return ResultUtils.success(monitoringLogs);
        } else return ResultUtils.success(monitoringLogDao.findList(sn,status,startime,endtime));
    }
}
