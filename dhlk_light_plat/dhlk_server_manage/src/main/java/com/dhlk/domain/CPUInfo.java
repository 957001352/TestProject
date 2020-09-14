package com.dhlk.domain;

import lombok.Data;

/**
 * 系统CPU信息对象
 */

@Data
public class CPUInfo {
    private Integer cpuNum; // 第几块cpu
    private long cpuTotal; // cpu总量
    private String cpuBrand; //cpu品牌
    private String cpuType; // cpu型号
    private String cpuUserUsageRate; //cpu用户使用率
    private String cpuSysUsageRate; //cpu系统使用率
    private String cpuWaitRate; //cpu当前等待率
    private String cpuErrRate; //cpu当前错误率
    private String cpuFreeRate; //cpu当前空闲率
    private String cpuUsageRate; // cpu总的使用率

    public CPUInfo() {
    }

    public CPUInfo(Integer cpuNum, long cpuTotal, String cpuBrand, String cpuType, String cpuUserUsageRate, String cpuSysUsageRate, String cpuWaitRate, String cpuErrRate, String cpuFreeRate, String cpuUsageRate) {
        this.cpuNum = cpuNum;
        this.cpuTotal = cpuTotal;
        this.cpuBrand = cpuBrand;
        this.cpuType = cpuType;
        this.cpuUserUsageRate = cpuUserUsageRate;
        this.cpuSysUsageRate = cpuSysUsageRate;
        this.cpuWaitRate = cpuWaitRate;
        this.cpuErrRate = cpuErrRate;
        this.cpuFreeRate = cpuFreeRate;
        this.cpuUsageRate = cpuUsageRate;
    }



}
