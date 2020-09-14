package com.dhlk.basicmodule.service.service.impl;

import com.dhlk.basicmodule.service.dao.MetaTableDao;
import com.dhlk.basicmodule.service.service.MetaTableService;
import com.dhlk.entity.hive.MetaTable;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.dhlk.domain.Result;
import com.dhlk.exceptions.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;

import java.util.Arrays;

/**
 * @Description hive元数据管理
 * @Author lpsong
 * @Date 2020/3/16
 */
@Service
public class MetaTableServiceImpl implements MetaTableService {
    @Autowired
    private MetaTableDao metaTableDao;
    @Override
    public Result save(MetaTable metaTable) throws MyException {
        //新增
        Integer flag=0;
        if (CheckUtils.isNull(metaTable.getId())) {
            flag=metaTableDao.insert(metaTable);
        }else{//修改
            flag=metaTableDao.update(metaTable);
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result delete(String ids) throws MyException {
        if (!CheckUtils.isNull(ids)) {
            if (metaTableDao.delete(Arrays.asList(ids.split(","))) > 0) {
                return  ResultUtils.success();
            }
        }
        return ResultUtils.failure();
    }

    @Override
    public Result findPageList(Integer pageNum, Integer pageSize) throws MyException {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<MetaTable> pageInfo = new PageInfo<MetaTable>(metaTableDao.findList());
        return ResultUtils.success(pageInfo);
    }

}