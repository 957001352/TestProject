package com.dhlk.utils;


import com.dhlk.enums.SystemEnums;
import com.dhlk.exceptions.MyException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 版本        修改时间        作者      修改内容
 * V1.0        ------        jpdong     原始版本
 * 文件说明: 各种加密方法，其中包括md5
 **/
public class DES {
    private static byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};
    public static String strkey = "%%d";

    /**
     * 数据加密
     *
     * @param encryptString
     * @return string
     * @throws Exception
     */
    public static String encryptDES(String encryptString) throws MyException {
        if (CheckUtils.isNull(encryptString)) {
            return "";
        }
        try {
            //iv  初始化向量参数，AES 为16bytes. DES 为8bytes. // 初始化向量
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            //两个参数，第一个为私钥字节数组， 第二个为加密方式 DES
            SecretKeySpec key = new SecretKeySpec(getkeys().getBytes(), "DES");
            // 实例化加密类，参数为加密方式，要写全
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
            return BASE64.encode(encryptedData);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MyException(SystemEnums.DESENCRYPT_ERR);
        }
    }



    /**
     * 数据解密
     *
     * @param decryptString
     * @return string
     * @throws Exception
     */
    public static String decryptDES(String decryptString) throws MyException {
        if (CheckUtils.isNull(decryptString)) {
            return "";
        }
        try {
            byte[] byteMi = new BASE64().decode(decryptString);
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec(getkeys().getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte decryptedData[] = cipher.doFinal(byteMi);
            return new String(decryptedData);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MyException(SystemEnums.DESENCRYPT_ERR);
        }
    }

    public static String getkeys() {
        return BASE64.STRKEY2;
    }

    // MD5加密
    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static String strkey3 = "*(&";

    public static String toHexString(byte[] b) { // String to byte
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    public static String md5(String s) {
        if (CheckUtils.isNull(s)) {
            return "";
        }
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();
            return toHexString(messageDigest).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 密码MD5 调用此方法
     *
     * @param s
     * @return
     */
    public static String md5Pwd(String s) {
        if (CheckUtils.isNull(s)) {
            return "";
        }
        return md5("dhlk" + s);
    }
}


