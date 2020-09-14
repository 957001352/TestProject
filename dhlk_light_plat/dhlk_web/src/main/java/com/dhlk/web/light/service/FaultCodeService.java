package com.dhlk.web.light.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Construction;
import com.dhlk.entity.light.FaultCode;
import com.dhlk.web.light.service.fbk.ConstructionServiceFbk;
import com.dhlk.web.light.service.fbk.FaultCodeServiceFBK;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @创建人 wangq
 * @创建时间 2020/6/5
 * @描述
 */
@FeignClient(value = "light-service/faultCode", fallback = FaultCodeServiceFBK.class)
public interface FaultCodeService {

    /**
     * 保存故障代码
     *
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody FaultCode faultCode);

    /**
     * 列表查询
     *
     * @param name
     * @param code
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/findList")
    Result findList(
                    @RequestParam(value = "name", required = false) String name,
                    @RequestParam(value = "code", required = false) String code,
                    @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                    @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    Result delete(@RequestParam(value = "ids") String ids);
}
