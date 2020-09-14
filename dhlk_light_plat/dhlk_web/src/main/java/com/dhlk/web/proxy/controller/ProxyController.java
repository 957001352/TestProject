package com.dhlk.web.proxy.controller;


import com.dhlk.domain.Result;
import com.dhlk.web.proxy.service.ProxyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ztang
 * @date 2020/7/10
 * <p>
 * bi一体机代理信息
 */

@RestController
@RequestMapping(value = "/proxy")
@Api(description = "bi一体机代理管理", value = "ProxyController")
public class ProxyController {

    @Autowired
    private ProxyService proxyService;

    /**
     * 新增代理设备
     *
     * @return
     */
    @ApiOperation("增加代理设备")
    @GetMapping("/add")
    public Result add(@RequestParam("deviceId") String deviceId) {
        return proxyService.add(deviceId);
    }

    /**
     * 删除代理设备
     *
     * @return
     */
    @ApiOperation("删除代理设备")
    @GetMapping("/del")
    public Result del(@RequestParam("deviceId")String deviceId) {
        return proxyService.del(deviceId);
    }

    /**
     * 查询所有代理设备
     *
     * @return
     */
    @ApiOperation("查询所有代理设备")
    @GetMapping("/findList")
    public Result findList() {
        return proxyService.findList();
    }
    /**
     * 获取代理信息
     *
     * @return
     */
    @ApiOperation("获取代理信息")
    @GetMapping("/findProxyInfo")
    public Result findProxyInfo(@RequestParam("deviceId")String deviceId) {
        return proxyService.findProxyInfo(deviceId);
    }

    /**
     * 获取本地服务连接信息
     *
     * @return
     */
    @ApiOperation("获取本地服务连接信息")
    @GetMapping("/requestTempPort")
    public Result requestTempPort(@RequestParam(value = "deviceId", required = false) String deviceId,
                                  @RequestParam(value = "localUrl", required = false) String localUrl) {
        return proxyService.requestTempPort(deviceId,localUrl);
    }

    /**
     * 获取本地服务连接信息
     *
     * @return
     */
    @ApiOperation("获取本地服务管理页面")
    @GetMapping("/requestIndexUrl")
    public Result requestIndexUrl(@RequestParam("deviceId")String deviceId) {
        return proxyService.requestIndexUrl(deviceId);
    }

}
