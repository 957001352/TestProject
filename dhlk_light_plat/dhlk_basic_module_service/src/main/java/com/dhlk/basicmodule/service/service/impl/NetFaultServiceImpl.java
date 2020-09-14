package com.dhlk.basicmodule.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dhlk.basicmodule.service.dao.NetFaultDao;
import com.dhlk.basicmodule.service.service.NetFaultService;
import com.dhlk.entity.basicmodule.NetFault;
import com.dhlk.entity.basicmodule.User;
import com.dhlk.domain.Result;
import com.dhlk.util.AuthUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/4/20
 */
@Service
public class NetFaultServiceImpl implements NetFaultService {

    @Autowired
    private  NetFaultDao netFaultDao;
    @Autowired
    private AuthUserUtil authUserUtil;

    @Override
    public Result save(NetFault netFault) {
        //新增
        Integer flag=0;
        if (CheckUtils.isNull(netFault.getId())) {
            flag=netFaultDao.insert(netFault);
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result findList(String tbId,Integer status) {
        if(!CheckUtils.isNull(tbId)){
            return ResultUtils.success(netFaultDao.findList(tbId,status));
        }else{
            return ResultUtils.success();
        }
    }

    @Override
    public Result dealFault(Integer id,Integer status) {
        Integer userId=authUserUtil.userId();
        if(netFaultDao.dealFault(id,userId,status) > 0){
            return ResultUtils.success();
        }
        return ResultUtils.failure();
    }
}