package com.dhlk.basicmodule.service.service.impl;

import com.dhlk.basicmodule.service.dao.HiveMetaDao;
import com.dhlk.basicmodule.service.service.HiveMetaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.dhlk.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dhlk.utils.ResultUtils;

import java.util.LinkedHashMap;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/26
 */
@Service
public class HiveMetaServiceImpl implements HiveMetaService {

    @Autowired
    private HiveMetaDao hiveMetaDao;
    @Override
    public Result findPageList(String table, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return ResultUtils.success(new PageInfo<LinkedHashMap<String,Object>>(hiveMetaDao.findList(table)));
    }

    @Override
    public Result findColumnList(String table) {
        return ResultUtils.success(hiveMetaDao.findColumnList(table));
    }
}