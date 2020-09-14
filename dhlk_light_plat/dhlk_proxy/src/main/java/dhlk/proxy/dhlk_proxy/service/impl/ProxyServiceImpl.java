package dhlk.proxy.dhlk_proxy.service.impl;
import	java.util.List;

import com.dhlk.domain.JsonResult;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.*;
import com.dhlk.enums.SystemEnums;
import dhlk.proxy.dhlk_proxy.config.ThreadConfig;
import dhlk.proxy.dhlk_proxy.service.ProxyService;
import dhlk.proxy.dhlk_proxy.thread.LoginThread;
import dhlk.proxy.dhlk_proxy.util.ClientUtil;
import dhlk.proxy.dhlk_proxy.util.ProxyUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
public class ProxyServiceImpl implements ProxyService {

    private static final int NOT_FOUND_DEVICE = 0;
    private static final int NOT_FOUND_PROXY_SERVER = 1;
    private static final int DEVICE_IS_EXIST = 2;


    /**
     *
     *    需要查找到 最小数量，数量一致找第一
     * 	    如何查找：代理设备存于map中，判断key个数。
     *    需要判断该设备id是否已经存在于某个服务器上。
     * 	    如何判断： 遍历
     *    需要记录
     * 	    更新map。
     *
     * @param deviceId
     * @return
     */
    @Override
    public Result add(String deviceId) {
        try {
            long l = ProxyUtil.PROXY_LOCK.tryOptimisticRead();
            Map<String, Map<String, BiProxyClientInfo>> proxyMap = ProxyUtil.PROXY_MAP;
            BiProxyServerInfoAndStateInfo biProxyServerInfo = selectDevice(proxyMap, deviceId);
            if (!ProxyUtil.PROXY_LOCK.validate(l)){
                l = ProxyUtil.PROXY_LOCK.readLock();
                biProxyServerInfo = selectDevice(proxyMap, deviceId);
                ProxyUtil.PROXY_LOCK.unlockRead(l);
            }
            if (biProxyServerInfo!=null){
                if (biProxyServerInfo.getState()>NOT_FOUND_DEVICE){

                    BiProxyServerInfo biProxyServerInfo1 = new BiProxyServerInfo();
                    biProxyServerInfo1.setHost(biProxyServerInfo.getBiProxyServerInfo().getPublicHost());
//                    biProxyServerInfo1.setPublicHost(biProxyServerInfo.getBiProxyServerInfo().getPublicHost());
                    biProxyServerInfo1.setProxyPort(biProxyServerInfo.getBiProxyServerInfo().getProxyPort());
                    biProxyServerInfo1.setToken(deviceId);


                    return JsonResult.toResult(SystemEnums.SUCESS,biProxyServerInfo1);
                }
                //增加代理设备
                BiProxyServerInfo biProxyServerInfo2 = biProxyServerInfo.getBiProxyServerInfo();
                BiProxyConfig detailInfo = ClientUtil.getDetailInfo(biProxyServerInfo2);
                List<BiProxyClientInfo> data = detailInfo.getData();

                BiProxyClientInfo biProxyClientInfo = new BiProxyClientInfo();
                biProxyClientInfo.setClientKey(deviceId);
                biProxyClientInfo.setName(deviceId);
                biProxyClientInfo.setProxyMappings(new ArrayList<>());
                data.add(biProxyClientInfo);
                ClientUtil.updateDetailInfo(biProxyServerInfo2,detailInfo);
                ThreadConfig.executor.submit(new LoginThread(biProxyServerInfo2));


                BiProxyServerInfo biProxyServerInfo1 = new BiProxyServerInfo();
                biProxyServerInfo1.setHost(biProxyServerInfo2.getPublicHost());
                biProxyServerInfo1.setProxyPort(biProxyServerInfo2.getProxyPort());
                biProxyServerInfo1.setToken(deviceId);


                return JsonResult.toResult(SystemEnums.SUCESS,biProxyServerInfo1);
            }
            return JsonResult.toResult(SystemEnums.PLAT_ERROR,"未发现可用的代理服务器");
        }catch (Exception e){
            return JsonResult.toResult(SystemEnums.PLAT_ERROR,e.getMessage());
        }
    }

