package com.dhlk.web.light.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.OriginalPower;
import com.dhlk.web.light.service.fbk.OriginalPowerServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xkliu
 * @date 2020/6/10
 * <p>
 * 企业历史照明功率维护service
 */
@FeignClient(value = "light-service/originalPower", fallback = OriginalPowerServiceFbk.class)
public interface OriginalPowerService {

    /**
     * 保存
     *
     * @param originalPower
     * @return
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody OriginalPower originalPower);

    /**
     * 查询一条
     */
    @GetMapping("/findOne")
    Result findOne();

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    Result delete(@RequestParam(value = "ids") String ids);

    /**
     * 预设亮度设置
     *
     * @return
     */
    @GetMapping("/preBrightness")
    Result preBrightness(@RequestParam(value = "preBrightness") Integer preBrightness);
}
