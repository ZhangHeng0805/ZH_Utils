package com.zhangheng.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式帮助类
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
            p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
            m = p.matcher(str);
            b = m.matches();
        }
        return b;
    }
}
