package com.dhlk.light.service.service;

import com.dhlk.domain.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xkliu
 * @date 2020/6/4
 * <p>
 * 企业列列表service
 */
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
    Result findCompanyList(String name,String address,Integer pageNum,Integer pageSize);

    /**
     * 查询企业是否存在
     * @param id
     * @return
     */
    Result isCompanyExist(Integer id);
}
