package com.dhlk.light.factory.service;


import com.dhlk.domain.Result;

public interface MonitoringLogService {
    /**
     * 列表查询
     */
    Result findList(String sn,String status,String startime,
                    String endtime,Integer pageNum,Integer pageSize);
}
