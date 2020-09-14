package com.dhlk.light.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.OriginalPower;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xkliu
 * @date 2020/6/10
 * <p>
 * 企业历史照明功率维护service
 */
public interface OriginalPowerService {

    /**
     * 保存企业历史照明功率维护
     *
     * @param
     * @return
     */
    Result save(@RequestBody OriginalPower originalPower);

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
     * 预设亮度设置
     * @return
     */
    Result preBrightness(Integer preBrightness);
}
