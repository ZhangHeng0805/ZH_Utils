package com.zhangheng.util;

import cn.hutool.core.text.StrBuilder;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author: ZhangHeng
 * @email: zhangheng_0805@163.com
 * @date: 2024-03-28 14:25
 * @version: 1.0
 * @description:
 */
public class ThrowableUtil {
    /**
     * 将异常错误解析为字符串
     * @param e
     * @return
     */
    public static String toString(Throwable e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     * 将异常错误解析为字符串
     * @param e
     * @param limit 指定长度
     * @return
     */
    public static String toString(Throwable e, int limit) {
        String string = toString(e);
        if (limit > string.length() || limit < 1) {
            return string;
        }
        return string.substring(0, limit);
    }

    /**
     * 获取异常链中最尾端的异常，即异常最早发生的异常对象。
     * 此方法通过调用Throwable.getCause() 直到没有cause为止，如果异常本身没有cause，返回异常本身
     * 传入null返回也为null
     *
     * @param throwable
     * @return
     */
    public static Throwable getRootCause(Throwable throwable) {
        Throwable cause = throwable.getCause();
        if (cause != null) {
            return getRootCause(cause);
        }
        return throwable;
    }

    /**
     * 获取异常链中全部的异常信息
     * @param throwable
     * @return
     */
    public static String getAllCauseMessage(Throwable throwable) {
        StrBuilder sb = new StrBuilder(throwable.toString());
        Throwable cause = throwable.getCause();
        while (cause != null) {
            sb.append(" >> ").append(cause.toString());
            cause = cause.getCause();
        }
        return sb.toString();
    }
}
