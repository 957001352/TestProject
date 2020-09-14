package com.dhlk.basicmodule.service.service.impl;

import com.dhlk.basicmodule.service.dao.DataBrokerDao;
import com.dhlk.basicmodule.service.service.DataBrokerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.DataBroker;
import com.dhlk.exceptions.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;

import java.util.Arrays;

/**
 * @Description mosquito管理
 * @Author lpsong
 * @Date 2020/3/16
 */
@Service
public class DataBrokerServiceImpl implements DataBrokerService {
    @Autowired
    private DataBrokerDao dataBrokerDao;
    @Override
    public Result save(DataBroker dataBroker) throws MyException {
        //新增
        Integer flag=0;
        if (CheckUtils.isNull(dataBroker.getId())) {
            flag=dataBrokerDao.insert(dataBroker);
        }else{//修改
            flag=dataBrokerDao.update(dataBroker);
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result delete(String ids) throws MyException{
        if (!CheckUtils.isNull(ids)) {
            if (dataBrokerDao.delete(Arrays.asList(ids.split(","))) > 0) {
                return  ResultUtils.success();
            }
        }
        return ResultUtils.failure();
    }

    @Override
    public Result findPageList(Integer pageNum, Integer pageSize) throws MyException{
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<DataBroker> pageInfo = new PageInfo<DataBroker>(dataBrokerDao.findList());
        return ResultUtils.success(pageInfo);
    }
}