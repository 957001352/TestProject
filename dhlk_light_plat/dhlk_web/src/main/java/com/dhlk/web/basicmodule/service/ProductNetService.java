package com.dhlk.web.basicmodule.service;

import com.dhlk.web.basicmodule.service.fbk.ProductNetServiceFbk;
import com.dhlk.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/4/7
 */
@FeignClient(value = "basicmodule-service/productNet", fallback = ProductNetServiceFbk.class)
public interface ProductNetService {
    /**
     * 保存
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    public Result save(@RequestParam(value = "netId") String netId,
                       @RequestParam(value = "productIds", required = false) String productIds,
                       @RequestParam(value = "type") Integer type);
    /**
     * 删除
     * @param id
     * @return result
     */
    @GetMapping(value = "/delete")
    public Result delete(@RequestParam(value = "id") String id);

    /**
     * 列表查询
     * @return
     */
    @GetMapping("/findList")
    Result findList(@RequestParam(value = "name", required = false) String name,@RequestParam(value = "type") Integer type);

    @GetMapping("/findBiList")
    Result findBiList(@RequestParam(value = "netId", required = false) Integer netId);

    @GetMapping("/findComputerList")
    Result findComputerList(@RequestParam(value = "netId", required = false) Integer netId);


    @GetMapping("/findNotBiList")
    Result findNotBiList();


    @GetMapping("/findNotComputerList")
    Result findNotComputerList();
}
