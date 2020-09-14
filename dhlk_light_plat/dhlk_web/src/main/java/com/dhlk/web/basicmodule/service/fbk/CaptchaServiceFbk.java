package com.dhlk.web.basicmodule.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.ImageVerificationDto;
import com.dhlk.entity.basicmodule.ImageVerificationVo;
import com.dhlk.enums.ResultEnum;
import com.dhlk.exceptions.MyException;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.basicmodule.service.CaptchaService;
import org.springframework.stereotype.Service;

/**
 * @program: dhlk.light.plat
 * @description: 验证码验证
 * @author: wqiang
 * @create: 2020-08-24 09:33
 **/
@Service
public class CaptchaServiceFbk implements CaptchaService {
    @Override
    public Result selectImageVerificationCode(ImageVerificationDto imageVerificationDto) throws MyException {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result checkVerificationResult(String redisKey,String x, String y) throws MyException {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
