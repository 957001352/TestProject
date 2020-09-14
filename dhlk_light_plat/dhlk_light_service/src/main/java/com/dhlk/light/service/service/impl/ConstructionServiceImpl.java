package com.dhlk.light.service.service.impl;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Tenant;
import com.dhlk.entity.light.Construction;
import com.dhlk.enums.ResultEnum;
import com.dhlk.light.service.dao.ConstructionDao;
import com.dhlk.light.service.dao.TenantDao;
import com.dhlk.light.service.service.ConstructionService;
import com.dhlk.light.service.util.HeaderUtil;
import com.dhlk.util.AuthUserUtil;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;
import com.dhlk.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author xkliu
 * @date 2020/6/4
 * <p>
 * 施工信息service实现类
 */
@Service
@Transactional
public class ConstructionServiceImpl implements ConstructionService {

    @Autowired
    private ConstructionDao constructionDao;

    @Autowired
    private TenantDao tenantDao;

    @Autowired
    private HeaderUtil headerUtil;

    @Override
    public Result save(Construction construction) {
        Integer flag = 0;
        //保存或者修改施工信息前先查询企业是否已经被删除
        Tenant tenant = tenantDao.selectTenantById(headerUtil.tenantId());
        if(tenant == null){
            return ResultUtils.error("该企业不存在");
        }
        //Construction 的 id 为null的时候进行新增,否则修改
        if (CheckUtils.isNull(construction.getId())) {
            construction.setTenantId(headerUtil.tenantId());
            flag = constructionDao.insert(construction);
        } else {
            Construction constructionInfo = constructionDao.selectTenantById(construction.getId());
            if(constructionInfo == null){
                return ResultUtils.failure();
            }
            flag = constructionDao.update(construction);
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result findList(String implPeople, String startDate, String endDate, Integer pageNum, Integer pageSize) {
        if (!CheckUtils.checkId(pageNum) || !CheckUtils.checkId(pageSize)) {
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Construction> constructions = constructionDao.findList(implPeople, startDate, endDate,headerUtil.tenantId());
        PageInfo<Construction> constructionPage = new PageInfo<>(constructions);
        return ResultUtils.success(constructionPage);
    }

    @Override
    public Result delete(String ids) {
        if (!CheckUtils.isNull(ids)) {
            List<String> lists = Arrays.asList(ids.split(","));
            for (String str :lists) {
                Construction construction = constructionDao.selectTenantById(Integer.valueOf(str));
                if(construction == null){
                    return ResultUtils.error("该施工信息数据不存在");
                }
            }
            if (constructionDao.delete(lists) > 0) {
                return ResultUtils.success();
            }
        }
        return ResultUtils.failure();
    }
}
