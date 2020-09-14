package com.dhlk.web.basicmodule.controller;

import com.dhlk.web.basicmodule.service.SysLogService;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dhlk.utils.FileUpDownUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: jzhao
 * @Date: 2020/4/13 16:41
 * @Description:
 */
@RestController
@RequestMapping(value = "/logFile")
@Api(description = "日志文件")
public class SysLogController {

    @Value("${log.path}")
    private String logPath;

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
    public Result searchLogFile() {
        return sysLogService.searchLogFile();
    }


    /**
     * @param response
     * @return domain.Result
     * @date 2020/4/13 11:32
     * @author jzhao
     * @description 日志文件下载(多个下载)
     */
    @GetMapping(value = "/downZipFile")
    @ApiOperation("日志文件下载")
    public Result downLog(@RequestParam(value = "fileName", required = true) String fileName, HttpServletResponse response) {
        return FileUpDownUtils.downZipFile(logPath, fileName, "日志文件", response);
    }
}
