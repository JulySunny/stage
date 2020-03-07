package com.gabriel.stage.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;


/**
 * @author: Gabriel
 * @date: 2019/12/3 22:49
 * @description 日期时间转换工具类
 */
public class LocalDataTimeUtils {

    public static final String FORMATTER_YYYY_M_MDD_H_HMMSS = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMATTER_YYYY_M_MDD_H_HMM = "yyyy-MM-dd HH:mm";

    public static final String FORMATTER_YYYY_M_MDD = "yyyy-MM-dd";

    /**
     * 将Date类型转化为指定格式string
     * @param date
     * @param format
     * @return
     */
    public static String getStringByDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 将String类型转化为指定格式Date类型
     * @param dateStr
     * @param format
     * @return
     */
    public static Date getDateByString(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前Date并转化为指定格式字符串
     * @param format
     * @return
     */
    public static String getCurrentDate(String format) {
        return getStringByDate(new Date(), format);
    }

    /**
     * Date转换为LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime convertDateToLDT(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转换为Date
     * @param time
     * @return
     */
    public static Date convertLDTToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * 获取指定日期的秒
     * @param time
     * @return
     */
    public static Long getMilliByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取指定日期的秒
     * @param time
     * @return
     */
    public static Long getSecondsByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * 获取指定时间的指定格式
     * @param time
     * @param pattern
     * @return
     */
    public static String formatTime(LocalDateTime time,String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取当前时间的指定格式
     * @param pattern
     * @return
     */
    public static String formatNow(String pattern) {
        return  formatTime(LocalDateTime.now(), pattern);
    }

    /**
     * 日期加上一个数,根据field不同加不同值,field为ChronoUnit.*
     * @param time
     * @param number
     * @param field
     * @return
     */
    public static LocalDateTime plus(LocalDateTime time, long number, TemporalUnit field) {
        return time.plus(number, field);
    }

    /**
     * 日期减去一个数,根据field不同减不同值,field参数为ChronoUnit.*
     * @param time
     * @param number
     * @param field
     * @return
     */
    public static LocalDateTime minu(LocalDateTime time, long number, TemporalUnit field){
        return time.minus(number,field);
    }

    /**
     * 获取两个日期的差  field参数为ChronoUnit.*
     * @param startTime
     * @param endTime
     * @param field
     * @return
     */
    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) return period.getYears();
        if (field == ChronoUnit.MONTHS) return period.getYears() * 12 + period.getMonths();
        return field.between(startTime, endTime);
    }

    /**
     * 比较两个Date的时间大小
     * @param date
     * @param date2
     * @return
     */
    public static boolean getLocalDateTime(Date date, Date date2) {
        if (date.compareTo(date2) == 1) {
            return true;
        }
        return false;
    }

    /**
     * 将LocalDateTime转化为Date
     * @param localDateTime
     * @return
     */
    public static Date getDataByLocalDateTime(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将指定格式String类型日期转化为LocalDateTime
     * @param format
     * @param localDateTime
     * @return
     */
    public static LocalDateTime getLocalDateTimeByString(String format,String localDateTime) {
        DateTimeFormatter df= DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(localDateTime, df);
    }

    /**
     * 获取指定LocalDateTime的秒数
     * @param localDateTime
     * @return
     */
    public static Long getSecondByLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * 获取指定LocalDateTime的毫秒数
     * @param localDateTime
     * @return
     */
    public static Long getMillSecondByLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 将LocalDateTime转化为指定格式字符串
     * @param localDateTime
     * @param format
     * @return
     */
    public static String getStringByLocalDateTime(LocalDateTime localDateTime, String format) {
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 将LocalDateTime转化为默认yy-mm-dd hh:mm:ss格式字符串
     * @param localDateTime
     * @return
     */
    public static String getDefaultStringByLocalDateTime(LocalDateTime localDateTime) {
        return getStringByLocalDateTime(localDateTime, FORMATTER_YYYY_M_MDD_H_HMMSS);
    }

    /**
     * 获取当前LocalDateTime并转化为指定格式字符串
     * @param format
     * @return
     */
    public static String getCurrentLocalDateTime(String format) {
        return getStringByLocalDateTime(LocalDateTime.now(), format);
    }

    /**
     * 获取某天的开始时间
     * @param localDateTime
     * @return
     */
    public static String getStartTime(LocalDateTime localDateTime) {
        return getDefaultStringByLocalDateTime(localDateTime.with(LocalTime.MIN));
    }

    /**
     * 获取一天的开始时间
     * @return
     */
    public static String getStartTime() {
        return getDefaultStringByLocalDateTime(LocalDateTime.now().with(LocalTime.MIN));
    }

    /**
     * 获取一天的结束时间
     * @param localDateTime
     * @return
     */
    public static String getEndTime(LocalDateTime localDateTime) {
        return getDefaultStringByLocalDateTime(localDateTime.with(LocalTime.MAX));
    }

    /**
     * 获取当天的结束时间
     * @return
     */
    public static String getEndTime() {
        return getDefaultStringByLocalDateTime(LocalDateTime.now().with(LocalTime.MAX));
    }

    /**
     * 比较两个LocalDateTime的时间大小
     * @param localDateTime1
     * @param localDateTime2
     * @return
     */
    public static boolean getCompareLocalDateTime(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        if (localDateTime1.isBefore(localDateTime2)) {
            return true;
        }
        return false;
    }

    /**
     * 计算两个指定格式String日期字符串的时间差(精确到毫秒)
     * @param format
     * @param time1
     * @param time2
     * @return
     */
    public static Long getCompareSecondLocalDateTime(String format ,String time1, String time2) {
        System.out.println("接收到的参数为:"+format+","+time1+","+time2);
        LocalDateTime localDateTime1=getLocalDateTimeByString(format,time1);
        LocalDateTime localDateTime2=getLocalDateTimeByString(format,time2);
        if(getMillSecondByLocalDateTime(localDateTime1)>getMillSecondByLocalDateTime(localDateTime2)) {
            return getMillSecondByLocalDateTime(localDateTime1)-getMillSecondByLocalDateTime(localDateTime2);
        }
        return getMillSecondByLocalDateTime(localDateTime2)-getMillSecondByLocalDateTime(localDateTime1);
    }

    /**
     * 获取两个LocalDateTime的天数差
     * @param localDateTime1
     * @param localDateTime2
     * @return
     */
    public static Long getCompareDayLocalDateTime(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        if (getCompareLocalDateTime(localDateTime1, localDateTime2)) {
            Duration duration = Duration.between(localDateTime1, localDateTime2);
            return duration.toDays();
        } else {
            Duration duration = Duration.between(localDateTime2, localDateTime1);
            return duration.toDays();
        }
    }

    /**
     * 获取两个LocalDateTime的小时差
     * @param localDateTime1
     * @param localDateTime2
     * @return
     */
    public static Long getCompareYearLocalDateTime(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        if (getCompareLocalDateTime(localDateTime1, localDateTime2)) {
            Duration duration = Duration.between(localDateTime1, localDateTime2);
            return duration.toHours();
        } else {
            Duration duration = Duration.between(localDateTime2, localDateTime1);
            return duration.toHours();
        }
    }


}
