package com.zhifan.utils;


import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * @author Miracle
 * @date 2019/7/9 14:39
 */
public class DateUtils {



    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).withZone(ZoneId.systemDefault());

    public static final DateTimeFormatter DATE_TIME_HOUR_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH", Locale.getDefault()).withZone(ZoneId.systemDefault());

    /**
     * 转换空格符号位地址栏%20
     */
    public static final DateTimeFormatter DATE_TIME_FORMATTER_URL = DateTimeFormatter.ofPattern("yyyy-MM-dd%20HH:mm:ss", Locale.getDefault()).withZone(ZoneId.systemDefault());


    public static final DateTimeFormatter DATE_TIME_T_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).withZone(ZoneId.systemDefault());

    public static final DateTimeFormatter DATE_TIME_FILE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss", Locale.getDefault()).withZone(ZoneId.systemDefault());
    /**
     * 缺省的时间戳格式
     */
    public static final String DEFAULT_TIMESTAMP = "yyyyMMddHHmmss";


    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * 时间处理，在原有的文字时间加减上特定秒数
     * @param time
     * @param second
     * @return
     */
    public static String dealTime(LocalDateTime time, Integer second) {
        return DateUtils.DATE_TIME_FORMATTER.format(time.plusSeconds(second));
    }

    /**
     * 时间字符串转int时间戳 换取当前整点的
     * @param dateStr
     * @return
     */
    public static long dealDateHourTime(String dateStr,Integer second){
        LocalDateTime localDateTime = LocalDateTime.from(DateUtils.DATE_TIME_FORMATTER.parse(dateStr));
        String format = DateUtils.DATE_TIME_HOUR_FORMATTER.format(localDateTime);
        return dealStringToDateHour(format,second);

    }

    public static long dealStringToDateHour(String dateStr,Integer second){
        LocalDateTime localDateTime = LocalDateTime.from(DateUtils.DATE_TIME_HOUR_FORMATTER.parse(dateStr)).plusSeconds(second);
        return localDateTime.toEpochSecond(ZoneOffset.of("+8"));
    }


    /**
     * int时间戳转时间字符串
     * @param timestamp 秒级时间戳
     * @return
     */
    public static String TimeStampToString(long timestamp,Integer second){
        LocalDateTime localDateTime;
        if(second == null){
             localDateTime = Instant.ofEpochSecond(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();

        }else {
             localDateTime = Instant.ofEpochSecond(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime().plusSeconds(second);

        }
        return DateUtils.DATE_TIME_FORMATTER.format(localDateTime);

    }

    /**
     * 时间字符串转int时间戳
     * @param dateStr
     * @return
     */
    public static long StringToTimeStamp(String dateStr){
        LocalDateTime localDateTime = LocalDateTime.from(DateUtils.DATE_TIME_FORMATTER.parse(dateStr));
        return localDateTime.toEpochSecond(ZoneOffset.of("+8"));

    }



    /**
     * 将日期类型转换成指定格式的日期字符串
     *
     * @param date   - Date日期对象
     * @param format - 日期格式字符串
     * @return - 指定日期类型格式的时间字符串
     */
    public static String convert(Date date, String format) {
        return convertDateToStr(date, format);
    }

    /**
     * 将日期类型转换成指定格式的日期字符串
     *
     * @param date       待转换的日期
     * @param dateFormat 日期格式字符串
     * @return String
     */
    public static String convertDateToStr(Date date, String dateFormat) {

        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }
}
