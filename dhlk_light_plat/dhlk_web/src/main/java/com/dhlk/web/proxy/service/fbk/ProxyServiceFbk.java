package com.dhlk.web.proxy.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.proxy.service.ProxyService;
import org.springframework.stereotype.Service;

/**
 * @author ztang
 * @date 2020/7/10
 * <p>
 * 代理服务调用失败时的实现类
 */

@Service
public class ProxyServiceFbk implements ProxyService {
    @Override
    public Result add(String deviceId) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result del(String deviceId) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findList() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findProxyInfo(String deviceId) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result requestTempPort(String deviceId,String localUrl) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result requestIndexUrl(String deviceId) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
