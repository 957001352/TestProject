package com.dhlk.web.basicmodule.service;

import com.dhlk.entity.basicmodule.DevicesAttrSet;
import com.dhlk.web.basicmodule.service.fbk.DevicesAttrSetServiceFbk;
import com.dhlk.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 生产设备管理
 **/
@FeignClient(value = "basicmodule-service/devicesAttrSet", fallback = DevicesAttrSetServiceFbk.class)
public interface DevicesAttrSetService {

    /**
     * 新增/修改
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody DevicesAttrSet devicesAttrSet);
    /**
     * 删除
     * @param id
     * @return result
     */
    @GetMapping(value = "/delete")
    Result delete(@RequestParam(value = "id") Integer id);

    /**
     * 查询
     * @param name 属性集合名称
     */
    @GetMapping("/findList")
    Result findList(@RequestParam(value = "name", required = false) String name);
}
