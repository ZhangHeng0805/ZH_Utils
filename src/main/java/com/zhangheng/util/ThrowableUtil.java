package com.zhangheng.util;

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
        if (limit > string.length()) {
            return string;
        }
        return string.substring(0, limit);
    }

    /**
     * 获取异常的根本原因
     * @param throwable
     * @return
     */
    public static Throwable getCase(Throwable throwable) {
        Throwable cause = throwable.getCause();
        if (cause != null) {
            return getCase(cause);
        }
        return throwable;
    }
}
