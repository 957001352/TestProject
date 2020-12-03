package com.dhlk.basicmodule.service.service;

import com.dhlk.domain.Result;

/**
 * @Description 用户登录
 * @Author lpsong
 * @Date 2020/3/11
 */
public interface LoginService {
    /**
    * 登录
    * @author      gchen
    * @param loginName password
    * @return Result
    * @date        2020/3/20 15:47
    */
    Result login(String loginName, String password,String redisKey,String x,String y);
    /**
     * 登出
     * @author      gchen
     * @param
     * @return Result
     * @date        2020/3/20 15:47
     */
    Result logout();
    /**
     * 生成base64图片验证码
     * @author  jlv
     * @param
     * @return  Result
     * @date        2020/3/31 17:04
     */
    Result kaptcha() ;
    /**
     * 获取tb登录token
     * @author  jlv
     * @return  Result
     * @date        2020/4/21 11:04
     */

    public Result getTbToken();


    /**
     * 获取E2C登录token
     * @author  gchen
     * @return  Result
     */
    Result getToken();

    /**
     * 校验token是否正确
     */
    Result checkToken();
}
