package com.dhlk.entity.basicmodule;

import lombok.Data;

import java.util.List;

@Data
public class BiProxyClientInfo {

    String name;
    String clientKey;
    int status;
    List<BiProxyPortInfo> proxyMappings;
}
