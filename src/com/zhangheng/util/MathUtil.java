package com.zhangheng.util;


import java.text.DecimalFormat;

/**
 * 数学工具类
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-05 16:11
 */
public class MathUtil {
    /**
     * 五舍六入保留两位小数
     * @param num
     * @return
     */
    public static double twoDecimalPlaces(double num){
        DecimalFormat df   = new DecimalFormat("#########.00");
        String format = df.format(num);
        Double aDouble = Double.valueOf(format);
        return aDouble;
    }

    /**
     * 数字格式化
     * 当数字大于10000时 以 100,000 展示
     * 小于等于10000时 保留两位小数显示
     * @param num
     * @return
     */
    public static String numerFormat(double num){
        String format;
        if (num>10000) {
            DecimalFormat df = new DecimalFormat(",###");
             format= df.format(num);
        }else {
            double v = twoDecimalPlaces(num);
            format=String.valueOf(v);

        }
        return format;
    }

    /**
     * 统计字符串中的字符个数
     * @param str 字符串
     * @param s 统计的字符
     * @return
     */
    public static int strCountNum(String str,char s){
        int count=0;
        for (int i=0;i<str.length();i++){
         if (str.charAt(i)==s){
             count++;
         }
        }
        return count;
    }

}
