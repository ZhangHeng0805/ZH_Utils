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
        String errorMsg = stringWriter.toString();
        return errorMsg;
    }
}
