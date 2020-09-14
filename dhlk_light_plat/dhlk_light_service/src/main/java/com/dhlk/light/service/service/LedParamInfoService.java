package com.dhlk.light.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.LedParamInfo;

import java.util.List;

/**
 * 灯参数列表
 */
public interface LedParamInfoService {
    /**
     * 新增
     * @param ledParamInfo
     */
    Result save(LedParamInfo ledParamInfo);
    /**
     * 修改
     * @param ledParamInfo
     */
    Result update(LedParamInfo ledParamInfo);
    /**
     * 列表查询
     * @param sns
     */
    Result findList(String sns);

    /**
     * 检查sn是否存在
     * @param sn
     */
    Integer isExistSn(String sn);

    /**
     * 批量插入
     * @param list
     */
    Integer insertList(List<LedParamInfo> list);

    /**
     * 批量更新
     * @param ledParamInfos
     */
    Integer updateList(List<LedParamInfo> ledParamInfos);


    /**
     * 参数列表重载
     * @param infoBox
     */
    Result refreshParam(InfoBox<String> infoBox);
}
