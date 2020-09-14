package com.dhlk.entity.basicmodule;

import lombok.Data;

import java.util.List;

@Data
public class BiProxyConfig {

    private int code;
    private String message;
    private List<BiProxyClientInfo> data;
}
