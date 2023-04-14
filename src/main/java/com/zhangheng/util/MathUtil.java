package com.zhangheng.util;


import cn.hutool.core.util.NumberUtil;
import com.zhangheng.bean.MyException;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数学工具类
 *
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-05 16:11
 */
public class MathUtil extends NumberUtil {
    /**
     * 五舍六入保留两位小数
     *
     * @param num 数据
     * @return 结果
     */
    public static double twoDecimalPlaces(double num) {
        DecimalFormat df = new DecimalFormat("#########.00");
        String format = df.format(num);
        Double aDouble = Double.valueOf(format);
        return aDouble;
    }

    /**
     * 数字格式化
     * 当数字大于10000时 以 100,000 展示
     * 小于等于10000时 保留两位小数显示
     *
     * @param num 数据
     * @return 结果
     */
    public static String numerFormat(double num) {
        String format;
        if (num > 10000) {
            DecimalFormat df = new DecimalFormat(",###");
            format = df.format(num);
        } else {
            double v = twoDecimalPlaces(num);
            format = String.valueOf(v);

        }
        return format;
    }

    /**
     * 统计字符串中的字符个数
     *
     * @param str 字符串
     * @param s   统计的字符
     * @return 结果
     */
    public static int strCountNum(String str, char s) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == s) {
                count++;
            }
        }
        return count;
    }

    /**
     * 简单的计算
     * 只能进行单一的加减乘除运算
     * 不能进行多次混合运算
     * @param expression 计算表达式
     *                   例[1+1=或者2*3]
     * @return
     * @throws MyException
     */
    public static String simpleOperation(String expression) throws MyException {
        int i=strCountNum(expression,'+')
                +strCountNum(expression,'-')
                +strCountNum(expression,'*')
                +strCountNum(expression,'/');
        if (i!=1)
            throw new MyException("计算类型Operation", "expression中的计算类型只能为'+','-','*','/'中的一个");
        if (expression.endsWith("="))
            expression = expression.replace("=", "");
        BigDecimal bigDecimal = null;
        if (expression.indexOf("+") > 0) {
            String[] split = expression.split("\\+");
            bigDecimal = MathUtil.add(split[0], split[1]);
        } else if (expression.indexOf("-") > 0) {
            String[] split = expression.split("-");
            bigDecimal = MathUtil.sub(split[0], split[1]);
        } else if (expression.indexOf("*") > 0) {
            String[] split = expression.split("\\*");
            bigDecimal = MathUtil.mul(split[0], split[1]);
        } else if (expression.indexOf("/") > 0) {
            String[] split = expression.split("/");
            bigDecimal = MathUtil.div(split[0], split[1]);
        } else {
            throw new MyException("计算类型Operation", "Operation类型应为+、-、*、/");
        }
        return toStr(bigDecimal);
    }

    /**
     * 加减乘除混合运算
     * @param expression 计算表达式
     *                   例 (((12+3)-6)*2)/5
     * @return
     * @throws MyException
     */
    public static String operation(String expression) throws MyException {
        if (expression.endsWith("="))
            expression = expression.replace("=", "");
        return toStr(calculate(expression));
    }
}
