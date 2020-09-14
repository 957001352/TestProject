package com.dhlk.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @program: server
 * @description: 时间工具类
 * @author: wqiang
 * @create: 2020-07-06 09:26
 **/
public class DateUtils {


    /**
     * 当前时间往前推几天
     * @param dayNum  要推的天数
     * @return
     */
    public static String getSubtractDate(int dayNum){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date now = new Date();
        Date afterDate = new Date(now.getTime() - dayNum*24*60*60*1000);
        return sdf.format(afterDate);
    }


    /**
     * 计算小时差
     * @param endTime
     * @param startTime
     * @return
     */
    public static Long getMistiming(String endTime,String startTime) {
        DateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        // 获得两个时间的毫秒时间差异
        try {
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            day = diff / nd;// 计算差多少天
            hour = diff % nd / nh + day * 24;// 计算差多少小时
            min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
            sec = diff % nd % nh % nm / ns;// 计算差多少秒
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return hour;
    }

    /**
     * 获取当前时间
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String re = dateFormat.format(d);
        return re;
    }

    public static String getTransitionTime(Long millisecond){
        if(millisecond != null && millisecond > 0){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = new Date();
            d.setTime(millisecond*1000l);
            return dateFormat.format(d);
        }
        return "";
    }

    /**
     * 将国际化时间转转义 例如：Tue May 12 10:23:29 2020 转换为 2020-05-12 10:23:29
     * @param BIH  国际时间：Tue May 12 10:23:29 2020
     * @return
     */

    public static String getTransformBIH(String BIH){

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf2 =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
           date = sdf.parse(BIH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf2.format(date);
    }

}
