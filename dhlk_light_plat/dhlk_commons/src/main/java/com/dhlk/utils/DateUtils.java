package com.dhlk.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Content: Date工具类
 * Author:jpdong
 * Date:2020/3/9
 */
public class DateUtils {

    /**
     * 获取当前的时间戳
     *
     * @return long
     */
    public static long getLongCurrentTimeStamp() {
        Date date = new Date();
        long time = date.getTime();
        return time;
    }

    /**
     * 获取当前的时间戳
     *
     * @return timestamp
     */
    public static Timestamp getCurrentTimeStamp() {
        Date date = new Date();
        long time = date.getTime();
        return new Timestamp(time);
    }

    /**
     * 获得当前日期
     *
     * @return Date
     */
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * 获取当前日期 yyyy-MM-dd
     *
     * @return String yyyy-MM-dd
     */
    public static String getToday() {

        return getToday("yyyy-MM-dd");
    }

    /**
     * 获取当前日期
     *
     * @param formatStr 默认 "yyyy-MM-dd"
     * @return String
     */
    public static String getToday(String formatStr) {
        if (CheckUtils.isNull(formatStr)) {
            return "yyyy-MM-dd";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
        Date d = new Date();
        String re = dateFormat.format(d);
        return re;
    }

    /**
     * 时间戳转换为日期
     *
     * @param millisecond
     * @return date
     * @author gchen
     * @date 2020/4/1 12:08
     */
    public static Date MillisecondToDate(long millisecond) {
        Date date = null;
        if (CheckUtils.isNull(millisecond)) {
            date = new Date(millisecond);
        }
        return date;
    }

    /***
     * 获取当前时间字符串精确到毫秒,如 20200612092750251
     * @return
     */
    public static String getNowTime() {
        Calendar nowtime = new GregorianCalendar();
        String strDateTime = String.format("%04d", nowtime.get(Calendar.YEAR)) + "" +
                String.format("%02d", nowtime.get(Calendar.MONTH) + 1) + "" +
                String.format("%02d", nowtime.get(Calendar.DATE)) + "" +
                String.format("%02d", nowtime.get(Calendar.HOUR)) + "" +
                String.format("%02d", nowtime.get(Calendar.MINUTE)) + "" +
                String.format("%02d", nowtime.get(Calendar.SECOND)) + "" +
                String.format("%03d", nowtime.get(Calendar.MILLISECOND));
        return strDateTime;
    }


    /**
     * 当前时间加往后加几分钟
     * @param addTime
     * @return
     */
    public static String getAddTime(String startTime,int addTime){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date now = null;
        try {
            now = sdf.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date afterDate = new Date(now.getTime() + addTime*60*1000);
        return sdf.format(afterDate);
    }

    public static String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date now = new Date();
        return sdf.format(now);
    }
    public static String getCurrentTime(String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date now = new Date();
        return sdf.format(now);
    }
    /**
     * 当前时间往前推一个小时
     * @param
     * @return
     */
    public static String getSubtractTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date now = new Date();
        Date afterDate = new Date(now.getTime() - 6*10*60*1000);
        return  sdf.format(afterDate);
    }

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
    public static String getSubtractDate(int dayNum,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date now = new Date();
        Date afterDate = new Date(now.getTime() - dayNum*24*60*60*1000);
        return sdf.format(afterDate);
    }
    /**
     * 倒计时
     * @param seconds  倒计时多少秒
     */
    public static int countDown(int seconds) {
        int i = seconds;
        while (i > 0) {
            System.err.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i--;
        }
        return i;
    }

    /**
     * 获取分钟差
     * @param nowTime
     * @param orTimestamp
     * @return
     */
    public static int getTimeDifference(Long nowTime, Long orTimestamp){
        return (int) ((nowTime - orTimestamp) / (1000 * 60));
    }

    /**
     * 获取当月的天数
     */
    public static int getCurrentMonthDay()
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
    /**
     * 根据 年、月 获取对应的月份的天数
     */
    public static int getDaysByYearMonth(int year, int month)
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }


    /**
     *计算两时间戳的差值 差值只设置 几天 几个小时 或 几分钟
     * 根据差值返回多长之间前或多长时间后
     *
     * */
    public static String getDistanceTime(long startTime, long currentTime) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long diff;

        if (startTime < currentTime) {
            diff = currentTime - startTime;
        } else {
            diff = startTime - currentTime;
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day != 0) return day + "天"+hour + "时"+min + "分" ;
        if (hour != 0) return hour + "时"+ min + "分";
        if (min != 0) return min + "分";
        //if (sec != 0) return sec + "秒" ;
        return "0分";
    }

    //将时间戳转换为时间
    public static String stampToTime(Long ts) throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(ts);//将时间调整为yyyy-MM-dd HH:mm:ss时间样式
        return  simpleDateFormat.format(date);
    }
}
