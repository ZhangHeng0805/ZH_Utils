# ZH_Utils
## [API文档](https://apidoc.gitee.com/ZhangHeng0805/ZH_Utils)
> **个人java开发工具包（持续更新中。。。）**
> * 参考依赖：
>   * [```Hutool```](https://www.hutool.cn/docs/#/)[Java工具类库]
>   * [```JSON-java```](https://github.com/stleary/JSON-java)[org.json]
### 实体```[com.zhangheng.bean]```
   * 消息实体```com.zhangheng.bean.Message```
   * 常量实体（没有上传）```com.zhangheng.bean.Const```
### json和xml的相互转换 ```[com.zhangheng.json]```
   * xml转json：```XML.toJSONObject("<a id=\"tag\">123</a>").toString()``` （支持xml标签属性保留转换）
   * json转xml：```XML.toString(new JSONObject("{\"a\": {\"-id\": 1,\"#text\": 2,}}"))```（前缀为-的字段为标签属性，字段#text为标签内容）
   * 示例
   ```xml
   <a a_id="1" a_name="2">
     <b b_id="b1">1</b>
     <b b_id="b2">2</b>
     <b>
       <content>123</content>
     </b>
     <c xmlns:N1="abc">
       <N1:c1>c1</N1:c1>
     </c>
   </a>
   ```
   ```json
    {"a": {
      "-a_id": 1,
      "-a_name": 2,
      "b": [
        {
          "-b_id": "b1",
          "#text": 1
        },
        {
          "-b_id": "b2",
          "#text": 2
        },
        {"content": 123}
      ],
      "c": {
        "-xmlns:N1": "abc",
        "N1:c1": "c1"
      }
    }}
   ```
### 工具```[com.zhangheng.util]```
   * 时间工具```com.zhangheng.util.TimeUtil```
   * 文件扫描```com.zhangheng.util.FolderFileScannerUtil```
   * 格式校验```com.zhangheng.util.FormatUtil```
   * 随机字段及数字```com.zhangheng.util.RandomrUtil```
   * 数学工具```com.zhangheng.util.MathUtil```
   * 加密工具```com.zhangheng.util.EncryptUtil```
   * 网络工具```com.zhangheng.util.NetUtil```
   * 数组工具```com.zhangheng.util.ArrayUtil```
   * 邮件工具```com.zhangheng.util.EmailUtil```
### 日志```[com.zhangheng.log]```
  * 格式: ```2022-03-22 16:26:35:623 [INFO] c.z.u.ArrayUtil.main()[37] --- true```
  * 日志输出(控制台输出) [```com.zhangheng.log.Log```和```com.zhangheng.log.printLog.Log```]
    * Log.info(msg);
    * Log.error(msg);
    * Log.warn(msg);
    * Log.debug(msg);
  * 生成日志文件（控制台输出和日志文件生成） [```com.zhangheng.log.printLog.Log```]
      * Log.Info(msg);
      * Log.Error(msg);
      * Log.Warn(msg);
      * Log.Debug(msg);
### 系统```[com.zhangheng.system]```
   * windows关闭服务进程```com.zhangheng.system.KillServer```
### 文件 ```[com.zhangheng.file]```
   * 文件类型判断```com.zhangheng.file.FiletypeUtil```
   * 文本文件操作（创建，读写）```com.zhangheng.file.TxtOperation```
# 一、工具
## 1.时间工具（TimeUtil）
* 类路径：```com.zhangheng.util.TimeUtil```
### 常量及格式
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
### 具体方法
####  TimeUtil.toTime(Date date,String dateFormat);
> * 将日期转换为指定格式时间
> * 例 TimeUtil.toTime(new Date(),TimeUtil.cnDateFormat) 获取当前[yyyy年MM月dd日 HH:mm:ss]时间
> * @param date 日期
> * @param dateFormat 指定的时间格式 类似[yyyy年MM月dd日 HH:mm:ss]
> * @return String

####  TimeUtil.Date toDate(String time,String dateFormat);
> * 将指定格式的时间转换为日期
> * @param time 指定格式的时间（2022年03月04日 12:00:00）
> * @param dateFormat 指定的时间格式 （TimeUtil.cnDateFormat[yyyy年MM月dd日 HH:mm:ss]）
> * @return Date

####  TimeUtil.getTime(int Type);
> * 根据类型获取当前时间信息
> * 例 TimeUtil.getTime(TimeUtil.Week) 获取当前星期
> * @param Type 不同类型的时间（年、月、日...）
> * @return String

####  TimeUtil.timeDifference(String time1,String dateFormat1,String time2,String dateFormat2,int Type);
> * 计算两个时间之间的差值（不同格式的时间）
> * @param time1 时间1
> * @param dateFormat1 时间2的格式
> * @param time2 时间2
> * @param dateFormat2 时间2的格式
> * @param Type 差值类型（月，日，时，分，秒，毫秒）
> * @return int （错误返回-1）

####  TimeUtil.calDifference(Date fromDate, Date toDate, int Type);
> * 计算两个日期之间的差值
> * @param fromDate 开始日期
> * @param toDate 截止日期
> * @param Type 差值类型（月，日，时，分，秒，毫秒）
> * @return int （错误返回-1）

####  TimeUtil.fewDaysAgo(String time,String dateFormat,int i);
> * 计算某个时间前半个月前某一天的时间 （指定格式）
> * @param time 指定格式的时间
> * * @param dateFormat 指定的时间格式
> * @param i [0,15] 前几天
> * @return String

## 2.随机工具（RandomrUtil）
* 类路径：```com.zhangheng.util.RandomrUtil```
### 常量及格式
```java
    /**
     * 数字类型
     */
    public static final String Number="0";
    /**
     * 小写英文类型
     */
    public static final String Lowercase="1";
    /**
     * 大写英文类型
     */
    public static final String Capital="2";
    /**
     * 英文符号类型
     */
    public static final String Symbol="3";
    /**
     * 英文符号
     */
    private static final String SYMBOLS_EN_STR="~!@#$%^&_*-+=()[]{}<>./?";
```
### 具体方法
####  RandomrUtil.createPassWord(int length,String type);
> * 生成大小写字母、数字、特殊符号组合的指定长度的密码
> * 例 RandomrUtil.createPassWord(12, "012") 生成由数字、小写和大写字母组成的12位随机码
> * @param length 生成的字符长度
> * @param type 
> *             [0:纯数字(0~9)]
> *             [1:小写字母(a~z)]
> *             [2:大写字母(A~Z)]
> *             [3:英文符号]
> *             [例："123"(小写字母+大写字母+英文符号)]
> * @return String

####  RandomrUtil.createRandom(int min,int max);
> * 生成指定范围的随机数
> * 例 createRandom(3, 33)  生成3(包含)到33(包含)的随机数 [3,33]
> * @param min 最小值（包含）
> * @param max 最大值（包含）
> * @return int

## 3.数学工具（MathUtil）
* 类路径：```com.zhangheng.util.MathUtil```
### 具体方法
####  MathUtil.twoDecimalPlaces(double num);
> * 五舍六入 保留两位小数
> * 例 MathUtil.twoDecimalPlaces(3.1461186) //3.15
> * @param num 数字
> * @return double

####  MathUtil.numerFormat(double num);
> * 根据数字长度返回不同格式的数字
> * 当数字大于10000时 以 100,000 展示，小于等于10000时 保留两位小数显示
> * 例 MathUtil.twoDecimalPlaces(100000) //100,000
> * @param num 数字
> * @return String

## 4.格式工具（FormatUtil）
* 类路径：```com.zhangheng.util.FormatUtil```
### 具体方法
####  FormatUtil.isLetterDigit(String str);
> * 字符串是否包含大小写字母及数字长度在6-18位(不含符号)
> * 例 FormatUtil.isLetterDigit("1ads5aAffsdw") //true
> * @param str 判断的字符串
> * @return boolean

####  FormatUtil.isMobilePhone(String str);
> * 手机号格式验证（11位手机号）
> * 例 FormatUtil.isMobilePhone("13733430842") //true
> * @param str 判断的手机号
> * @return boolean

## 5.文件扫描工具（FolderFileScannerUtil）
* 类路径：```com.zhangheng.util.FolderFileScannerUtil```
### 具体方法
####  FolderFileScannerUtil.scanFilesWithRecursion(String folderPath);
* 递归扫描指定文件夹下面的指定文件
* 例 FormatUtil.isMobilePhone("./imag/") 绝对或相对路径
* @param folderPath 需要进行文件扫描的文件夹路径
* @return ```ArrayList<Object>``` 文件的绝对路径集合

####  FolderFileScannerUtil.scanFilesWithNoRecursion(String folderPath);
* 非递归方式扫描指定文件夹下面的所有文件
* 例 FormatUtil.isMobilePhone("./imag") 绝对或相对路径
* @param folderPath 需要进行文件扫描的文件夹路径
* @return ```ArrayList<Object>``` 文件的绝对路径集合

## 6.文件类型工具（FiletypeUtil）
* 类路径：```com.zhangheng.file.FiletypeUtil```
### 具体方法
####  FiletypeUtil.getFileType(String filename,String[][] MIME_MapTable,String defaultType);
> * 判断文件类型
> * 例 FiletypeUtil.getFileType("img.png",FiletypeUtil.MIME_MapTable,"unknown")) 绝对或相对路径 //image
> * @param filename 文件全称（含后缀名）
> * @param MIME_MapTable 类型映射二维数组
> * @param defaultType 当没右匹配值时，返回默认类型
> * @return


## 7.加密工具（EncryptUtil）
* 类路径：```com.zhangheng.util.EncryptUtil```
### 常量格式
```java
    /**
         * （加密类型）SHA
         */
        public final static String SHA = "SHA";
        /**
         * （加密类型）SHA256
         */
        public final static String SHA256 = "SHA-256";
        /**
         * （加密类型）MD5
         */
        public final static String MD5 = "MD5";
```
### 具体方法
####  EncryptUtil.encrypt(String source,String algorithm);
> * 加密
> * 例 EncryptUtil.encrypt("123",EncryptUtil.MD5) 对123进行md5加密
> * @param source 加密对象
> * @param algorithm 加密算法 （从上面常量选）
> * @return String
####  EncryptUtil.getSHA(String source);
> * SHA加密 并转换字符串
> * 例 EncryptUtil.getSHA("123")
> * @param source 加密对象
> * @return String
####  EncryptUtil.getSHA256(String source);
> * SHA256加密 并转换字符串
> * 例 EncryptUtil.getSHA1("123")
> * @param source 加密对象
> * @return String
####  EncryptUtil.enBase64(byte[] key);
> * BASE64加密
> * @param key 加密对象
> * @return String
####  EncryptUtil.deBase64(String key);
> * BASE64解密
> * @param key 解密对象
> * @return byte[]
####  EncryptUtil.getMd5(String str);
> * MD5 加密（UTF-8编码）
> * 例 EncryptUtil.getMd5("123")
> * @param str 加密对象
> * @return String
####  EncryptUtil.getSignature(String data, String key, String type,String charset);
> * 生成签名数据
> * 例 EncryptUtil.getSignature("张三","abc456",EncryptUtil.SAH256,"UTF-8")
> * @param data 待加密的数据
> * @param key  加密使用的key
> * @param type 加密类型
> * @param charset 字符编码
> * @return String
####  EncryptUtil.getMyMd5(String encodestr,String key);
> * 改造md5加密方法
> * 例 EncryptUtil.getMyMd5("123","adsfgwhy12345678");
> * @param encodestr 加密的字符串
> * @param key 加密的key（长度16）
> * @return String

## 8.数组工具（ArrayUtil）
* 类路径：```com.zhangheng.util.ArrayUtil```
### 具体方法
####  ArrayUtil.exist(Object[] arr,Object obj);
> * 检查数组里是否存在对象
> * @param arr 目标数组
> * @param obj 检查对象
> * @return boolean
