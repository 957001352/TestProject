package com.dhlk.entity.basicmodule;

import lombok.Data;

@Data
public class BiProxyServerInfo{

    String name;
    String publicHost;
    String host;
    String port;
    int proxyPort;
    String username;
    String password;
    int minProxyPort;
    int maxProxyPort;
    String token;

    //区分一体机的code
    String code;

}
