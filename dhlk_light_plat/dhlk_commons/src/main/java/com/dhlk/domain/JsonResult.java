package com.dhlk.domain;

import com.dhlk.enums.SystemEnums;

/**
 * Content:统一返回处理
 * 使用方法：JsonResult.toResult(SystemEnums enum, Object obj)
 * JsonResult.toResult(Object obj)
 * Author:jpdong
 * Date:2020/1/20
 */
public class JsonResult {
    /**
     * 带枚举的统一返回的方法
     *
     * @param resultEnum
     * @param re
     * @return
     */
    public static Result toResult(SystemEnums resultEnum, Object re) {
        if (resultEnum == null) {
            resultEnum = SystemEnums.SUCESS;
        }
        return new Result<Object>(resultEnum, re);
    }

    /**
     * 不带枚举的返回
     *
     * @param re
     * @return
     */
    public static Result toResult(Object re) {
        return toResult(null, re);
    }
}

