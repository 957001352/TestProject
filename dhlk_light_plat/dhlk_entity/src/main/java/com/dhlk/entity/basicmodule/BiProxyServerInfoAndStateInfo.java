package com.dhlk.entity.basicmodule;

import lombok.Data;

@Data
public class BiProxyServerInfoAndStateInfo{

    int state;
    String msg;

    BiProxyServerInfo biProxyServerInfo;
}
