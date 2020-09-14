package com.dhlk.web.dm.service;

import com.dhlk.entity.dm.DmClassifyType;
import com.dhlk.web.dm.service.fbk.DmClassifyTypeServiceFbk;
import com.dhlk.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 设备类型分类
 */
@FeignClient(value = "basicmodule-service/dmClassifyType", fallback = DmClassifyTypeServiceFbk.class)
public interface DmClassifyTypeService {

    /**
     * 保存
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody DmClassifyType dmClassifyType);
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
