package com.dhlk.basicmodule.service.service.impl;

import com.dhlk.basicmodule.service.service.SysLogService;
import com.dhlk.domain.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.dhlk.utils.FileUpDownUtils;
import com.dhlk.utils.FileUtils;
import com.dhlk.utils.ResultUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author: jzhao
 * @Date: 2020/4/13 11:35
 * @Description:
 */
@Service
public class SysLogServiceImpl implements SysLogService {
    @Value("${log.path}")
    private String logPath;

    /**
     * @date 2020/4/20 12:06
     * @author jzhao
     * @param fileName
     * @param response
     * @return domain.Result
     * @description 日志文件下载
     */
    @Override
    public Result downZipFile(String fileName, HttpServletResponse response) {
        return FileUpDownUtils.downZipFile(logPath, fileName, "日志文件", response);
    }

    /**
     * @param
     * @return domain.Result
     * @date 2020/4/20 9:04
     * @author jzhao
     * @description 查询所有的日志文件
     */
    @Override
    public Result findLogFile() {
        //根据地址获取所有的日志文件
        List<Map<String, String>> fileList = FileUtils.getInstance().findFileByPath(logPath, true);
        return fileList.size() >= 0 ? ResultUtils.success(fileList) : ResultUtils.error(-1, "文件不存在!");
    }
}
