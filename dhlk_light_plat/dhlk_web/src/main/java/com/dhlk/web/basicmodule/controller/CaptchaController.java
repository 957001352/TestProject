package com.dhlk.web.basicmodule.controller;



import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.ImageVerificationDto;
import com.dhlk.web.basicmodule.service.CaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(description = "滑动拼图验证")
public class CaptchaController {


    /**
     * 运算码业务处理对象
     */
    @Autowired
    private CaptchaService captchaService;

    /**
     * 获取验证码
     * @param  imageVerificationDto 验证码参数
     * @return 根据类型参数返回验证码
     */
    @PostMapping("/image")
    @ApiOperation("获取滑动图片源")
    public Result getVerificationImage(@RequestBody ImageVerificationDto imageVerificationDto) {
      return captchaService.selectImageVerificationCode(imageVerificationDto);
    }

    /**
     * 校验验证码
     * @param x x轴坐标
     * @param y y轴坐标
     * @return 验证结果
     */
    @GetMapping("/check/verification/result")
    @ApiOperation("校验验证码")
    public Result checkVerificationResult(
            @RequestParam(value = "redisKey", required = true) String redisKey,
            @RequestParam(value = "x",required = true) String x,
            @RequestParam(value = "y",required = true) String y) {
        return captchaService.checkVerificationResult(redisKey,x, y);
    }
}