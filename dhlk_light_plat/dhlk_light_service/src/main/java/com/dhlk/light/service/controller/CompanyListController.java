package com.dhlk.light.service.controller;

import com.dhlk.domain.Result;
import com.dhlk.light.service.service.CompanyListService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xkliu
 * @date 2020/6/4
 * <p>
 * 企业列表控制器
 */
@RestController
@RequestMapping(value = "/companyList")
@Api(value = "CompanyListController", description = "企业列表管理")
public class CompanyListController {

    @Autowired
    private CompanyListService companyListService;

    /**
     * 企业列表查询
     * @param name
     * @param address
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/findCompanyList")
    @RequiresAuthentication
    public Result findCompanyList(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "address", required = false) String address,
                                  @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return companyListService.findCompanyList(name, address, pageNum, pageSize);
    }

    /**
     * 查询企业是否存在
     * @param id
     * @return
     */
    @GetMapping("/isCompanyExist")
    @RequiresAuthentication
    public Result isCompanyExist(@RequestParam(value = "id") Integer id) {
        return companyListService.isCompanyExist(id);
    }

}
