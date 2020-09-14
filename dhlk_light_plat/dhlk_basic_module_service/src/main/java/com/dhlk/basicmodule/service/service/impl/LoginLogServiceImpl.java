package com.dhlk.basicmodule.service.service.impl;

import com.dhlk.basicmodule.service.dao.LoginLogDao;
import com.dhlk.basicmodule.service.service.LoginLogService;
import com.dhlk.util.AuthUserUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.LoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dhlk.utils.ResultUtils;

@Service
@Transactional
public class LoginLogServiceImpl implements LoginLogService {
    @Autowired
    private LoginLogDao loginLogDao;
    @Autowired
    private AuthUserUtil authUserUtil;
    @Override
    public Result save(LoginLog loginLog) {
        if(loginLogDao.insert(loginLog)>0){
            return ResultUtils.success();
        }
        return ResultUtils.failure();
    }

    @Override
    public Result findPageList(String userName, String ip, String startTime, String endTime, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<LoginLog> pageInfo = new PageInfo<>(loginLogDao.findList(userName,ip,startTime,endTime,authUserUtil.tenantId()));
        return ResultUtils.success(pageInfo);
    }
}
