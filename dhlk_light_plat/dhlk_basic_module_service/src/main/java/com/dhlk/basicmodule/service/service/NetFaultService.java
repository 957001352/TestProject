package com.dhlk.basicmodule.service.service;

import com.dhlk.entity.basicmodule.NetFault;
import com.dhlk.domain.Result;

/**
 * @Description 网络设备故障信息
 * @Author lpsong
 * @Date 2020/4/20
 */
public interface NetFaultService {
    /**
     * 新增/修改
     */
    Result save(NetFault netFault);
    /**
     * 列表查询
     * @param status
     */
    Result findList(String tbId,Integer status);
    /**
     * 故障处理
     * @param status
     */
    Result dealFault(Integer id,Integer status);
}
