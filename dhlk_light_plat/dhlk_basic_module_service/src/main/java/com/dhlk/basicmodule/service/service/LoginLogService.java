package com.dhlk.basicmodule.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.LoginLog;

/**
 * 用户登录日志
 */
public interface LoginLogService {
    /**
     * 新增
     */
    Result save(LoginLog loginLog);
    /**
     * 分页查询
     * @param userName 用户姓名
     * @param ip ip地址
     * @param startTime  开始日期
     * @param endTime  结束日期
     * @param pageNum
     * @param pageSize
     */
    Result findPageList(String userName,String ip,String startTime,String endTime,Integer pageNum, Integer pageSize);


}
