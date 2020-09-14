package dhlk.proxy.dhlk_proxy.util;
import	java.util.ArrayList;

import com.dhlk.domain.JsonResult;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.*;
import com.dhlk.enums.SystemEnums;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static dhlk.proxy.dhlk_proxy.util.ProxyUtil.SERVER_MAP;

@Slf4j
public class ClientUtil {

    public static boolean login(BiProxyServerInfo biProxyServerInfo){
        try {
            log.error("开始登陆》》》》》》》》》》》》》》》》》》》》》》》》》");
            String url = "http://"+ biProxyServerInfo.getHost()+":"+biProxyServerInfo.getPort()+"/login";
            BiProxyUser user = new BiProxyUser();
            user.setUsername(biProxyServerInfo.getUsername());
            user.setPassword(biProxyServerInfo.getPassword());
            String body = ProxyUtil.GSON.toJson(user);
            String info = HttpUtil.post(url,null, body);
            BiProxyLoginInfo loginEntity = ProxyUtil.GSON.fromJson(info, BiProxyLoginInfo.class);
            if(loginEntity.getCode()==20000){
                biProxyServerInfo.setToken(loginEntity.getData());
                SERVER_MAP.put(biProxyServerInfo.getName(),biProxyServerInfo);
                return true;
            }
        }catch (NullPointerException e) {
            log.error("返回数据异常");
        }catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }


    public static BiProxyConfig getDetailInfo(BiProxyServerInfo biProxyServerInfo){
        String url = "http://" + biProxyServerInfo.getHost() + ":" + biProxyServerInfo.getPort() + "/config/detail";
        String xx = HttpUtil.post( url, biProxyServerInfo.getToken(),"{}");
        BiProxyConfig biProxyConfig = ProxyUtil.GSON.fromJson(xx,BiProxyConfig.class);
        return biProxyConfig;
    }

    public static BiProxyConfig updateDetailInfo(BiProxyServerInfo biProxyServerInfo, BiProxyConfig biProxyConfig){
        String url = "http://" + biProxyServerInfo.getHost() + ":" + biProxyServerInfo.getPort() + "/config/update";
        String body = ProxyUtil.GSON.toJson(biProxyConfig.getData());
        String xx = HttpUtil.post( url, biProxyServerInfo.getToken(),body);
        biProxyConfig = ProxyUtil.GSON.fromJson(xx,BiProxyConfig.class);
        return biProxyConfig;
    }

    public static Result clearPort(BiProxyServerInfo biProxyServerInfo){
        try {
            BiProxyConfig detailInfo = getDetailInfo(biProxyServerInfo);
            List<BiProxyClientInfo> data = detailInfo.getData();
            List<BiProxyClientInfo> collect = data.stream().map(info -> {
                info.setProxyMappings(new ArrayList<>());
                return info;
            }).collect(Collectors.toList());
            BiProxyConfig biProxyConfig = new BiProxyConfig();
            biProxyConfig.setData(collect);
            updateDetailInfo(biProxyServerInfo,biProxyConfig);
            Result result = putPort(biProxyServerInfo,"",0,false);
            return result;
        }catch (Exception e){
            return JsonResult.toResult(SystemEnums.PLAT_ERROR,"清理端口异常，"+e.getMessage());
        }
    }
    // state 为 true时，判断端口是否添加成功
    public static Result putPort(BiProxyServerInfo info,String localUrl,int finalPort,boolean state){
        try {
            AtomicBoolean isExist = new AtomicBoolean(false);
            List<Integer> list = new ArrayList<> ();
            BiProxyConfig detailInfo = ClientUtil.getDetailInfo(info);
            List<BiProxyClientInfo> data = detailInfo.getData();
            data.stream().forEach(clientInfo ->{
                List<BiProxyPortInfo> proxyMappings = clientInfo.getProxyMappings();
                proxyMappings.stream().forEach(portInfo ->{
                    list.add(portInfo.getInetPort());
                    if (state){
                        if (portInfo.getInetPort()==finalPort&&portInfo.getLan().equals(localUrl)){
                            isExist.set(true);
                        }
                    }
                });
            });
            Collections.sort(list);
            ProxyUtil.PROXY_PORT_MAP.put(info.getName(),list);
            if (state){
                if (isExist.get()){
                    return JsonResult.toResult(SystemEnums.SUCESS,"");
                }else {
                    return JsonResult.toResult(SystemEnums.PLAT_ERROR,"配置端口失败");
                }
            }else {
                return JsonResult.toResult(SystemEnums.SUCESS,"");
            }


        }catch (Exception e){
            return JsonResult.toResult(SystemEnums.PLAT_ERROR,"获取端口出现异常，"+e.getMessage());
        }
    }

}
