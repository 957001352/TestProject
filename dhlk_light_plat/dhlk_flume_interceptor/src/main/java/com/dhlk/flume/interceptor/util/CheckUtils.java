package com.dhlk.flume.interceptor.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Content: 检验性工具类
 * Author:jpdong
 * Date:2020/3/3
 */
public class CheckUtils {
    //region isNull(对象)判断是否是空

    /**
     * 判断integer是否是空
     *
     * @param i integer
     * @return true/false
     */
    public static Boolean isNull(Integer i) {
        try {
            if(i==null){
                return true;
            }
            return isNull(i.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断 int 是否是空
     *
     * @param i int
     * @return true/false
     */
    public static Boolean isNull(int i) {
        try {
            return isNull(i + "");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断 float 是否是空
     *
     * @param i float
     * @return true/false
     */
    public static Boolean isNull(float i) {
        try {
            return isNull(i + "");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断 Float 是否是空
     *
     * @param i Float
     * @return true/false
     */
    public static Boolean isNull(Float i) {
        try {
            return isNull(i.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 判断 double 是否是空
     *
     * @param i float
     * @return true/false
     */
    public static Boolean isNull(double i) {
        try {
            return isNull(i + "");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断 Float 是否是空
     *
     * @param i Float
     * @return true/false
     */
    public static Boolean isNull(Double i) {
        try {
            return isNull(i.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断Object是否是空
     *
     * @param i Object
     * @return true/false
     */
    public static Boolean isNull(Object i) {
        try {
            return isNull(i + "");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

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
    //endregion

    //region 判断数字、字符是否是数字

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
     * 判断是否是数字类字
     *
     * @param s 待判断的Integer
     * @return true/false
     */
    public static Boolean isNumeric(Integer s) {
        if (s == null) {
            return false;
        }
        return isNumeric(s.toString());
    }

    /**
     * 判断是否是数字类字符串
     *
     * @param s 待判断的Int
     * @return true/false
     */
    public static Boolean isNumeric(int s) {
        return isNumeric(s + "");
    }


    /**
     * 判断是否是整形数字类字符串 String
     *
     * @param s 待判断的字符
     * @return true/false
     */
    public static Boolean isIntNumeric(String s) {
        if (s == null) {
            return false;
        }

        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(s).matches();
    }

    /**
     * 判断是否是整形数字类字 Integer
     *
     * @param s 待判断的Integer
     * @return true/false
     */
    public Boolean isIntNumeric(Integer s) {
        if (s == null) {
            return false;
        }
        return isIntNumeric(s.toString());
    }

    /**
     * 判断是否是整形数字类int
     *
     * @param s 待判断的Int
     * @return true/false
     */
    public static Boolean isIntNumeric(int s) {
        return isIntNumeric(s + "");
    }
    //endregion

    //region 账号、密码验证判断相关

    /**
     * 判断是否是密码，长度6~20
     *
     * @param str String
     * @return true/false
     */
    public static Boolean isPassword(String str) {
        if (isNull(str)) {
            return false;
        }
        str = str.trim();
        if (str.length() < 6 || str.length() > 20) {
            return false;
        }
        return true;
    }


    /**
     * 判断是否是ID,长度6-20
     *
     * @param str String
     * @return true/false
     */
    public static Boolean isID(String str) {
        if (isNull(str)) {
            return false;
        }
        str = str.trim();
        if (str.length() < 6 || str.length() > 20) {
            return false;
        }
        return true;
    }


    /**
     * 判断是否是管理员账号,长度4-20
     *
     * @param str String
     * @return true
     */
    public static Boolean isAdminID(String str) {
        if (isNull(str)) {
            return false;
        }
        str = str.trim();
        if (str.length() < 4 || str.length() > 20) {
            return false;
        }
        return true;
    }
    //endregion

    //region Email、手机号、身份证，网址的是否检查

    /**
     * 判断是否是Email
     *
     * @param str String
     * @return true/false
     */
    public static Boolean isEmail(String str) {
        if (isNull(str)) {
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(str);
        if (m.matches())
            return true;
        else
            return false;
    }

    /**
     * 判断是否是手机号
     *
     * @param str String
     * @return true/false
     */
    public static Boolean isMobize(String str) {
        if (isNull(str)) {
            return false;
        }
        String regex = "^((13[0-9])|(14[5,9])|(15[0-3,5-9])|(16[5-6])|(17[0-8])|(18[0-9])|198|199)\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        return m.matches();
    }


    /**
     * 判断是否是URL
     *
     * @param url String
     * @return true/false
     */
    public static Boolean isURL(String url) {
        // URL验证规则
        String regEx = "[a-zA-z]+://[^\\s]*";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();
        return rs;
    }

    /**
     * 判断是否是IP
     *
     * @param addr
     * @return
     */
    public boolean isIP(String addr) {
        if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

        Pattern pat = Pattern.compile(rexp);

        Matcher mat = pat.matcher(addr);

        boolean ipAddress = mat.find();

        return ipAddress;
    }
    //endregion

    /**
     * 判断是否是日期
     *
     * @param str string
     * @return true / false
     */
    public static Boolean isDate(String str) {
        if (str==null||isNull(str)){
            return false;
        }
        boolean result = true;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            format.setLenient(false);
            format.parse(str);
        } catch (Exception e) {
            result = false;
        }
        return result;

    }

    /**
     * 检查主键id是否为数字且是否大于等于0
     * @author      cg
     * @param id
     * @date        2020/3/16 12:04
     */
    public static boolean checkId(Integer id){
        if(CheckUtils.isNumeric(id) && id >= 0){
            return true;
        }
        return false;
    }
    /**
     * 检查状态码status是否在0,1,2范围内
     * @author      cg
     * @param status
     * @date        2020/3/16 12:04
     */
    public static boolean checkStatus(Integer status){
        if(CheckUtils.isNumeric(status) && status >= 0 && status <= 2){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Integer i=null;
        String s="";
        System.out.println(isNull(s));
    }

}
