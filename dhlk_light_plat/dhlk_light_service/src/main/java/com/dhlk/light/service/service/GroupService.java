package com.dhlk.light.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Switch;

public interface GroupService {
    /**
     * 保存/修改
     */
    Result save(Switch swich);

    /**
     * 删除分组
     */
    Result delete(String id);

    /**
     * 查询
     */
    Result findList(String areaId);
}
