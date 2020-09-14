package com.dhlk.web.basicmodule.service;

import com.dhlk.entity.basicmodule.NetDevicesSoft;
import com.dhlk.web.basicmodule.service.fbk.NetDevicesSoftServiceFbk;
import com.dhlk.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 软件和网络设备关系管理
 */
@FeignClient(value = "basicmodule-service/netDevicesSoft", fallback = NetDevicesSoftServiceFbk.class)
public interface NetDevicesSoftService {
        /**
         * 保存
         * @param
         * @return
         */
        @PostMapping(value = "/save")
        Result save(@RequestBody NetDevicesSoft netDevicesSoft);
        /**
         * 删除
         * @param ids
         * @return result
         */
        @GetMapping(value = "/delete")
        Result delete(@RequestParam(value = "id") String ids);

        /**
         * 列表查询
         * @param pageNum
         * @param pageSize
         * @return
         */
        @GetMapping("/findPageList")
        Result findPageList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize);

}
