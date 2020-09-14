package com.dhlk.domain;

import com.dhlk.enums.SystemEnums;

/**
 * Content:统一结果返回
 * Example:
 * Person p=new Person("Tom","M");
 * utils.Result<T> r=new utils.Result<>(utils.ResultEnum.SUCESS,p)
 * Author:jpdong
 * Date:2020/1/8
 */
public class Result<T> {
    private Integer code; //code
    private String msg; //对应code的提示信息
    private T Data; //Data

    public Result() {}
    public Result(SystemEnums resultEnum, T data) {
        this.code = resultEnum.getState();
        this.msg = resultEnum.getStateInfo();
        Data = data;
    }

    public Result(Integer code, String msg, T data) {
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
}
