package com.zhangheng.captcha.generator;

import cn.hutool.core.math.Calculator;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.zhangheng.util.RandomUtil;

/**
 * @author: ZhangHeng
 * @email: zhangheng_0805@163.com
 * @date: 2023-07-28 20:02
 * @version: 1.0
 * @description: 数字计算验证码生成器
 */
public class MathGenerator implements CodeGenerator{
    private static final long serialVersionUID = 452368671774091076L;

    private static final String operators = "+-*";

    /** 参与计算数字最大长度 */
    private final int numberLength;

    /**
     * 构造
     */
    public MathGenerator() {
        this(2);
    }

    /**
     * 构造
     *
     * @param numberLength 参与计算最大数字位数
     */
    public MathGenerator(int numberLength) {
        this.numberLength = numberLength;
    }

    @Override
    public String generate() {
        char c = RandomUtil.randomChar(operators);
        final int limit = getLimit();
        String number1 = Integer.toString(RandomUtil.randomInt(-(limit-1),limit));
        String number2 = Integer.toString(RandomUtil.randomInt(limit));
        number1 = StrUtil.padAfter(number1, this.numberLength, CharUtil.SPACE);
        number2 = StrUtil.padAfter(number2, this.numberLength, CharUtil.SPACE);

        return StrUtil.builder()//
                .append(number1)//
                .append(c)//
                .append(number2)//
                .append('=').toString();
    }


    @Override
    public boolean verify(String code, String userInputCode) {
        int result;
        try {
            result = Integer.parseInt(userInputCode);
        } catch (NumberFormatException e) {
            // 用户输入非数字
            return false;
        }

        final int calculateResult = (int) Calculator.conversion(code);
        return result == calculateResult;
    }

    /**
     * 获取验证码长度
     *
     * @return 验证码长度
     */
    public int getLength() {
        return this.numberLength * 2 + 2;
    }

    /**
     * 根据长度获取参与计算数字最大值
     *
     * @return 最大值
     */
    private int getLimit() {
        return Integer.parseInt("1" + StrUtil.repeat('0', this.numberLength));
    }
}
