package com.dhlk.light.factory.service;

import com.dhlk.domain.Result;

/**
 * @author xkliu
 * @date 2020/8/10
 */
public interface OriginalPowerService {

    /**
     * 预设亮度设置
     * @return
     */
    Result preBrightness(Integer preBrightness);
}
