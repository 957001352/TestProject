package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.SysLogService;
import com.dhlk.domain.Result;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: jzhao
 * @Date: 2020/4/13 11:02
 * @Description: 日志文件
 */
@RestController
@RequestMapping(value = "/logFile")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    /**
     * @return void
     * @date 2020/4/13 16:06
     * @author jzhao
     * @description
     */
    @GetMapping(value = "/searchLogFile")
    @ApiOperation("查询日志文件")
    @RequiresAuthentication
    public Result searchLogFile() {
        return sysLogService.findLogFile();
    }

    /**
     * @param response
     * @return domain.Result
     * @date 2020/4/13 11:32
     * @author jzhao
     * @description 日志下载
     */
    @GetMapping(value = "/downZipFile")
    @RequiresAuthentication
    public Result downZipFile(@RequestParam(value = "fileName", required = true) String fileName, HttpServletResponse response) {
        return sysLogService.downZipFile(fileName, response);
    }
}
