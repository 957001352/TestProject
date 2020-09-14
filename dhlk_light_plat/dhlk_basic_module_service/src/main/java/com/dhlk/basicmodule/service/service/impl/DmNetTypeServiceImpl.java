package com.dhlk.basicmodule.service.service.impl;

import com.dhlk.basicmodule.service.dao.DmNetTypeDao;
import com.dhlk.basicmodule.service.service.DmNetTypeService;
import com.dhlk.entity.dm.DmNetType;
import com.dhlk.domain.Result;
import com.dhlk.exceptions.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;

import java.util.Arrays;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/13
 */
@Service
public class DmNetTypeServiceImpl implements DmNetTypeService {
    @Autowired
    private DmNetTypeDao dmNetTypeDao;
    @Override
    public Result save(DmNetType dmNetType) throws MyException {
        if(CheckUtils.isNull(dmNetType.getName())){
            return  ResultUtils.error("名称不能为空");
        }
        if(dmNetTypeDao.isRepeatName(dmNetType)>0){
           return  ResultUtils.error("名称重复");
        }
        //新增
        Integer flag=0;
        if (CheckUtils.isNull(dmNetType.getId())) {
            flag=dmNetTypeDao.insert(dmNetType);
        }else{//修改
            flag=dmNetTypeDao.update(dmNetType);
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result delete(String ids) throws MyException {
        if (!CheckUtils.isNull(ids)) {
            if (dmNetTypeDao.delete(Arrays.asList(ids.split(","))) > 0) {
                return  ResultUtils.success();
            }
        }

        return ResultUtils.failure();
    }

    @Override
    public Result findList(String name) throws MyException {
        return ResultUtils.success(dmNetTypeDao.findList(name));
    }



}