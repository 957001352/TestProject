package dhlk.proxy.dhlk_proxy.config;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import	java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.dhlk.entity.basicmodule.BiProxyClientInfo;
import dhlk.proxy.dhlk_proxy.thread.LoginThread;
import dhlk.proxy.dhlk_proxy.util.ProxyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;


import javax.annotation.PostConstruct;

@Configuration
public class ThreadConfig {

    @Autowired
    @Qualifier("proxyData")  //  指定注入bean的名称，提高注入准确性
    private ProxyConfig proxyConfig;

    public static volatile ThreadPoolExecutor executor;

    @PostConstruct
    public void init() {
        ProxyUtil.PROXY_CONFIG = proxyConfig;

        new Thread(()->{
            Map <String, Map<String, BiProxyClientInfo>> map = ProxyUtil.PROXY_MAP;
            proxyConfig.getServer().stream().forEach(info ->{


                map.put(info.getName(),new HashMap<>());
                ProxyUtil.SERVER_STATE_MAP.put(info.getName(),false);
            });

            executor = new ThreadPoolExecutor(5,10,60, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(10),new MyThreadFactory("check login"));
            while (true){
                proxyConfig.getServer().stream().forEach(info ->{
                    LoginThread fds = new LoginThread(info);
                    executor.submit(fds);
                });
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }
}
