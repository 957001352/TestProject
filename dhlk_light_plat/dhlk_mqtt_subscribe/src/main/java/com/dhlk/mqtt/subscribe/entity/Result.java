package com.dhlk.mqtt.subscribe.entity;


public class Result<T> {
    private Integer code; //code
    private String msg; //对应code的提示信息
    private T Data; //Data
    private String topic;
    public Result() {}


    public Result(String topic,Integer code, String msg, T data) {
        this.topic=topic;
        this.code = code;
        this.msg = msg;
        Data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
