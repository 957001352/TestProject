package com.dhlk.domain;

import lombok.Data;

/**
 * 内存信息实体类
 */
@Data
public class MemoryInfo {
    private String memTotal; // 内存总量
    private String memUsed; // 当前内存使用量
    private String memFree; // 当前内存剩余量
    private String memExchangeAreaTotal; // 交换区总量
    private String memExchangeAreaUsage; // 当前交换区使用量
    private String memExchangeAreaFree; //当前交换区剩余量

    public MemoryInfo(String memTotal, String memUsed, String memFree, String memExchangeAreaTotal, String memExchangeAreaUsage, String memExchangeAreaFree) {
        this.memTotal = memTotal;
        this.memUsed = memUsed;
        this.memFree = memFree;
        this.memExchangeAreaTotal = memExchangeAreaTotal;
        this.memExchangeAreaUsage = memExchangeAreaUsage;
        this.memExchangeAreaFree = memExchangeAreaFree;
    }


    public MemoryInfo() {
    }

}
