package com.dhlk.web.light.service;

/**
 * @创建人 wangq
 * @创建时间 2020/6/30
 * @描述
 */

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.LedVersionInfo;
import com.dhlk.web.light.service.fbk.LedVersionInfoServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "light-service/ledVersionInfo", fallback = LedVersionInfoServiceFbk.class)
public interface LedVersionInfoService {

    /**
     * 保存灯版本信息
     *
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody LedVersionInfo ledVersionInfo);


    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    Result delete(@RequestParam(value = "ids") String ids);

    /**
     * 查询灯版本信息列表
     * @param name
     * @param creator
     * @param createTime
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/findList")
    Result findList(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "creator", required = false) String creator,
            @RequestParam(value = "createTime", required = false) String createTime,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize);


    /**
     * 固件升级
     */
    @PostMapping(value = "/firmwareUpgrade")
    Result firmwareUpgrade(@RequestBody InfoBox<LedVersionInfo> infoBox);
}
