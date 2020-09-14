package com.dhlk.light.service.service.impl;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Tenant;
import com.dhlk.enums.ResultEnum;
import com.dhlk.light.service.dao.CompanyListDao;
import com.dhlk.light.service.service.CompanyListService;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;
import com.dhlk.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xkliu
 * @date 2020/6/4
 *
 * 企业列表service实现类
 */
@Service
@Transactional
public class CompanyListServiceImpl implements CompanyListService {

    @Autowired
    private CompanyListDao companyListDao;


    @Override
    public Result findCompanyList(String name, String address, Integer pageNum, Integer pageSize) {
        if (!CheckUtils.checkId(pageNum) || !CheckUtils.checkId(pageSize)) {
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Tenant> tenants = companyListDao.findCompanyList(name, address);
        PageInfo<Tenant> tenantsPage = new PageInfo<>(tenants);
        return ResultUtils.success(tenantsPage);
    }


    @Override
    public Result isCompanyExist(Integer id) {
        Boolean flag = false;
        if (!CheckUtils.checkId(id)) {
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        Integer result = companyListDao.isCompanyExist(id);
        if(result > 0){
            flag  = true;
        }
        return ResultUtils.success(flag);
    }
}
