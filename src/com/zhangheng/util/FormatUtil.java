package com.zhangheng.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式工具类
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-04 17:00
 */
public class FormatUtil {

    /**
     *
     * 字符串是否包含大小写字母及数字长度在6-18位(不含符号)
     * @param str
     * @return
     */
    public static boolean isLetterDigit(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]{6,18}$";
        boolean isRight = isDigit && isLetter && str.matches(regex);
        return isRight;
    }
    /**
     * 手机号验证
     * @param  str
     * @return 验证通过返回true
     */
    public static boolean isMobilePhone(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        if (str!=null) {
            //严谨
            String phoneReg1="^(?:(?:\\+|00)86)?1(?:(?:3[\\d])|(?:4[5-79])|(?:5[0-35-9])|(?:6[5-7])|(?:7[0-8])|(?:8[\\d])|(?:9[189]))\\d{8}$";
            String phoneReg2="^[1][3,4,5,8][0-9]{9}$";
            p = Pattern.compile(phoneReg1); // 验证手机号
            m = p.matcher(str);
            b = m.matches();
        }
        return b;
    }

    /**
     * 判断ip格式是否正确
     * @param ip
     * @return 正确返回true
     */
    public static boolean isIP(String ip){
        String ipReg="^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9]).(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d).(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d).(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        Pattern compile = Pattern.compile(ipReg);
        return compile.matcher(ip).matches();
    }

    /**
     * 判断端口号格式是否正确
     * @param port
     * @return
     */
    public static boolean isPort(String port){
        String Reg="([0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-4]\\d{4}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])";
        Pattern compile = Pattern.compile(Reg);
        return compile.matcher(port).matches();
    }

}
