package com.zhangheng.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.zhangheng.util.MathUtil.pad2;
import static com.zhangheng.util.MathUtil.pad3;

/**
 * 时间工具类
 *
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-04 13:24
 */
public class TimeUtil extends DateUtil {

    /**
     * 中国时区
     */
    public static final String TimeZoneID_CN = "Asia/Shanghai";
    /**
     * 英文的时间格式（默认格式）
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String EnDateFormat = "yyyy-MM-dd HH:mm:ss";
    /**
     * 英文的时间格式（带时区）
     * yyyy-MM-dd'T'HH:mm:ssXXX
     */
    public static final String EnZoneDateFormat = "yyyy-MM-dd'T'HH:mm:ssXXX";
    /**
     * 英文的时间格式(精确到毫秒)
     * yyyy-MM-dd HH:mm:ss:SSS
     */
    public static final String EnDateFormat_Detailed = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * 英文的时间格式(精确到毫秒,带时区)
     * yyyy-MM-dd'T'HH:mm:ss:SSSXXX
     */
    public static final String EnZoneDateFormat_Detailed = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    /**
     * 中文格式的时间格式
     * yyyy年MM月dd日 HH:mm:ss
     */
    public static final String CnDateFormat = "yyyy年MM月dd日 HH:mm:ss";
    /**
     * 默认的时间格式
     * EnDateFormat
     */
    protected static String DefaultDateFormat = EnDateFormat;


    // 缓存 formatter，高并发无锁
    protected static final Map<String, DateTimeFormatter> FORMATTER_CACHE = new ConcurrentHashMap<>();


    /**
     * 星期名数组
     * {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"}
     */
    public static final String[] WeekArray = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    /**
     * 日期格式集合{类型:格式}
     * {Year:"yyyy",Month:"MM",Day:"dd",Hour:"HH",Minutes:"mm",Second:"ss",MilliSecond:"SSS"}
     */
    public static final Map<Integer, String> DateFormatMap = new HashMap<Integer, String>() {{
        put(Year, "yyyy");
        put(Month, "MM");
        put(Day, "dd");
        put(Hour, "HH");
        put(Minutes, "mm");
        put(Second, "ss");
        put(MilliSecond, "SSS");
        put(Week, "EEE");
    }};

    /**
     * Type（类型）
     * 年
     */
    public static final int Year = 0x0000;//年
    /**
     * Type（类型）
     * 月
     */
    public static final int Month = 0x0001;//月
    /**
     * Type（类型）
     * 日
     */
    public static final int Day = 0x0002;//日
    /**
     * Type（类型）
     * 时
     */
    public static final int Hour = 0x0003;//时
    /**
     * Type（类型）
     * 分
     */
    public static final int Minutes = 0x0004;//分
    /**
     * Type（类型）
     * 秒
     */
    public static final int Second = 0x0005;//秒
    /**
     * Type（类型）
     * 毫秒
     */
    public static final int MilliSecond = 0x0009;//毫秒
    /**
     * Type（类型）
     * 星期
     */
    public static final int Week = 0x0006;//星期
    /**
     * Type（类型）
     * 当前月中的第几天
     */
    public static final int Day_Of_Month = 0x0007;//一月中的第几天
    /**
     * Type（类型）
     * 当前年中的第几天
     */
    public static final int Day_Of_Year = 0x0008;//一年的第几天


    private static Calendar cal;

    /**
     * 将日期转换为默认格式时间
     *
     * @param date 日期
     * @return 默认格式时间
     */
    public static String toTime(Date date) {
        return toTime(date, DefaultDateFormat);
    }

    /**
     * 将日期转换为指定格式时间
     *
     * @param date       日期
     * @param dateFormat 指定的时间格式
     * @return 指定格式时间
     */
    public static String toTime(Date date, String dateFormat) {
        return toTime(date, dateFormat, TimeZoneID_CN);
    }

    /**
     * 将日期转换为指定格式时间
     *
     * @param date       日期
     * @param dateFormat 指定的时间格式
     * @param timeZoneID 时区
     * @return 指定格式时间
     */
    public static String toTime(Date date, String dateFormat, String timeZoneID) {
        if (date == null) {
            return null;
        }
        // 3. 日期格式化（JDK8+ 新时间API，无坑、超快）
        return getFormatter(dateFormat, timeZoneID).format(date.toInstant());
    }

    public static String toTime(long timeMillis) {
        return getFormatter(DefaultDateFormat, TimeZoneID_CN)
                .format(Instant.ofEpochMilli(timeMillis));
    }

    private static DateTimeFormatter getFormatter(String dateFormat, String timeZoneID) {
        String zoneId = timeZoneID == null ? ZoneId.systemDefault().getId() : timeZoneID;

        // 2. 生成缓存KEY：格式 + 时区（唯一组合，避免串时区）
        String cacheKey = dateFormat + "|" + zoneId;

        // 3. 缓存中不存在才创建，存在直接复用（真正全局单例）
        return FORMATTER_CACHE.computeIfAbsent(cacheKey,
                key -> DateTimeFormatter.ofPattern(dateFormat).withZone(ZoneId.of(zoneId))
        );
    }


    /**
     * 获取当前时间
     *
     * @return 默认格式的时间
     */
    public static String getNowTime() {
        return getFormatter(DefaultDateFormat, TimeZoneID_CN).format(Instant.now());
    }

