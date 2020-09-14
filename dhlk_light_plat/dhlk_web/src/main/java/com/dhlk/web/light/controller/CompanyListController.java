package com.dhlk.web.light.controller;

import com.dhlk.domain.Result;
import com.dhlk.web.light.service.CompanyListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping(value = "/companylist")
@Api(description = "企业列表管理", value = "CompanyListController")
public class CompanyListController {

    @Autowired
    private CompanyListService companyListService;

    /**
     * 企业列表列表查询
     *
     * @param
     * @return
     */
    @ApiOperation("列表查询")
    @GetMapping("/findCompanyList")
    public Result findCompanyList(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "address", required = false) String address,
                                  @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return companyListService.findCompanyList(name, address, pageNum, pageSize);
    }

    /**
     * 查询企业是否已删除
     * @param id
     * @return
     */
    @ApiOperation("查询企业是否已删除")
    @GetMapping("/isCompanyExist")
    public Result isCompanyExist(@RequestParam(value = "id") Integer id) {
        return companyListService.isCompanyExist(id);
    }
}
