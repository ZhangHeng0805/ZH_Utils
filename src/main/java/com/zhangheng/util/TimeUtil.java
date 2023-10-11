package com.zhangheng.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间工具类
 *
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-04 13:24
 */
public class TimeUtil extends DateUtil{

    /**
     * 格林威治时间-北京时区
     */
    public static final String TimeZoneID_GMT8 = "GMT+8";
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
        return toTime(date, dateFormat, null);
    }

    /**
     * 将日期转换为指定格式时间
     *
     * @param date       日期
     * @param dateFormat 指定的时间格式
     * @param timeZoneID 时区 GMT+8北京时间
     * @return 指定格式时间
     */
    public static String toTime(Date date, String dateFormat, String timeZoneID) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        if (!StrUtil.isBlank(timeZoneID))
            sdf.setTimeZone(TimeZone.getTimeZone(timeZoneID));
        return sdf.format(date);
    }

    /**
     * 获取当前时间
     *
     * @return 默认格式的时间
     */
    public static String getNowTime() {
        return toTime(new Date());
    }

    /**
     * 获取当前Unix时间戳
     *
     * @return 当前Unix时间戳
     */
    public static String getNowUnix() {
        return dateToUnix(new Date());
    }

    /**
     * 将默认格式的时间转换为日期
     *
     * @param time 默认格式的时间
     * @return 转换的日期
     */
    public static Date toDate(String time) throws ParseException {
        return toDate(time, DefaultDateFormat);
    }

    /**
     * 将指定格式的时间转换为日期
     *
     * @param time       指定格式的时间
     * @param dateFormat 指定的格式
     * @return 转换的日期
     * @throws ParseException
     */
    public static Date toDate(String time, String dateFormat) throws ParseException {
        return toDate(time, dateFormat, null);
    }

    /**
     * 将指定格式的时间转换为日期
     *
     * @param time       指定格式的时间
     * @param dateFormat 指定的格式
     * @param timeZoneID 时区 GMT+8北京时间
     * @return 转换的日期
     * @throws ParseException
     */
    public static Date toDate(String time, String dateFormat, String timeZoneID) throws ParseException {
        Date date = null;
        SimpleDateFormat simpleFormat = new SimpleDateFormat(dateFormat);
        if (!StrUtil.isBlank(timeZoneID))
            simpleFormat.setTimeZone(TimeZone.getTimeZone(timeZoneID));
        date = simpleFormat.parse(time);
        return date;
    }

    /**
     * 将Unix时间戳转换为日期Date
     *
     * @param unix Unix时间戳
     * @return 转换的日期
     */
    public static Date UnixToDate(String unix) {
        Long unixLong = Long.valueOf(unix) * 1000;
        Date UnixDate = DateUtil.date(unixLong);
        return UnixDate;
    }

    /**
     * 将Date日期转换为Unix时间戳
     *
     * @param date 日期
     * @return 转化的Unix时间戳
     */
    public static String dateToUnix(Date date) {
        return Long.toString(date.getTime() / 1000L);
    }

    /**
     * 获取当前时间指定信息
     * [年、月、日、时、分、秒、毫秒、星期、当前月的第几天、当前年的第几天]
     *
     * @param Type 本类中的类型属性
     * @return
     */
    public static String getTimeInfo(Integer Type) {
        cal=Calendar.getInstance();
        String t;
        int i = -1;
        switch (Type) {
            case Year://获取当前年
                i = cal.get(Calendar.YEAR);
                break;
            case Month://获取当前月
                i = cal.get(Calendar.MONTH) + 1;
                break;
            case Day://获取当前日
                i = cal.get(Calendar.DATE);
                break;
            case Hour://获取当前小时（24时）
                i = cal.get(Calendar.HOUR_OF_DAY);
                break;
            case Minutes://获取当前分
                i = cal.get(Calendar.MINUTE);
                break;
            case Second://获取当前秒
                i = cal.get(Calendar.SECOND);
                break;
            case MilliSecond://获取当前毫秒
                i = cal.get(Calendar.MILLISECOND);
                break;
            case Week://获取当前星期
                i = cal.get(Calendar.DAY_OF_WEEK);
                break;
            case Day_Of_Month://获取当前月的第几天
                i = cal.get(Calendar.DAY_OF_MONTH);
                break;
            case Day_Of_Year://获取当前年中的第几天
                i = cal.get(Calendar.DAY_OF_YEAR);
                break;
            default:
                return String.valueOf(i);
        }
        if (Type == Month || Type == Day || Type == Hour || Type == Minutes || Type == Second) {
            t = i < 10 ? "0" + i : String.valueOf(i);
        } else if (Type == Week) {
            t = WeekArray[i - 1];
        } else if (Type == MilliSecond) {
            String s = String.valueOf(i);
            if (s.length() < 3) {
                if (s.length() < 2) {
                    if (s.length() < 1) {
                        t = "000";
                    } else {
                        t = "00" + s;
                    }
                } else {
                    t = "0" + s;
                }
            } else {
                t = s;
            }
        } else {
            t = String.valueOf(i);
        }
        return t;
    }


    /**
     * 比较两个时间之间的差值（默认格式）
     *
     * @param time1 时间1
     * @param time2 时间2
     * @param Type  差值类型（月，日，时，分，秒）
     * @return 正确返回差值 错误返回-1
     */
    public static long timeDifference(String time1, String time2, int Type) throws ParseException {
        return timeDifference(time1, DefaultDateFormat, time2, DefaultDateFormat, Type);
    }

    /**
     * 计算两个时间之间的差值（相同的指定格式）
     *
     * @param time1      时间1
     * @param time2      时间2
     * @param Type       差值类型（月，日，时，分，秒）
     * @param dateFormat 两个时间的格式
     * @return 正确返回差值 错误返回-1
     */
    public static long timeDifference(String time1, String time2, String dateFormat, int Type) throws ParseException {
        return timeDifference(time1, dateFormat, time2, dateFormat, Type);
    }

    /**
     * 计算距离当前时间的差值
     *
     * @param time 计算的时间（默认格式）
     * @param Type 差值类型（月，日，时，分，秒）
     * @return 正确返回差值 错误返回-1
     */
    public static long timeDifference(String time, int Type) throws ParseException {
        return timeDifference(time, DefaultDateFormat, toTime(new Date()), DefaultDateFormat, Type);
    }

    /**
     * 算距离当前时间的差值
     *
     * @param Type       差值类型（月，日，时，分，秒）
     * @param time       计算的时间（指定格式）
     * @param dateFormat 计算的时间格式
     * @return 正确返回差值 错误返回-1
     */
    public static long timeDifference(int Type, String time, String dateFormat) throws ParseException {
        return timeDifference(time, dateFormat, toTime(new Date()), EnDateFormat_Detailed, Type);
    }

    /**
     * 计算两个时间之间的差值（不同格式的时间）
     *
     * @param time1       时间1
     * @param dateFormat1 时间1的格式
     * @param time2       时间2
     * @param dateFormat2 时间2的格式
     * @param Type        差值类型（分钟差、天数差。。。）
     * @return 正确返回差值 错误返回-1
     */
    public static long timeDifference(String time1, String dateFormat1, String time2, String dateFormat2, int Type) throws ParseException {
        Date fromDate = toDate(time1, dateFormat1);
        Date toDate = toDate(time2, dateFormat2);
        return calDifference(fromDate, toDate, Type);
    }

    /**
     * 计算差值
     *
     * @param fromDate 开始日期
     * @param toDate   结束日期
     * @param Type     差值类型
     * @return 正确返回差值 错误返回-1
     */
    public static long calDifference(Date fromDate, Date toDate, int Type) {
        long difference = -1;
        if (fromDate != null && toDate != null) {
            long from = fromDate.getTime();
            long to = toDate.getTime();
            switch (Type) {
                case Month:
                    difference = (long) ((to - from) * 1.0 / ((long) 1000 * 60 * 60 * 24 * 30));
                    break;
                case Day:
                    difference = (long) ((to - from) * 1.0 / (1000 * 60 * 60 * 24));
                    break;
                case Hour:
                    difference = (long) ((to - from) * 1.0 / (1000 * 60 * 60));
                    break;
                case Minutes:
                    difference = (long) ((to - from) * 1.0 / (1000 * 60));
                    break;
                case Second:
                    difference = (long) ((to - from) * 1.0 / (1000));
                    break;
                case MilliSecond:
                    difference = (long) (to - from);
                    break;
                default:
                    return difference;
            }
            difference = Math.abs(difference);
        }
        return difference;
    }

    /**
     * 计算默认格式时间半个月前某一天的时间
     *
     * @param time
     * @param i    前几天 范围[0,15]
     * @return 默认格式的时间
     */
    public static String fewDaysAgo(String time, int i) throws Exception {
        return fewDaysAgo(time, DefaultDateFormat, i);
    }

    /**
     * 计算指定时间15前某一天的时间
     *
     * @param time       指定时间
     * @param dateFormat 指定的时间格式
     * @param i          前几天 [0,15]
     * @return 指定格式的时间
     */
    public static String fewDaysAgo(String time, String dateFormat, int i) throws Exception {
        String day = null;
        SimpleDateFormat simpleFormat = new SimpleDateFormat(dateFormat);
        Date fromDate = toDate(time, dateFormat);
        long days = -1;
        if (i > 15 && i < 0)
            throw new Exception("[method:fewDaysAgo(...,int i) 'i' is error]参数'i'范围错误，参数'i'的范围：0~15。");
        if (fromDate != null) {
            long data = fromDate.getTime();
            long d = 1000 * 60 * 60 * 24 * i;
            days = data - d;
            Date date = new Date(days);
            day = toTime(date, dateFormat);
        }
        return day;
    }

    /**
     * 将毫秒格式化时间格式
     *
     * @param ms 毫秒
     * @return 00:00:00 / 00:00 格式的时间
     */
    public static String formatMS(int ms) {
        if (ms < 60000) {
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
        if (ms < (60 * 1000)) {
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
        String m = "";
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
