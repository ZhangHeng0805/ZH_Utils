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
     * 字符串是否包含大小写字母及数字长度在6-18位(不含符号)
     * @param str 校验的字符串
     * @return 是否
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
     * @param  str 校验的字符串
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
     * @param ip 校验的字符串
     * @return 正确返回true
     */
    public static boolean isIP(String ip){
        String ipReg="^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9]).(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d).(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d).(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        Pattern compile = Pattern.compile(ipReg);
        return compile.matcher(ip).matches();
    }

    /**
     * 判断端口号格式是否正确
     * @param port 校验的字符串
     * @return 是否
     */
    public static boolean isPort(String port){
        String Reg="([0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-4]\\d{4}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])";
        Pattern compile = Pattern.compile(Reg);
        return compile.matcher(port).matches();
    }
    /**
     * 判断邮箱格式是否正确
     * @param email 校验的字符串
     * @return 是否
     */
    public static boolean isEmail(String email){
        String Reg="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        Pattern compile = Pattern.compile(Reg);
        return compile.matcher(email).matches();
    }
    /**
     * 判断身份证号格式是否正确
     * @param idCard 校验的字符串
     * @return 是否
     */
    public static boolean isIDCard(String idCard){
        String Reg="(^\\d{8}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}$)|(^\\d{6}(18|19|20)\\d{2}(0[1-9]|10|11|12)([0-2]\\d|30|31)\\d{3}(\\d|X|x)$)";
        Pattern compile = Pattern.compile(Reg);
        return compile.matcher(idCard).matches();
    }

    /**
     * 判断网址格式是否正确
     * @param url 校验的字符串
     * @return 是否
     */
    public static boolean isWebUrl(String url){
        String Reg="^(((ht|f)tps?):\\/\\/)?[\\w-]+(\\.[\\w-]+)+([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?$";
        Pattern compile = Pattern.compile(Reg);
        return compile.matcher(url).matches();
    }

    /**
     * 判断字符串是否为数字格式（包含正负、小数）
     * @param number 校验的字符串
     * @return 是否
     */
    public static boolean isNumber(String number){
        //判断字符串是否为空
        if (number!=null&&number.length()>0) {
            //判断字付串开头是否以（-、+、数字）开头
            if (number.startsWith("-") || number.startsWith("+") || Character.isDigit(number.charAt(0))) {
                //+出现次数小于2
                if (MathUtil.strCountNum(number,'+')<2) {
                    //-出现次数小于2
                    if (MathUtil.strCountNum(number, '-') < 2) {
                        //.出现次数小于2
                        if (MathUtil.strCountNum(number, '.') < 2) {
                            //判断是否有小数点
                            if (number.indexOf(".")>0){
                                //判断小数点前一个字符是否为数字
                                if (!Character.isDigit(number.charAt(number.indexOf(".")-1))){
                                    return false;
                                }
                            }
                            //遍历字符串
                            for (int i=1;i<number.length();i++) {
                                //若字符不为数字
                                if (!Character.isDigit(number.charAt(i))){
                                    //若字符不为小数点
                                    if (number.charAt(i)!='.'){
                                        return false;
                                    }
                                }
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