    private BiProxyServerInfoAndStateInfo selectDevice(Map<String, Map<String, BiProxyClientInfo>> proxyMap, String deviceId){
        int minCnt=0;
        BiProxyServerInfoAndStateInfo minServer=new BiProxyServerInfoAndStateInfo();
        minServer.setState(NOT_FOUND_PROXY_SERVER);
        minServer.setMsg("未发现可用的代理服务器");
        boolean isExist=false;
        for (Map.Entry<String, Map<String, BiProxyClientInfo>> entry : proxyMap.entrySet()) {

            if (!ProxyUtil.SERVER_STATE_MAP.get(entry.getKey())){
                continue;
            }

            if (entry.getValue().containsKey(deviceId)){
                isExist = true;
                minServer.setBiProxyServerInfo(ProxyUtil.SERVER_MAP.get(entry.getKey()));
                break;
            }
            //判断最小数量
            if (minCnt==0){
                minCnt = entry.getValue().size();
                minServer.setState(NOT_FOUND_DEVICE);
                minServer.setBiProxyServerInfo(ProxyUtil.SERVER_MAP.get(entry.getKey()));
            }else {
                if (entry.getValue().size()<minCnt){
                    minCnt = entry.getValue().size();
                    minServer.setState(NOT_FOUND_DEVICE);
                    minServer.setBiProxyServerInfo(ProxyUtil.SERVER_MAP.get(entry.getKey()));
                }
            }
        }
        if (isExist){
            minServer.setState(DEVICE_IS_EXIST);
            minServer.setMsg("该设备已被记录");
        }
        return minServer;
    }


    /**
     *   需要判断该设备id存在于哪个服务器：
     * 	    如何判断：遍历
     * @param deviceId
     * @return
     */
    @Override
    public Result del(String deviceId) {
        try {
            long l = ProxyUtil.PROXY_LOCK.tryOptimisticRead();
            Map<String, Map<String, BiProxyClientInfo>> proxyMap = ProxyUtil.PROXY_MAP;
            BiProxyServerInfoAndStateInfo biProxyServerInfo = selectDevice(proxyMap, deviceId);
            if (!ProxyUtil.PROXY_LOCK.validate(l)) {
                l = ProxyUtil.PROXY_LOCK.readLock();
                biProxyServerInfo = selectDevice(proxyMap, deviceId);
                ProxyUtil.PROXY_LOCK.unlockRead(l);
            }
            if (biProxyServerInfo.getState()==DEVICE_IS_EXIST){
                //删除设备
                BiProxyConfig detailInfo = ClientUtil.getDetailInfo(biProxyServerInfo.getBiProxyServerInfo());
                List<BiProxyClientInfo> data = detailInfo.getData();
                List<BiProxyClientInfo> collect = data.stream().filter(t -> !t.getClientKey().equals(deviceId)).collect(Collectors.toList());
                detailInfo.setData(collect);

                ClientUtil.updateDetailInfo(biProxyServerInfo.getBiProxyServerInfo(),detailInfo);
                ThreadConfig.executor.submit(new LoginThread(biProxyServerInfo.getBiProxyServerInfo()));
                return JsonResult.toResult(SystemEnums.SUCESS,"删除成功");
            }
            return JsonResult.toResult(SystemEnums.PLAT_ERROR,"未发现该设备，请稍后再试");


        }catch (Exception e){
            return JsonResult.toResult(SystemEnums.PLAT_ERROR,e.getMessage());
        }
    }

    @Override
    public Result findList() {
        long l = ProxyUtil.PROXY_LOCK.tryOptimisticRead();
        Map<String, Map<String, BiProxyClientInfo>> proxyMap = ProxyUtil.PROXY_MAP;
        if(!ProxyUtil.PROXY_LOCK.validate(l)){
            l = ProxyUtil.PROXY_LOCK.readLock();
            proxyMap = ProxyUtil.PROXY_MAP;
            ProxyUtil.PROXY_LOCK.unlockRead(l);
        }
        return JsonResult.toResult(SystemEnums.SUCESS,proxyMap);
    }

    @Override
    public Result findProxyInfo(String deviceId) {
        try {
            long l = ProxyUtil.PROXY_LOCK.tryOptimisticRead();
            Map<String, Map<String, BiProxyClientInfo>> proxyMap = ProxyUtil.PROXY_MAP;
            BiProxyServerInfoAndStateInfo biProxyServerInfo = selectDevice(proxyMap, deviceId);
            if (!ProxyUtil.PROXY_LOCK.validate(l)) {
                l = ProxyUtil.PROXY_LOCK.readLock();
                biProxyServerInfo = selectDevice(proxyMap, deviceId);
                ProxyUtil.PROXY_LOCK.unlockRead(l);
            }
            if (biProxyServerInfo.getState() == DEVICE_IS_EXIST) {
                BiProxyServerInfo bsi = biProxyServerInfo.getBiProxyServerInfo();
                BiProxyServerInfo biProxyServerInfo1 = new BiProxyServerInfo();
                biProxyServerInfo1.setHost(bsi.getPublicHost());
                biProxyServerInfo1.setProxyPort(bsi.getProxyPort());
                biProxyServerInfo1.setToken(deviceId);
                return JsonResult.toResult(SystemEnums.SUCESS,biProxyServerInfo1);
            }
            return JsonResult.toResult(SystemEnums.PLAT_ERROR,"未发现该设备，请稍后再试");

        }catch (Exception e){
            return JsonResult.toResult(SystemEnums.PLAT_ERROR,e.getMessage());
        }
    }

