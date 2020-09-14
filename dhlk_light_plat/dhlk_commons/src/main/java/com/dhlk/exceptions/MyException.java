package com.dhlk.exceptions;

import com.dhlk.enums.SystemEnums;

/**
 * Content:自定义异常类
 * Author:jpdong
 * Date:2020/1/8
 */
public class MyException extends RuntimeException {
    private String code; //异常代码
    private String message; //异常详细描述

    /**
     * @param code    异常代码
     * @param message 异常详细描述
     */
    public MyException(String code, String message) {
        super("Error Code:[" + code + "],Error Message:" + message);
        this.code = code;
        this.message = message;
    }

    /**
     * @param enums 枚举
     */
    public MyException(SystemEnums enums) {
        this.code = enums.getState().toString();
        this.message = enums.getStateInfo();
    }


    /**
     * 异常打印
     */
    @Override
    public void printStackTrace() {
        System.out.println("异常代码：" + code + ", 异常信息：" + message);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
