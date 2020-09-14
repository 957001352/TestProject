package utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: server
 * @description: 操作字符串工具类
 * @author: wqiang
 * @create: 2020-07-03 16:52
 **/
public class StringUtils {



    /**
     * 判断String是否是空
     *
     * @param s String
     * @return true/false
     */

    public static Boolean isNull(String s) {
        try {
            if (s == null || s.equals(null)) {
                s = "";
            }
            s = s.trim();
            if (s.length() < 1) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断字符串是空或则空串
     *
     * @param s
     * @return
     */
    public static Boolean isNullOrEmpty(String s) {
        if (s == null || s.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是数字类字符串
     *
     * @param str 待判断的字符
     * @return true/false
     */
    public static Boolean isNumeric(String str) {
        // 该正则表达式可以匹配所有的数字 包括负数
        Pattern pattern = Pattern.compile("-?[0-9]+(\\.[0-9]+)?");
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }

        Matcher isNum = pattern.matcher(bigStr); // matcher是全匹配
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }


    /**
     * 判断一个字符串是否含有数字
     *
     * @param content
     * @return
     */
    public static boolean HasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;

    }

}
