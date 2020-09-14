package com.dhlk.bicontroller.config.dhlk_bicontroller_config.thread;


import com.dhlk.bicontroller.config.dhlk_bicontroller_config.service.ProxyService;
import com.dhlk.entity.basicmodule.BiProxyClientInfo;
import com.dhlk.entity.basicmodule.BiProxyConfig;

import java.util.ArrayList;
import java.util.List;

public class AutoRemoveThread extends Thread{

    private ProxyService proxyService;

    public AutoRemoveThread(ProxyService proxyService){
        this.proxyService = proxyService;
    }

    @Override
    public void run() {
        while (true){
            try {
                List<BiProxyClientInfo> list = new ArrayList<>();

                BiProxyConfig biConfigEntity = proxyService.getProxyInfo();
                for (BiProxyClientInfo clientInfo:biConfigEntity.getData()){
                    if (clientInfo.getStatus()==0){
                        list.add(clientInfo);
                    }
                }

                for (BiProxyClientInfo clientInfo : list){
                    biConfigEntity.getData().remove(clientInfo);
                }
                proxyService.updateProxyInfo(biConfigEntity);

            }catch (Exception e){
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000*60*60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
