package com.dhlk.light.factory.service;

import com.dhlk.domain.Result;

public interface GroupService {
    /**
     * 查询
     */
    Result findList();

    /**
     * 查询灯状态是否改变
     */
    Result ledStatusIsChange(String sns,String status);

    /**
     * 查询所有分组
     */
    Result findGroupList(String areaId);
}
