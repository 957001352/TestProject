package com.dhlk.web.proxy.service;

import com.dhlk.domain.Result;
import com.dhlk.web.proxy.service.fbk.ProxyServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ztang
 * @date 2020/7/10
 * <p>
 * 代理信息service
 */
@FeignClient(value = "proxy-service/proxy", fallback = ProxyServiceFbk.class)
public interface ProxyService {

    /**
     * 添加代理设备
     *
     * @param
     * @return
     */
    @GetMapping(value = "/add")
    Result add(@RequestParam(value = "deviceId", required = false) String deviceId);

    /**
     * 删除代理设备
     *
     * @param
     * @return
     */
    @GetMapping(value = "/del")
    Result del(@RequestParam(value = "deviceId", required = false) String deviceId);

    /**
     * 查询所有代理设备
     *
     * @param
     * @return
     */
    @GetMapping(value = "/findList")
    Result findList();

    /**
     * 获取代理信息
     *
     * @param
     * @return
     */
    @GetMapping(value = "/findProxyInfo")
    Result findProxyInfo(@RequestParam(value = "deviceId", required = false) String deviceId);

    /**
     * 获取服务连接信息
     * 即 获取临时端口
     *
     * @param
     * @return
     */
    @GetMapping(value = "/requestTempPort")
    Result requestTempPort(@RequestParam(value = "deviceId", required = false) String deviceId,
                           @RequestParam(value = "localUrl", required = false) String localUrl);

    /**
     * 获取bi管理页面地址
     *
     * @param
     * @return
     */
    @GetMapping(value = "/requestIndexUrl")
    Result requestIndexUrl(@RequestParam(value = "deviceId", required = false) String deviceId);



}
