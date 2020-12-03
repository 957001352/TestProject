package com.dhlk.light.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.LoginLog;
import com.dhlk.entity.basicmodule.Tenant;
import com.dhlk.entity.basicmodule.User;
import com.dhlk.entity.light.OriginalPower;
import com.dhlk.enums.ResultEnum;
import com.dhlk.exceptions.MyException;
import com.dhlk.jwt.JWTUtil;
import com.dhlk.light.service.dao.LoginLogDao;
import com.dhlk.light.service.dao.OriginalPowerDao;
import com.dhlk.light.service.dao.TenantDao;
import com.dhlk.light.service.dao.UserDao;
import com.dhlk.light.service.service.AppLoginService;
import com.dhlk.service.RedisService;
import com.dhlk.systemconst.Const;
import com.dhlk.util.AuthUserUtil;
import com.dhlk.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
@Service
public class AppLoginServiceImpl implements AppLoginService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private LoginLogDao loginLogDao;
    @Autowired
    private TenantDao tenantDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AuthUserUtil authUserUtil;
    @Autowired
    private OriginalPowerDao originalPowerDao;


    @Override
    public Result appLogin(String loginName, String password,String tenantCode,String systemRunTime) {
        if(CheckUtils.isNull(loginName) || CheckUtils.isNull(password) || CheckUtils.isNull(tenantCode)){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        //校验客户编号
        Tenant tenantByCode = tenantDao.findTenantByCode(tenantCode);

        if(tenantByCode == null) return ResultUtils.error("客户编号不正确");

        password = EncryUtils.md5(password, loginName);

        User userBean = userDao.findUserByLoginName(loginName);
        if(userBean == null){
            return ResultUtils.error(ResultEnum.USERNAME_PASSWORDEROR.getStateInfo());
        }
        if (!userBean.getPassword().equals(password)) {
            return ResultUtils.error(ResultEnum.USERNAME_PASSWORDEROR.getStateInfo());
        }
        if(userBean.getTenantId() != null && !userBean.getTenantId().equals(tenantByCode.getId())){
            return ResultUtils.error(ResultEnum.USERNAME_PASSWORDEROR.getStateInfo());
        }

        if (userBean.getStatus().equals(Const.STATUS_BAN)){ //检查用户状态是否为禁用状态
            return ResultUtils.error(ResultEnum.BAN_USR.getStateInfo());
        }
        if(userBean.getTenantId() != null){
            if(userDao.checkIsDelete(userBean.getId())==null || userDao.checkIsDelete(userBean.getId()) == 2) {
                return ResultUtils.error("租户不存在或被删除");
            }
//            if(userDao.checkExpired(userBean.getId()) > 0) {
//                return ResultUtils.error("租户已过期");
//            }
        }


        //获取加密token
        String token = JWTUtil.sign(loginName, password);
        //将租户id插入缓存登录用户信息里
        userBean.setTenantId(tenantByCode.getId());
        //将用户的访问令牌存入redis中,自定义token前缀+token令牌作为key,用户信息作为value,设置过期时间
        String userInfo = JSON.toJSONString(userBean);
        redisService.set(Const.TOKEN_CACHE_ITEM_PREFIX+token,userInfo,Const.TOKEN_LOSE_TIME);

        //存入租户运行时长
        if(!CheckUtils.isNull(systemRunTime)){
            OriginalPower power = originalPowerDao.selectOriginalPowerByTenantId(tenantByCode.getId());
            if(power != null){
                originalPowerDao.setValues(null,null,null,tenantByCode.getId(),null,systemRunTime);
            } else {
                OriginalPower originalPower = new OriginalPower();
                originalPower.setSystemRunTime(systemRunTime);
                originalPower.setTenantId(tenantByCode.getId());
                originalPowerDao.insert(originalPower);
            }
        }
        //存储登录日志
        LoginLog log = new LoginLog();
        log.setUserId(userBean.getId());
        log.setIp(IPUtil.getIpAddr(HttpContextUtil.getRequest()));//设置ip
        log.setTenantId(authUserUtil.tenantId());
        loginLogDao.insert(log);

        //返回前端需要数据
        Map map = new HashMap();
        map.put("token",token);
        map.put("loginUser",userBean);
        return ResultUtils.success(map);
    }

    @Override
    public Result appLogout() {

        try{
            HttpServletRequest request = HttpContextUtil.getRequest();
            String token = request.getHeader(Const.TOKEN_HEADER);
            //删除redis里缓存的token
            redisService.del(Const.TOKEN_CACHE_ITEM_PREFIX+token);
            return ResultUtils.success();
        }catch (MyException e){
            return ResultUtils.failure();
        }
    }

    @Override
    public Result sycToken(String token) {
        if(CheckUtils.isNull(token)){
            return ResultUtils.error("token错误");
        }
        String username = JWTUtil.getUsername(token);
        User user = userDao.findUserByLoginName(username);
        String userInfo = JSON.toJSONString(user);
        if(JWTUtil.verify(token, username, user.getPassword())){
            redisService.set(Const.TOKEN_CACHE_ITEM_PREFIX + token, userInfo, Const.TOKEN_LOSE_TIME);
            return ResultUtils.success();
        }
        return ResultUtils.failure();
    }
}
