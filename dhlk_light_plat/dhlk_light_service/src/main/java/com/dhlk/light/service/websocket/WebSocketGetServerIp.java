package com.dhlk.light.service.websocket;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class WebSocketGetServerIp {
    private static LoadBalancerClient loadBalancerClient = SpringContextHolder.getBean(LoadBalancerClient.class);
    private static Environment env = SpringContextHolder.getBean(Environment.class);

    public static String getServiceInstanceUrl() {
        String serverName = env.getProperty("spring.application.name");
        ServiceInstance serviceInstance = loadBalancerClient.choose(serverName);
        if (serviceInstance != null) {
            String url = getLocalIP() + ":" + serviceInstance.getPort();
            return url;
        }

        return null;

    }

    public static String getLocalIP() {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        byte[] ipAddr = addr.getAddress();
        String ipAddrStr = "";
        for (int i = 0; i < ipAddr.length; i++) {
            if (i > 0) {
                ipAddrStr += ".";
            }
            ipAddrStr += ipAddr[i] & 0xFF;
        }
        return ipAddrStr;
    }
}