    /**
     * 将默认格式的时间转换为日期
     *
     * @param time 默认格式的时间
     * @return 转换的日期
     */
    public static Date toDate(String time) {
        return toDate(time, DefaultDateFormat);
    }

    /**
     * 将指定格式的时间转换为日期
     *
     * @param time       指定格式的时间
     * @param dateFormat 指定的格式
     * @return 转换的日期
     */
    public static Date toDate(String time, String dateFormat) {
        return toDate(time, dateFormat, TimeZoneID_CN);
    }

    /**
     * 将指定格式的时间转换为日期
     *
     * @param time       指定格式的时间
     * @param dateFormat 指定的格式
     * @param timeZoneID 时区 GMT+8北京时间
     * @return 转换的日期
     */
    public static Date toDate(String time, String dateFormat, String timeZoneID) {
        if (StrUtil.isBlank(time)) {
            return null;
        }
        return Date.from(getFormatter(dateFormat, timeZoneID).parse(time, Instant::from));
    }

    /**
     * 将Unix时间戳转换为日期Date
     *
     * @param unix Unix时间戳
     * @return 转换的日期
     */
    public static Date unixToDate(String unix) {
        // 1. 空值校验
        if (StrUtil.isBlank(unix)) {
            return null;
        }

        try {
            // 2. 秒 → 毫秒（Unix 时间戳通常是秒）
            long seconds = Long.parseLong(unix.trim());
            long milliseconds = seconds * 1000L;

            // 3. 直接返回，比 hutool 更轻量、更快
            return new Date(milliseconds);
        } catch (NumberFormatException e) {
            // 非法数字，返回 null 或抛出异常（根据你的业务决定）
            return null;
        }
    }

    /**
     * 将Date日期转换为Unix时间戳
     *
     * @param date 日期
     * @return 转化的Unix时间戳
     */
    public static String dateToUnix(Date date) {
        return String.valueOf(date.getTime() / 1000L);
    }

    /**
     * 获取当前Unix时间戳
     *
     * @return 当前Unix时间戳
     */
    public static String getNowUnix() {
        return String.valueOf(System.currentTimeMillis() / 1000L);
    }

    /**
     * 获取当前时间指定信息
     * [年、月、日、时、分、秒、毫秒、星期、当前月的第几天、当前年的第几天]
     *
     * @param type 本类中的类型属性
     * @return
     */
    public static String getNowTimeInfo(Integer type) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of(TimeZoneID_CN));
        switch (type) {
            case Year:
                return String.valueOf(now.getYear());
            case Month:
                return pad2(now.getMonthValue());
            case Day:
                return pad2(now.getDayOfMonth());
            case Hour:
                return pad2(now.getHour());
            case Minutes:
                return pad2(now.getMinute());
            case Second:
                return pad2(now.getSecond());
            case MilliSecond:
                return pad3(now.getNano() / 1_000_000); // 纳秒转毫秒
            case Week:
                return WeekArray[now.getDayOfWeek().getValue() % 7]; // 对齐周日=0
            case Day_Of_Month:
                return String.valueOf(now.getDayOfMonth());
            case Day_Of_Year:
                return String.valueOf(now.getDayOfYear());
            default:
                return null;
        }
    }

    /**
     * 将毫秒格式化时间格式
     *
     * @param ms 毫秒
     * @return 00:00:00 / 00:00 格式的时间
     */
    public static String formatMS(int ms) {
        if (ms < 1000) {
            return ms + "ms";
        } else if (ms < 60000) {
            return "00:" + getString((ms % 60000) / 1000);
        } else if (ms < 3600000) {
            return getString((ms % 3600000) / 60000) + ":" + getString((ms % 60000) / 1000);
        } else {
            return getString(ms / 3600000) + ":" + getString((ms % 3600000) / 60000) + ":" + getString((ms % 60000) / 1000);
        }
    }

    /**
     * 将毫秒格式化时间格式
     *
     * @param ms 毫秒
     * @return xx天xx时xx分xx秒格式时间
     */
    public static String formatMSToCn(int ms) {
        if (ms < 1000) {
            return ms + "毫秒";
        } else if (ms < (60 * 1000)) {
            return getString(((ms % (60 * 1000)) / 1000)) + "秒";
        } else if (ms < 60 * 60 * 1000) {
            return getString((ms % (60 * 60 * 1000)) / (60 * 1000)) + "分" + getString((ms % (60 * 1000)) / 1000) + "秒";
        } else if (ms < 24 * 60 * 60 * 1000) {
            return getString(ms / (60 * 60 * 1000)) + "时" + getString((ms % (60 * 60 * 1000)) / (60 * 1000)) + "分" + getString((ms % (60 * 1000)) / 1000) + "秒";
        } else {
            return getString(ms / (24 * 60 * 60 * 1000)) + "天" + getString((ms % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000)) + "时" + getString((ms % (60 * 60 * 1000)) / (60 * 1000)) + "分" + getString((ms % (60 * 1000)) / 1000) + "秒";
        }
    }

    /**
     * formatMS()的内置方法
     *
     * @param t
     * @return
     */
    private static String getString(int t) {
        String m;
        if (t > 0) {
            if (t < 10) {
                m = "0" + t;
            } else {
                m = t + "";
            }
        } else {
            m = "00";
        }
        return m;
    }

}
