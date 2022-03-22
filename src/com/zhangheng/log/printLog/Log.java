package com.zhangheng.log.printLog;

import com.zhangheng.log.Logger;

/**
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-21 16:46
 */
public class Log extends com.zhangheng.log.Log {

    private static Logger log=new Logger();

    private static Logger init(Object object) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String className = stackTrace[stackTrace.length-1].getClassName();
        String methodName = stackTrace[stackTrace.length-1].getMethodName();
        int lineNumber = stackTrace[stackTrace.length-1].getLineNumber();
        log.setClassPath(className);
        log.setMethod(methodName);
        log.setLineNumber(lineNumber);
        log.setMsg(object);
        return log;
    }
    public static String Info(Object msg){
        Logger init = init(msg);
        String info = Info(init);
        Print.print(info);
        return info;
    }
    public static String Error(Object msg){
        Logger init = init(msg);
        String error = Error(init);
        Print.print(error);
        return error;
    }
    public static String Warn(Object msg){
        Logger init = init(msg);
        String warn = Warn(init);
        Print.print(warn);
        return warn;
    }
    public static String Debug(Object msg){
        Logger init = init(msg);
        String debug = Debug(init);
        Print.print(debug);
        return debug;
    }

}
