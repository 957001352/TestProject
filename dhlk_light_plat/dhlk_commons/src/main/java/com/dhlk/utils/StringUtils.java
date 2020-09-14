package com.dhlk.utils;




import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.dhlk.utils.CheckUtils.isNull;

/**
 * Content:
 * Author:jpdong
 * Date:2020/3/4
 */
public class StringUtils {

    // 特殊字符常量
    private static final String REGEX = "[\n`~!@#$%^&*()+-=|{}':;'\\[\\].<>/?！￥……（）_——【】‘；：”“’。 、？]";

    /**
     * 获取的[minValue,maxValue]长度的随机整数
     *
     * @param minValue 下界
     * @param maxValue 上界
     * @return
     */
    public static Integer getRnd(Integer minValue, Integer maxValue) {
        return (int) (Math.random() * (maxValue - minValue)) + minValue;
    }

    /**
     * 比较两个字符是否相同
     *
     * @param s1 字符1
     * @param s2 字符2
     * @return true or false
     */
    public static Boolean isEqual(String s1, String s2) {
        s1 = s1.trim().toLowerCase();
        s2 = s2.trim().toLowerCase();
        return s1.equals(s2);
    }

    /**
     * 截取指定长度的字符，兼容中英文模式
     *
     * @param str    字符
     * @param length 长度
     * @return 截取指定长度的字符
     */
    public static String cutString(String str, int length) {
        if (isNull(str)) {
            return "";
        }
        try {
            if (str.getBytes("GBK").length < length) {
                return str;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        StringBuffer sb = new StringBuffer();
        int count = 0;
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                if (count <= length - 2) {
                    sb.append(c);
                }
                count = +2;
            } else {
                if (count < length) {
                    sb.append(c);
                }
                count++;
            }
        }
        return sb.toString() + "...";

    }

    /**
     * 判断是否是中文
     *
     * @param chineseStr String
     * @return true or false
     */
    public static boolean isChinese(String chineseStr) {
        if (isNull(chineseStr)) {
            return false;
        }
        char[] chars = chineseStr.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if ((chars[i] >= '\u0000' && chars[i] < '\uFFFD') || ((chars[i] > '\uFFFD' && chars[i] < '\uFFFF'))) {
                continue;
            } else {
                return false;
            }
        }
        return true;

    }

    /**
     * 判断char是否是中文
     *
     * @param c char
     * @return true or false
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }


    /**
     * 判断参数是否为空
     * @param cs
     * @return
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }


    /**
     *特殊字符转换成""
     * @return
     */
    public static String charmapChange(String str){
        String res = " ";
        Pattern p = Pattern.compile(REGEX);
        //把想要替换的字符串传进来
        Matcher m = p.matcher(str);
        String newString = m.replaceAll(res).trim();
        return newString;
    }

    /**
     * 判断Object类型是否为null
     * @param obj
     * @return
     */
//    public static Boolean isObjectNotEmpty(Object obj) {
//        String str = ObjectUtils.toString(obj, "");
//        return org.apache.commons.lang3.StringUtils.isNotBlank(str);
//    }

}
