package com.dhlk.light.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.LedVersionInfo;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @创建人 wangq
 * @创建时间 2020/6/30
 * @描述
 */
public interface LedVersionInfoService {

    /**
     * 新增
     */

    Result save(@RequestBody LedVersionInfo ledVersionInfo);

    /**
     * 删除
     */

    Result delete(String snList);


    /**
     * 查询列表
     */

    Result findList(String name, String creator, String createTime,Integer pageNum, Integer pageSize);

    /**
     * 发送消息给MQTT给硬件，让硬件进行升级
     * 固件升级
     * @param InfoBox
     */
    Result firmwareUpgrade(InfoBox<LedVersionInfo> InfoBox);
}
