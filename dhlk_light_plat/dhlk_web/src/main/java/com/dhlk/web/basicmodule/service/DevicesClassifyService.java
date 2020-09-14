package com.dhlk.web.basicmodule.service;

import com.dhlk.entity.basicmodule.DevicesClassify;
import com.dhlk.web.basicmodule.service.fbk.DevicesClassifyServiceFbk;
import com.dhlk.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 生产设备管理
 **/
@FeignClient(value = "basicmodule-service/devicesClassify", fallback = DevicesClassifyServiceFbk.class)
public interface DevicesClassifyService {

    /**
     * 新增/修改
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody DevicesClassify devicesClassify);
    /**
     * 删除
     * @param id
     * @return result
     */
    @GetMapping(value = "/delete")
    Result delete(@RequestParam(value = "id") String id);

    /**
     * 查询
     * @param name 设备分配名称
     */
    @GetMapping("/findList")
    Result findList(@RequestParam(value = "name", required = false) String name);
}
