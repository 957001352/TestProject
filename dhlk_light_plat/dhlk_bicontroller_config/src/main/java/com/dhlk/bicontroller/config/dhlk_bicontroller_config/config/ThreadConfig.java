package com.dhlk.bicontroller.config.dhlk_bicontroller_config.config;


import com.dhlk.bicontroller.config.dhlk_bicontroller_config.service.ProxyService;
import com.dhlk.bicontroller.config.dhlk_bicontroller_config.thread.AutoLoginThread;
import com.dhlk.bicontroller.config.dhlk_bicontroller_config.thread.AutoRemoveThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ThreadConfig {

    @Autowired
    ProxyService proxyService;


    @PostConstruct
    public void init() {
        new AutoRemoveThread(proxyService).start();
        new AutoLoginThread(proxyService).start();
    }
}
