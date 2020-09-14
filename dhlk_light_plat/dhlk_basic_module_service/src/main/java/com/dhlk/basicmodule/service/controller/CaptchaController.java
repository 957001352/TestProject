package com.dhlk.basicmodule.service.controller;



import com.dhlk.basicmodule.service.service.CaptchaService;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.ImageVerificationDto;
import com.dhlk.entity.basicmodule.ImageVerificationVo;
import com.dhlk.exceptions.MyException;
import com.dhlk.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 滑动验证码
 * -------------------
 * @Author: wangqiang
 * @Date: 2020/08/21 17:16
 */

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(CaptchaController.class);

    /**
     * 运算码业务处理对象
     */
    @Autowired
    private CaptchaService captchaService;

    /**
     * 获取验证码
     * @param imageVerificationDto 验证码参数
     * @return 根据类型参数返回验证码
     */
    @PostMapping("/image")
    public Result getVerificationImage(@RequestBody ImageVerificationDto imageVerificationDto) {
        ImageVerificationVo imageVerificationVo = null;
        try {
            imageVerificationVo = captchaService.selectImageVerificationCode(imageVerificationDto);
        } catch (MyException e) {
            return null;
        }
        return ResultUtils.success(imageVerificationVo);
    }

    /**
     * 校验验证码
     * @param x x轴坐标
     * @param y y轴坐标
     * @return 验证结果
     */
    @RequestMapping("/check/verification/result")
    public Result checkVerificationResult(
            @RequestParam(value = "redisKey", required = true) String redisKey,
            @RequestParam(value = "x", required = true) String x,
            @RequestParam(value = "y", required = true) String y) {
        boolean result = false;
        try {
            result = captchaService.checkVerificationResult(redisKey,x, y);
        } catch (MyException e) {
            return ResultUtils.success(false);
        }
        return ResultUtils.success(result);
    }
}