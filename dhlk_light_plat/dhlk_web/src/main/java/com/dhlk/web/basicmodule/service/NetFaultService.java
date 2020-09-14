package com.dhlk.web.basicmodule.service;

import com.dhlk.entity.basicmodule.NetFault;
import com.dhlk.web.basicmodule.service.fbk.NetFaultServiceFbk;
import com.dhlk.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/4/20
 */
@FeignClient(value = "basicmodule-service/netFault", fallback = NetFaultServiceFbk.class)
public interface NetFaultService {

    @PostMapping(value = "/save")
    Result save(@RequestBody NetFault netFault);



    @PostMapping(value = "/dealFault")
    Result dealFault(@RequestParam(value = "id") Integer id,
                            @RequestParam(value = "status") Integer status);
    /**
     *列表查询
     * @param status
     * @return
     */
    @GetMapping("/findList")
    Result findList(@RequestParam(value = "tbId", required = false) String tbId,
                    @RequestParam(value = "status", required = false) Integer status);
}
