package com.dhlk.utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Content: 转换类操作类
 * Author:jpdong
 * Date:2020/3/3
 */
public class Convert {
    //region 日期，时间戳相关转换方法

    /**
     * 字符转日期(yyyy-MM-dd)
     *
     * @param date 字符
     * @return Date
     */
    public static Date strToDate(String date) {
        return strToDate(date, "yyyy-MM-dd");
    }

    /**
     * 字符转日期
     *
     * @param date      字符
     * @param formatStr 格式,为空则默认yyyy-MM-dd
     * @return Date
     */
    public static Date strToDate(String date, String formatStr) {
        if (CheckUtils.isNull(formatStr)) {
            formatStr = "yyyy-MM-dd";
        }
        if (date == null || !CheckUtils.isDate(date)) {
            return null;
        }
        ParsePosition pos = new ParsePosition(0);
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            Date re = format.parse(date, pos);
            return re;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 格式化日期
     *
     * @param str       待格式化的日期类字符
     * @param formatStr 格式 yyyy-MM-dd, yyyy-MM-dd hh:mm:ss ...
     * @return String
     */
    public static String formatDateTime(String str, String formatStr) {
        if (CheckUtils.isNull(formatStr)) {
            formatStr = "yyyy-MM-dd";
        }
        if (str == null || !CheckUtils.isDate(str)) {
            return "";
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            String re = format.format(str);
            return re;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 格式化日期
     *
     * @param str       待格式化的日期类字符
     * @param formatStr 格式 yyyy-MM-dd, yyyy-MM-dd hh:mm:ss ...
     * @return String
     */
    public static String formatDateTime(Long str, String formatStr) {
        if (CheckUtils.isNull(formatStr)) {
            formatStr = "yyyy-MM-dd";
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            String re = format.format(str);
            return re;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 字符转成时间戳
     *
     * @param str String
     * @return 时间戳
     */
    public static Timestamp stringToTimeStamp(String str) {
        if (str == null || !CheckUtils.isDate(str)) {
            return null;
        }
        Date date = strToDate(str, "yyyy-MM-dd hh:mm:ss");
        Timestamp re = new Timestamp(date.getTime());
        return re;
    }

    /**
     * 时间戳转字符
     *
     * @param ts 待转时间戳
     * @return 字符
     */
    public static String timeStampToString(Timestamp ts) {
        if (ts == null) {
            return "";
        }
        Time time = new Time(ts.getTime());
        return time.toString();
    }

    /**
     * 时间戳转Time
     *
     * @param ts 待转时间戳
     * @return Time
     */
    public static Time timeStampToTime(Timestamp ts) {
        if (ts == null) {
            return null;
        }
        Time time = new Time(ts.getTime());
        return time;
    }
    //endregion

    //region 数字、字符类相关转换方法

    /**
     * Integer to String
     *
     * @param i Integer
     * @return String
     */
    public static String intToString(Integer i) {
        if (i == null) {
            return "";
        }
        return i.toString();
    }

    /**
     * String to Integer
     *
     * @param s String
     * @return Integer
     */
    public static Integer stringToInteger(String s) {
        if (CheckUtils.isNull(s)) {
            return null;
        }
        if (!CheckUtils.isNumeric(s)) {
            return null;
        }
        return Integer.parseInt(s);
    }
    //endregion

    //region IP类相关转换方法

    /**
     * IP 的long类型转换成IP
     *
     * @param longIP long
     * @return IP
     */
    public static String longToIP(long longIP) {
        StringBuffer sb = new StringBuffer("");
        sb.append(String.valueOf(longIP >>> 24));
        sb.append(".");
        sb.append(String.valueOf(longIP >>> 16));
        sb.append(".");
        sb.append(String.valueOf(longIP >>> 8));
        sb.append(".");
        sb.append(String.valueOf(longIP & 0x000000FF));
        return sb.toString();
    }

    /**
     * IP转换成long
     *
     * @param strIP IP
     * @return long
     */
    public static long IPtoLong(String strIP) {
        if (CheckUtils.isNull(strIP)) {
            return 0L;
        }
        long[] ip = new long[4];
        int p1 = strIP.indexOf(".");
        int p2 = strIP.indexOf(".", p1 + 1);
        int p3 = strIP.indexOf(".", p2 + 1);
        ip[0] = Long.parseLong(strIP.substring(0, p1));
        ip[1] = Long.parseLong(strIP.substring(p1 + 1, p2));
        ip[2] = Long.parseLong(strIP.substring(p2 + 1, p3));
        ip[3] = Long.parseLong(strIP.substring(p3 + 1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }
    //endregion

    /**
     * 根据指定的标签，将字符转换成数组
     *
     * @param str        字符
     * @param splitLabel 标签
     * @return 数组
     */
    public static String[] splitStringByLabel(String str, String splitLabel) {
        if (CheckUtils.isNull(str) || CheckUtils.isNull(splitLabel)) {
            return null;
        }
        int i = 0;
        StringTokenizer tokenizer = new StringTokenizer(str, splitLabel);
        String[] strArray = new String[tokenizer.countTokens()];
        while (tokenizer.hasMoreTokens()) {
            strArray[i] = new String();
            strArray[i] = tokenizer.nextToken();
            i++;
        }
        return strArray;
    }

}
