package com.dhlk.light.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.Intensity;
import com.dhlk.entity.light.IntensityInfo;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xkliu
 * @date 2020/6/30
 */
public interface IntensityService {

    /**
     * 保存
     *
     * @param
     * @return
     */
    Result save(@RequestBody Intensity intensity);

    /**
     * 单条查询
     */
    Result findOne();

    /**
     * 物理删除
     *
     * @param ids
     * @return
     */
    Result delete(String ids);

    /**
     * 光感控制
     */
    Result intensityContro(InfoBox<IntensityInfo> intensityInfo);

    /**
     * 记忆光感强度
     * @return
     */
    Result memoryIntensity();
}
