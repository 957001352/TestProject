package dhlk.proxy.dhlk_proxy.controller;


import com.dhlk.domain.JsonResult;
import com.dhlk.domain.Result;
import com.dhlk.enums.SystemEnums;
import com.dhlk.utils.CheckUtils;
import dhlk.proxy.dhlk_proxy.config.ProxyConfig;
import dhlk.proxy.dhlk_proxy.service.ProxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/proxy")
public class ProxyController {

    @Autowired
    ProxyService proxyService;

    @Autowired
    @Qualifier("proxyData")  //  指定注入bean的名称，提高注入准确性
    private ProxyConfig proxyConfig;


    //增加代理设备
    @RequestMapping(value = "/add")
    public Result addProxyDevice(String deviceId) {
        if(CheckUtils.isNull(deviceId)){
            return JsonResult.toResult(SystemEnums.PLAT_ERROR,"设备id不存在");
        }
        return proxyService.add(deviceId);
    }

    //删除代理设备
    @RequestMapping(value = "/del")
    public Result delProxyDevice( String deviceId){
        if(CheckUtils.isNull(deviceId)){
            return JsonResult.toResult(SystemEnums.PLAT_ERROR,"设备id不存在");
        }
        return proxyService.del(deviceId);
    }

    //查询所有代理设备
    @RequestMapping(value = "/findList")
    public Result findList(){
        return proxyService.findList();
    }

    //获取代理信息
    @RequestMapping(value = "/findProxyInfo")
    public Result findProxyInfo(String deviceId) {
        if(CheckUtils.isNull(deviceId)){
            return JsonResult.toResult(SystemEnums.PLAT_ERROR,"设备id不存在");
        }
        return proxyService.findProxyInfo(deviceId);
    }

    //获取临时端口
    @RequestMapping(value = "/requestTempPort")
    public Result requestTempPort(String deviceId,String localUrl){
        if(CheckUtils.isNull(deviceId)){
            return JsonResult.toResult(SystemEnums.PLAT_ERROR,"设备id不存在");
        }
        return proxyService.requestTempPort(deviceId,localUrl,0);
    }

    //获取本地页面
    @RequestMapping(value = "/requestIndexUrl")
    public Result requestIndexUrl(String deviceId){
        if(CheckUtils.isNull(deviceId)){
            return JsonResult.toResult(SystemEnums.PLAT_ERROR,"设备id不存在");
        }
        return proxyService.requestTempPort(deviceId,proxyConfig.getLocalUrl(),0);
    }
}
