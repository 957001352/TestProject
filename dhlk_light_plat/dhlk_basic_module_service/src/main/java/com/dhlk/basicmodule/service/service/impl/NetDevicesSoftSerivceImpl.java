package com.dhlk.basicmodule.service.service.impl;

import com.dhlk.basicmodule.service.dao.NetDevicesSoftDao;
import com.dhlk.basicmodule.service.service.NetDevicesSoftService;
import com.dhlk.entity.basicmodule.NetDevicesSoft;
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
 * @Description 网络设备管理
 * @Author lpsong
 * @Date 2020/3/12
 */
@Service
public class NetDevicesSoftSerivceImpl implements NetDevicesSoftService {
    @Autowired
    private NetDevicesSoftDao netDevicesSoftDao;

    @Override
    public Result save(NetDevicesSoft netDevicesSoft) throws MyException {
        //新增
        Integer flag=0;
        if (CheckUtils.isNull(netDevicesSoft.getId())) {
            flag=netDevicesSoftDao.insert(netDevicesSoft);
        }else{//修改
            flag=netDevicesSoftDao.update(netDevicesSoft);
        }
       return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result delete(String ids) throws MyException{
        if (!CheckUtils.isNull(ids)) {
            if (netDevicesSoftDao.delete(Arrays.asList(ids.split(","))) > 0) {
                return  ResultUtils.success();
            }
        }

        return ResultUtils.failure();
    }

    @Override
    public Result findPageList(Integer pageNum, Integer pageSize) throws MyException{
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<NetDevicesSoft> pageInfo = new PageInfo<NetDevicesSoft>(netDevicesSoftDao.findList());
        return ResultUtils.success(pageInfo);
    }

}