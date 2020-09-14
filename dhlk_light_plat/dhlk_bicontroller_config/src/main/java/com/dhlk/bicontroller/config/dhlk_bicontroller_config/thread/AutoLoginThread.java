package com.dhlk.bicontroller.config.dhlk_bicontroller_config.thread;


import com.dhlk.bicontroller.config.dhlk_bicontroller_config.service.ProxyService;
import com.dhlk.entity.basicmodule.BiProxyConfig;

public class AutoLoginThread extends Thread{

    ProxyService proxyService;

    public AutoLoginThread(ProxyService proxyService){
        this.proxyService = proxyService;

    }

    @Override
    public void run() {
        super.run();
        while (true){
            try {

                BiProxyConfig proxyInfo = proxyService.getProxyInfo();
                if (proxyInfo==null || proxyInfo.getCode()!=20000){
                    System.out.println("重新登录");
                    proxyService.login();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000*10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
