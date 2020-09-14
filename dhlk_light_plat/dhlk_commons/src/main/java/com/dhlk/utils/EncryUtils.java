package com.dhlk.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Content: 加解密操作类
 * Author:jpdong
 * Date:2020/3/3
 */
public class EncryUtils {
    //region MD5加密

    /**
     * MD5加密
     *
     * @param str  待加密字符
     * @param prex 内置附加字符
     * @return String
     */
    public static String md5(String str, String prex) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    str.getBytes());
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    /**
     * MD5加密
     *
     * @param str 待加密字符
     * @return String
     */
    public static String md5(String str) {
        return md5(str, "");
    }
    //endregion

}
