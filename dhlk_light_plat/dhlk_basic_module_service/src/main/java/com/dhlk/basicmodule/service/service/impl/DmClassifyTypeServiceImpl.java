package com.dhlk.basicmodule.service.service.impl;

import com.dhlk.basicmodule.service.dao.DmClassifyTypeDao;
import com.dhlk.basicmodule.service.service.DmClassifyTypeService;
import com.dhlk.entity.dm.DmClassifyType;
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
public class DmClassifyTypeServiceImpl implements DmClassifyTypeService {
    @Autowired
    private DmClassifyTypeDao dmClassifyTypeDao;
    @Override
    public Result save(DmClassifyType dmClassifyType) throws MyException {
        if(CheckUtils.isNull(dmClassifyType.getName())){
            return  ResultUtils.error("名称不能为空");
        }
        if(dmClassifyTypeDao.isRepeatName(dmClassifyType)>0){
           return  ResultUtils.error("名称重复");
        }
        //新增
        Integer flag=0;
        if (CheckUtils.isNull(dmClassifyType.getId())) {
            flag=dmClassifyTypeDao.insert(dmClassifyType);
        }else{//修改
            flag=dmClassifyTypeDao.update(dmClassifyType);
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result delete(String ids) throws MyException {
        if (!CheckUtils.isNull(ids)) {
            if (dmClassifyTypeDao.delete(Arrays.asList(ids.split(","))) > 0) {
                return  ResultUtils.success();
            }
        }

        return ResultUtils.failure();
    }

    @Override
    public Result findList(String name) throws MyException {
        return ResultUtils.success(dmClassifyTypeDao.findList(name));
    }



}