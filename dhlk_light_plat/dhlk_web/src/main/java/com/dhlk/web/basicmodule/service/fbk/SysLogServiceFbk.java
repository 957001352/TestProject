package com.dhlk.web.basicmodule.service.fbk;

import com.dhlk.web.basicmodule.service.SysLogService;
import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import org.springframework.stereotype.Service;
import com.dhlk.utils.ResultUtils;

/**
 * @Author: jzhao
 * @Date: 2020/4/13 16:48
 * @Description: 日志文件
 */
@Service
public class SysLogServiceFbk implements SysLogService {

    @Override
    public Result searchLogFile() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

//    @Override
//    public Result download(String filePath, HttpServletResponse response) {
//        return ResultUtils.error(ResultEnum.NETWORK_ERR);
//    }
}
