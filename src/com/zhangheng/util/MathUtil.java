package com.zhangheng.util;

import java.text.DecimalFormat;

/**
 * 数学帮助类
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
        DecimalFormat df   = new DecimalFormat("######0.00");
        String format = df.format(num);
        Double aDouble = Double.valueOf(format);
        return aDouble;
    }
}
