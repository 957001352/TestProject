package com.dhlk.energy.subscribe.entity;


public class Result {
    private String deviceCode; //对应code的提示信息
    private Object data; //Data
    private Long createTime;
    public Result() {}


    public Result(String deviceCode, Long createTime, Object data) {
        this.deviceCode=deviceCode;
        this.createTime=createTime;
        this.data = data;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
