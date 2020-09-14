package com.dhlk.web.basicmodule.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.ImageVerificationDto;
import com.dhlk.exceptions.MyException;
import com.dhlk.web.basicmodule.service.fbk.CaptchaServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * description:  验证码业务处理类
 * version:  1.0
 * date: 2020/08/24 9:16
 * @author: wangqing
 */
@FeignClient(value = "basicmodule-service/captcha", fallback = CaptchaServiceFbk.class)
public interface CaptchaService {

    /**
     * 根据类型获取验证码
     * @param imageVerificationDto  图片类型dto
     * @return  图片验证码
     * @throws MyException 获取图片验证码异常
     */
    @PostMapping("/image")
    Result selectImageVerificationCode(@RequestBody ImageVerificationDto imageVerificationDto) throws MyException;

    /**
     * 校验图片验证码
     * @param x x轴坐标
     * @param y y轴坐标
     * @return 校验结果
     * @throws MyException 校验图片验证码异常
     */
    @GetMapping("/check/verification/result")
    Result checkVerificationResult(
            @RequestParam(value = "redisKey", required = true) String redisKey,
            @RequestParam(value = "x",required = true) String x,
            @RequestParam(value = "y",required = true) String y) throws MyException;
}
