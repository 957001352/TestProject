package com.dhlk.utils;

import java.util.UUID;

/**
 * 主键生成工具类
 *
 * @author xkliu
 * @date 2020/6/8
 */
public class IDUtils {

    /**
     * UUID生成ID
     *
     * @return
     */
    public static String getId() {
        String id = UUID.randomUUID().toString();
        return id.replaceAll("-", "");
    }
}
