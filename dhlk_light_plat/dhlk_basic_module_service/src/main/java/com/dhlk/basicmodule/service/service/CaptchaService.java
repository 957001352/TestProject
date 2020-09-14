package com.dhlk.basicmodule.service.service;

import com.dhlk.entity.basicmodule.ImageVerificationDto;
import com.dhlk.entity.basicmodule.ImageVerificationVo;
import com.dhlk.exceptions.MyException;

/**
 * description:  验证码业务处理类
 * version:  1.0
 * @Author: wangqiang
 * @Date: 2020/08/21 17:16
 */
public interface CaptchaService {

    /**
     * 根据类型获取验证码
     * @param imageVerificationDto  图片类型dto
     * @return  图片验证码
     * @throws MyException 获取图片验证码异常
     */
    ImageVerificationVo selectImageVerificationCode(ImageVerificationDto imageVerificationDto) throws MyException;

    /**
     * 校验图片验证码
     * @param x x轴坐标
     * @param y y轴坐标
     * @return 校验结果
     * @throws MyException 校验图片验证码异常
     */
    boolean checkVerificationResult(String redisKey,String x, String y) throws MyException;
}
