package com.dhlk.basicmodule.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.dhlk.basicmodule.service.dao.LoginLogDao;
import com.dhlk.basicmodule.service.dao.UserDao;
import com.dhlk.basicmodule.service.service.CaptchaService;
import com.dhlk.basicmodule.service.service.LoginService;
import com.dhlk.basicmodule.service.service.UserService;
import com.dhlk.basicmodule.service.util.RestTemplateUtil;
import com.dhlk.entity.basicmodule.LoginLog;
import com.dhlk.entity.basicmodule.User;
import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import com.dhlk.exceptions.MyException;
import com.dhlk.jwt.JWTUtil;
import com.dhlk.util.AuthUserUtil;
import com.dhlk.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import com.dhlk.service.RedisService;
import com.dhlk.systemconst.Const;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserService userService;
    @Autowired
    private LoginLogDao loginLogDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RestTemplateUtil restTemplateUtil;
    @Autowired
    private AuthUserUtil authUserUtil;
    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserDao userDao;

    @Override
    public Result login(String loginName, String password,String redisKey,String x,String y) {

       /* if(CheckUtils.isNull(loginName) || CheckUtils.isNull(password) || CheckUtils.isNull(kaptcha)){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        Boolean flag = redisService.hasKeyAndItem("loginKaptcha", kaptcha.toLowerCase());
        if(CheckUtils.isNull(flag) || !flag){
            return ResultUtils.error(ResultEnum.KAPTCHA_ERR.getStateInfo());
        }*/
        if(CheckUtils.isNull(loginName) || CheckUtils.isNull(password) || CheckUtils.isNull(x) || CheckUtils.isNull(y) || CheckUtils.isNull(redisKey)){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }

        if(!redisService.hasKey(redisKey)){
            return ResultUtils.error(ResultEnum.SLIDE_KAPTCHA_INVALID);
        }
        boolean flag = captchaService.checkVerificationResult(redisKey,x, y);
        if(CheckUtils.isNull(flag) || !flag){
            return ResultUtils.error(ResultEnum.SLIDE_KAPTCHA_ERR.getStateInfo());
        }

        password = EncryUtils.md5(password, loginName);

        User userBean = userService.getUserByLoginName(loginName);
        if(userBean == null){
            return ResultUtils.error(ResultEnum.USERNAME_PASSWORDEROR.getStateInfo());
        }
        if (!userBean.getPassword().equals(password)) {
            return ResultUtils.error(ResultEnum.USERNAME_PASSWORDEROR.getStateInfo());
        }
        if (userBean.getStatus()== Const.STATUS_BAN){ //检查用户状态是否为禁用状态
            return ResultUtils.error(ResultEnum.BAN_USR.getStateInfo());
        }
        if(userBean.getTenantId() != null){
            if(userService.checkIsDelete(userBean.getId())==null || userService.checkIsDelete(userBean.getId()) == 2) {
                return ResultUtils.error("租户不存在或被删除");
            }
//            if(userService.checkExpired(userBean.getId()) > 0) {
//                return ResultUtils.error("租户已过期");
//            }
        }


        //获取加密token
        String token = JWTUtil.sign(loginName, password);
        //将用户的访问令牌存入redis中,自定义token前缀+token令牌作为key,用户信息作为value,设置过期时间
        String userInfo = JSON.toJSONString(userBean);
        redisService.set(Const.TOKEN_CACHE_ITEM_PREFIX+token,userInfo,Const.TOKEN_LOSE_TIME);

        //将用户权限缓存到redis中  用户id为key，权限集合为value
        Map<String, Set> permissions = userService.getPermissionsByLoginName(userBean);
        String permissionsJson = JSON.toJSONString(permissions.get("perms"));
        redisService.set(Const.PERMISSIONS_CACHE_ITEM_PREFIX+userBean.getLoginName(),permissionsJson,Const.TOKEN_LOSE_TIME);

        //存储登录日志
        LoginLog log = new LoginLog();
        log.setUserId(userBean.getId());
        log.setIp(IPUtil.getIpAddr(HttpContextUtil.getRequest()));//设置ip
        log.setTenantId(authUserUtil.tenantId());
        loginLogDao.insert(log);

        //返回前端需要数据
        Map map = new HashMap();
        map.put("permissions",permissions.get("codes"));
        map.put("token",token);
        map.put("loginUser",userBean);
        return ResultUtils.success(map);
    }


    @Override
    public Result logout() {
        
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

    public Result kaptcha() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            String code = KaptchaUtil.generateTextCode(KaptchaUtil.TYPE_ALL_MIXED, 4, "dhlktech");

            code = code.toLowerCase();

            BufferedImage img = KaptchaUtil.generateImageCode(code, 100, 25,7, true, Color.WHITE, Color.BLACK, null);
            Map<String, String> map = new HashMap<>(2);
            ImageIO.write(img, "jpg", outputStream);
            byte[] base64Img = Base64Utils.encode(outputStream.toByteArray());
            String base64Str="data:image/jpeg;base64," + new String(base64Img).replaceAll("\n", "");
            map.put("base64Str", base64Str);
            map.put("code", code);
            redisService.hset("loginKaptcha",code,base64Str,60);//60秒验证码失效
            return ResultUtils.success(map);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtils.failure();
        }
    }

    @Override
    public Result getTbToken() {
        String tbJwtToken = restTemplateUtil.getTbJwtToken();
        String res = tbJwtToken.replace("Bearer ", "");
        return ResultUtils.success(res);
    }

    @Override
    public Result getToken() {
        String header = HttpContextUtil.getRequest().getHeader(Const.TOKEN_HEADER);
        return ResultUtils.success(header);
    }


    @Override
    public Result checkToken() {
        String token = request.getHeader(Const.TOKEN_HEADER);
        if(CheckUtils.isNull(token)){
            return ResultUtils.error("token错误");
        }
        String username = JWTUtil.getUsername(token);
        if(CheckUtils.isNull(username)){
            return ResultUtils.error("token错误");
        }
        User user = userDao.findUserByLoginName(username);
        if(!CheckUtils.isNull(user)){
            if(redisService.hasKey(Const.TOKEN_CACHE_ITEM_PREFIX+token)){
                return ResultUtils.success();
            }
            if(JWTUtil.verify(token, username, user.getPassword())){
                String userInfo = JSON.toJSONString(user);
                redisService.set(Const.TOKEN_CACHE_ITEM_PREFIX + token, userInfo, Const.TOKEN_LOSE_TIME);
                return ResultUtils.success();
            }
        }
        return ResultUtils.failure();
    }
}
