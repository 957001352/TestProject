package com.dhlk.light.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Computer;

/**
 * 照明一体机
 * gchen
 */
public interface ComputerService {
    /**
     * 新增/修改
     */
    Result save(Computer computer);
    /**
     * 批量删除
     * @param id
     */
    Result delete(String id);
    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     */
    Result findList(String name,Integer pageNum, Integer pageSize);

    /**
     * 添加代理
     */
    Result addReseller(String biProxyServerInfo,String mac);
}
