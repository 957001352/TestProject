package com.dhlk.basicmodule.service.service;

import com.dhlk.domain.Result;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: jzhao
 * @Date: 2020/4/13 11:33
 * @Description:
 */
public interface SysLogService {

    Result findLogFile();

    Result downZipFile(String fileName, HttpServletResponse response);
}
