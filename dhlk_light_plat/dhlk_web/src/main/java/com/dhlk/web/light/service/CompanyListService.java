package com.dhlk.web.light.service;

import com.dhlk.domain.Result;
import com.dhlk.web.light.service.fbk.CompanyListServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xkliu
 * @date 2020/6/4
 * <p>
 * 企业管理 service
 */
@FeignClient(value = "light-service/companyList", fallback = CompanyListServiceFbk.class)
public interface CompanyListService {

    /**
     * 企业列表查询
     *
     * @param name
     * @param address
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/findCompanyList")
    Result findCompanyList(@RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "address", required = false) String address,
                           @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize);

    /**
     * 查询企业是否存在
     * @param id
     * @return
     */
    @GetMapping("/isCompanyExist")
    Result isCompanyExist(@RequestParam(value = "id") Integer id);
}
