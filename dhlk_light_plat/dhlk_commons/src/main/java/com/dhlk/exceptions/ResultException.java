package com.dhlk.exceptions;

import com.dhlk.enums.SystemEnums;

/**
 * Content:自定义枚举异常处理类,用于统一返回
 * Author:jpdong
 * Date:2020/1/8
 */
public class ResultException extends RuntimeException {
    private String code; //异常代码
    private String exceptionDescription; //异常描述

    public ResultException(SystemEnums systemEnums) {
        super(systemEnums.getStateInfo());
        this.exceptionDescription = systemEnums.getStateInfo();
        this.code = systemEnums.getState().toString();
    }

    /**
     * 异常代码
     *
     * @return
     */
    public String getCode() {
        return this.code.toString();
    }

    /**
     * 异常描述
     *
     * @return
     */
    public String getExceptionDescription() {
        return this.exceptionDescription;
    }
}