    /**
     * @param deviceId
     * @param localUrl
     * @return
     */
    @Override
    public Result requestTempPort(String deviceId, String localUrl,int count) {
        //查找未被使用的端口
        try {
            count++;
            //判断 该设备是否在代理服务器上
            long l = ProxyUtil.PROXY_LOCK.tryOptimisticRead();
            Map<String, Map<String, BiProxyClientInfo>> proxyMap = ProxyUtil.PROXY_MAP;
            BiProxyServerInfoAndStateInfo biProxyServerInfo = selectDevice(proxyMap, deviceId);
            if (!ProxyUtil.PROXY_LOCK.validate(l)) {
                l = ProxyUtil.PROXY_LOCK.readLock();
                biProxyServerInfo = selectDevice(proxyMap, deviceId);
                ProxyUtil.PROXY_LOCK.unlockRead(l);
            }
            if (biProxyServerInfo.getState() == DEVICE_IS_EXIST) {
                //在代理服务器上
                BiProxyServerInfo bsi = biProxyServerInfo.getBiProxyServerInfo();

                //寻找该地址是否已被代理
                AtomicBoolean isExist = new AtomicBoolean(false);
                AtomicInteger serverPort = new AtomicInteger();
                BiProxyClientInfo biProxyClientInfo = ProxyUtil.PROXY_MAP.get(bsi.getName()).get(deviceId);
                biProxyClientInfo.getProxyMappings().forEach(info ->{
                    if(info.getLan().equals(localUrl)){
                        isExist.set(true);
                        serverPort.set(info.getInetPort());
                        return;
                    }
                });

                if (isExist.get()){
                    //该代理已存在
                    return JsonResult.toResult(SystemEnums.SUCESS,bsi.getPublicHost()+":"+serverPort.get());
                }


                //查找空闲端口
                List<Integer> list = ProxyUtil.PROXY_PORT_MAP.get(bsi.getName());
                int minPort=bsi.getMinProxyPort();
                int port_tmp = minPort;
                for (int i:list){
                    if (i-port_tmp>1 && port_tmp>=minPort){
                        break;
                    }else {
                        port_tmp = i;
                    }
                }

                if(port_tmp+1>bsi.getMaxProxyPort()){
                    //触发清理端口
                    Result result = ClientUtil.clearPort(bsi);
                    if (count<3){
                        requestTempPort(deviceId, localUrl,count);
                    }else {
                        return result;
                    }

                }

                //增加该代理服务
                BiProxyConfig detailInfo = ClientUtil.getDetailInfo(bsi);
                int finalPort_tmp = port_tmp+1;
                List<BiProxyClientInfo> collect = detailInfo.getData().stream().map(info -> {
                    if (info.getClientKey().equals(deviceId)) {
                        List<BiProxyPortInfo> proxyMappings = info.getProxyMappings();
                        BiProxyPortInfo biProxyPortInfo = new BiProxyPortInfo();
                        biProxyPortInfo.setName(localUrl);
                        biProxyPortInfo.setLan(localUrl);
                        biProxyPortInfo.setInetPort(finalPort_tmp);
                        proxyMappings.add(biProxyPortInfo);
                    }
                    return info;
                }).collect(Collectors.toList());

                BiProxyConfig biProxyConfig = new BiProxyConfig();
                biProxyConfig.setData(collect);
                ClientUtil.updateDetailInfo(bsi,biProxyConfig);
                //更新端口
                Result result = ClientUtil.putPort(bsi, localUrl, finalPort_tmp, true);
                if (result.getCode().equals(SystemEnums.SUCESS.getState())){
                    ThreadConfig.executor.submit(new LoginThread(bsi));
                    return JsonResult.toResult(SystemEnums.SUCESS,bsi.getPublicHost()+":"+finalPort_tmp);
                }else {
                    return result;
                }
            }
            return JsonResult.toResult(SystemEnums.PLAT_ERROR,"未发现该设备，请稍后再试");
        }catch (Exception e){
            return JsonResult.toResult(SystemEnums.PLAT_ERROR,e.getMessage());
        }
    }
}
