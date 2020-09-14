package com.dhlk.bicontroller.config.dhlk_bicontroller_config.controller;



import com.dhlk.bicontroller.config.dhlk_bicontroller_config.service.ProxyService;
import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dhlk.utils.ResultUtils;

@RestController
@RequestMapping("/bi")
public class BiConfigController {

    @Autowired
    ProxyService proxyService;

    @RequestMapping("applyConfigUrl")
    public Result applyConfigUrl(String token){

        String s = proxyService.getUrlInfo(token);
        if (s == null) {
            return ResultUtils.error(ResultEnum.SYSTEM_ERR,"系统异常");
        }else {
            return ResultUtils.success(s);
        }
    }
}
