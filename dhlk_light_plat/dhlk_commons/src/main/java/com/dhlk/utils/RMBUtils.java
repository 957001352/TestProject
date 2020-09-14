package com.dhlk.utils;

/**
 * Content: 人民币小写转大写
 * Author:jpdong
 * Date:2020/1/21
 */
public class RMBUtils {
    private static final char[] data = new char[]{
            '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'
    };

    private static final char[] units = new char[]{
            '元', '拾', '佰', '仟', '万', '拾', '佰', '仟', '亿'
    };

    /**
     * 转换
     *
     * @param money 人民币金额
     * @return 大写的金额
     */
    public static String convert(int money) {
        StringBuffer sbf = new StringBuffer();
        int unit = 0;
        while (money != 0) {
            sbf.insert(0, units[unit++]);

            int number = money % 10;
            sbf.insert(0, data[number]);
            money = money / 10;
        }
        return sbf.toString();

    }
}

