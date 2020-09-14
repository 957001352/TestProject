package com.dhlk.light.factory.service.impl;

import com.alibaba.fastjson.JSON;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Tenant;
import com.dhlk.entity.basicmodule.User;
import com.dhlk.entity.light.OriginalPower;
import com.dhlk.exceptions.MyException;
import com.dhlk.jwt.JWTUtil;
import com.dhlk.light.factory.dao.OriginalPowerDao;
import com.dhlk.light.factory.dao.TenantDao;
import com.dhlk.light.factory.dao.UserDao;
import com.dhlk.light.factory.service.DataSyncService;
import com.dhlk.light.factory.service.LoginService;
import com.dhlk.light.factory.util.HeaderUtil;
import com.dhlk.light.factory.util.LedConst;
import com.dhlk.service.RedisService;
import com.dhlk.systemconst.Const;
import com.dhlk.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserDao userDao;
    @Value("${cloud.baseUrl}")
    private String cloudBaseUrl;

    @Autowired
    private DataSyncService dataSyncService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private TenantDao tenantDao;
    @Autowired
    private OriginalPowerDao originalPowerDao;

    @Override
    public Result login(String loginName, String password, String tenantCode) {
        if (CheckUtils.isNull(loginName) || CheckUtils.isNull(password)) {
            return ResultUtils.error("参数错误");
        }

        if (CheckUtils.isNull(tenantCode)) {
            return ResultUtils.error("客户编码不能为空");
        }

        if (userDao.checkUserExist() <= 0) {

            String systemRunTime = String.valueOf(DateUtils.getLongCurrentTimeStamp());
            Map loginInfo = new HashMap();
            loginInfo.put("loginName", loginName);
            loginInfo.put("password", password);
            loginInfo.put("tenantCode",tenantCode);
            loginInfo.put("systemRunTime",systemRunTime);
            try {
                //登录云端获取token
                HttpClientResult httpClientResult = HttpClientUtils.doPost(cloudBaseUrl + "/app/appLogin", loginInfo);
                if (httpClientResult.getCode() != 200) {
                    return ResultUtils.error("登陆失败");
                }

                Map result = HttpClientUtils.resultToMap(httpClientResult);
                if(Integer.parseInt(String.valueOf(result.get("code"))) != 0){
                    return ResultUtils.error(result.get("msg")+"");
                }

                Map<String, Object> retrunInfo = (Map<String, Object>) result.get("data");
                String token = (String) retrunInfo.get("token");
                //下载云端数据
                dataSyncService.dataSync(token, tenantCode);

                //记录系统初始化时间，并将同步到云端

                Integer tenantId = tenantDao.findTenantId();
                OriginalPower originalPower = originalPowerDao.selectOriginalPowerByTenantId(tenantId);
                if(originalPower == null) {
                    OriginalPower power = new OriginalPower();
                    power.setSystemRunTime(systemRunTime);
                    power.setTenantId(tenantId);
                    originalPowerDao.insert(power);

                }else {
                    originalPowerDao.updateOriginalPower(null,tenantId,systemRunTime);
                }
            } catch (Exception e) {
                return ResultUtils.error("登陆失败");
            }
        }

        if(tenantDao.findTenantByCode(tenantCode) == null) return ResultUtils.error("客户编码错误");

        password = EncryUtils.md5(password, loginName);
        User loginUser = userDao.loginCheck(loginName, password);
        if (loginUser == null) return ResultUtils.error("用户名或密码不正确");
        if (loginUser.getStatus() == 1) return ResultUtils.error("用户被禁用");
        //获取加密token
        String token = JWTUtil.sign(loginName, password);

        //将用户的访问令牌存入redis中,自定义token前缀+token令牌作为key,用户信息作为value,设置过期时间
        String string = JSON.toJSONString(loginUser);
        redisService.set(Const.TOKEN_CACHE_ITEM_PREFIX+token,string,Const.TOKEN_LOSE_TIME);
        //返回前端需要数据
        Map map = new HashMap();
        map.put("token", token);
        map.put("loginUser", loginUser);

        Map tokenInfo = new HashMap();
        tokenInfo.put("token", token);

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        Future future = executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClientResult httpClientResult = HttpClientUtils.doPost(cloudBaseUrl + "/app/sycToken", tokenInfo);
                    String content = httpClientResult.getContent();
                    if(httpClientResult.getCode()==200){
                        Result result = JSON.parseObject(content, Result.class);
                        if(result.getCode()==0) log.info("连接云端成功");

                    }
                    executorService.shutdown();

                } catch (Exception e) {
                    log.error("连接云端失败");
                    executorService.shutdown();
                }
            }
        }, 0, LedConst.RECONCATTIME, TimeUnit.SECONDS);
        return ResultUtils.success(map);

    }

    @Override
    public Result logout() {
        try {
            HttpServletRequest request = HttpContextUtil.getRequest();
            String token = request.getHeader(Const.TOKEN_HEADER);
            //删除redis里缓存的token
            redisService.del(Const.TOKEN_CACHE_ITEM_PREFIX+token);
            return ResultUtils.success();
        } catch (MyException e) {
            return ResultUtils.failure();
        }
    }

    @Override
    public Result getTenantCode() {
        System.out.println("----------->"+System.currentTimeMillis());
        Tenant tenant = tenantDao.findTenantByCode(null);
        if(tenant == null){
            return ResultUtils.success("");
        }
        String[] s = tenant.getCode().split("_");
        return ResultUtils.success(s[2]);
    }

}
