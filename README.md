# ZH_Utils
个人java开发工具包（持续更新中。。。）
* 消息实体
* 时间帮助
* 文件扫描
* 文件类型
* 格式校验
* 随机字段及数字
* 数学工具
* 加密工具

# 时间工具（TimeUtil）
* 类型路径：```com.zhangheng.util```
```java
	/**
     * 英文的时间格式（默认格式）
     */
    public static final String enDateFormat = "yyyy-MM-dd HH:mm:ss";
    /**
     * 英文的时间格式(精确到毫秒)
     */
    public static final String enDateFormat_Detailed = "yyyy-MM-dd HH:mm:ss:SSS";
    /**
     * 中文格式的时间格式
     */
    public static final String cnDateFormat = "yyyy年MM月dd日 HH:mm:ss";
    /**
     * 星期名数组
     */
    public static final String[] WeekArray={"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
    /**
     * 日期格式数组{"年","月","日","时","分","秒","毫秒"}
     */
    public static final String[] dateFormatArr={"yyyy","MM","dd","HH","mm","ss","SSS"};
    /**
     * Type（类型）
     * 年
     */
    public static final int Year=0x0000;//年
    /**
     * Type（类型）
     * 月
     */
    public static final int Month=0x0001;//月
    /**
     * Type（类型）
     * 日
     */
    public static final int Day=0x0002;//日
    /**
     * Type（类型）
     * 时
     */
    public static final int Hour=0x0003;//时
    /**
     * Type（类型）
     * 分
     */
    public static final int Minutes=0x0004;//分
    /**
     * Type（类型）
     * 秒
     */
    public static final int Second=0x0005;//秒
    /**
     * Type（类型）
     * 毫秒
     */
    public static final int MilliSecond=0x0009;//毫秒
    /**
     * Type（类型）
     * 星期
     */
    public static final int Week=0x0006;//星期
    /**
     * Type（类型）
     * 当前月中的第几天
     */
    public static final int Day_Of_Month=0x0007;//一月中的第几天
    /**
     * Type（类型）
     * 当前年中的第几天
     */
    public static final int Day_Of_Year=0x0008;//一年的第几天
```

> ####  TimeUtil.toTime(Date date);
> > * 将日期转换为默认格式[yyyy-MM-dd HH:mm:ss]时间
> > * @param date 日期
> > * @return String

> ####  TimeUtil.toTime(Date date,String dateFormat);
> > * 将日期转换为指定格式时间
> > * @param date 日期
> > * @param dateFormat 指定的时间格式
> > * @return String

> ####  TimeUtil.toDate(String time);
> > * 将默认格式的时间[yyyy-MM-dd HH:mm:ss]转换为日期
> > * @param time 默认格式的时间
> > * @return Date

> ####  TimeUtil.Date toDate(String time,String dateFormat);
> > * 将指定格式的时间转换为日期
> > * @param time 默认格式的时间
> > * @param dateFormat 指定的时间格式 
> > * @return Date

> ####  TimeUtil.getTime(int Type);
> > * 根据类型获取当前时间信息
> > * @param Type 不同类型的时间（年、月、日）
> > * @return String

> ####  TimeUtil.timeDifference(String time,int Type);
> > * 计算距离当前时间的差值
> > * @param time 计算的时间（默认格式）
> > * @param Type 差值类型（月，日，时，分，秒，毫秒）
> > * @return int （错误返回-1）

> ####  TimeUtil.timeDifference(int Type,String time,String dateFormat);
> > * 计算距离当前时间的差值
> > * @param Type 差值类型（月，日，时，分，秒，毫秒）
> > * @param time 计算的时间（指定格式）
> > * @param dateFormat 计算的时间格式
> > * @return int （错误返回-1）

> ####  TimeUtil.TimeDifference(String time1,String time2,int Type);
> > * 比较两个时间之间的差值（默认格式）
> > * @param time1 时间1
> > * @param time2 时间2
> > * @param Type 差值类型（月，日，时，分，秒，毫秒）
> > * @return int （错误返回-1）

> ####  TimeUtil.timeDifference(String time1,String time2,String dateFormat,int Type);
> > * 计算两个时间之间的差值（相同的指定格式）
> > * @param time1 时间1
> > * @param time2 时间2
> > * @param dateFormat 两个时间的格式
> > * @param Type 差值类型（月，日，时，分，秒，毫秒）
> > * @return int （错误返回-1）

> ####  TimeUtil.timeDifference(String time1,String dateFormat1,String time2,String dateFormat2,int Type);
> > * 计算两个时间之间的差值（不同格式的时间）
> > * @param time1 时间1
> > * @param dateFormat1 时间2的格式
> > * @param time2 时间2
> > * @param dateFormat2 时间2的格式
> > * @param Type 差值类型（月，日，时，分，秒，毫秒）
> > * @return int （错误返回-1）

> ####  TimeUtil.calDifference(Date fromDate, Date toDate, int Type);
> > * 计算两个日期之间的差值
> > * @param fromDate 开始日期
> > * @param toDate 截止日期
> > * @param Type 差值类型（月，日，时，分，秒，毫秒）
> > * @return int （错误返回-1）

> ####  TimeUtil.fewDaysAgo(String time,int i);
> > * 计算某个时间前半个月前某一天的时间 （默认格式）
> > * @param time 默认格式的时间
> > * @param i [0,15] 前几天
> > * @return String

> ####  TimeUtil.fewDaysAgo(String time,String dateFormat,int i);
> > * 计算某个时间前半个月前某一天的时间 （指定格式）
> > * @param time 指定格式的时间
> > * * @param dateFormat 指定的时间格式
> > * @param i [0,15] 前几天
> > * @return String