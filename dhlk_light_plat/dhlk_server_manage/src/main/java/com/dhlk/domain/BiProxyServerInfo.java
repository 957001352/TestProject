package com.dhlk.domain;

import lombok.Data;

@Data
public class BiProxyServerInfo {

    String name;
    String host;
    String port;
    int proxyPort;
    String username;
    String password;
    int minProxyPort;
    int maxProxyPort;
    String token;


}
