package com.dhlk.web.dm.service;

import com.dhlk.entity.dm.DmNetType;
import com.dhlk.web.dm.service.fbk.DmNetTypeServiceFbk;
import com.dhlk.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 网络设备分类
 */
@FeignClient(value = "basicmodule-service/dmNetType", fallback = DmNetTypeServiceFbk.class)
public interface DmNetTypeService {
    /**
     * 保存
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody DmNetType dmNetType);
    /**
     * 删除
     * @param ids
     * @return result
     */
    @GetMapping(value = "/delete")
    Result delete(@RequestParam(value = "ids") String ids);

    /**
     * 列表查询
     * @return
     */
    @GetMapping("/findList")
    Result findList(@RequestParam(value = "name", required = false) String name);

}
