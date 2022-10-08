package com.zhangheng.log.printLog;

import com.zhangheng.log.Logger;

/**
 * 日志输出（控制台输出同时写入日志文件中）
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-21 16:46
 */
public class Log extends com.zhangheng.log.Log {

    private static Logger log=new Logger();
    private static String className;
    private static String methodName;
    private static Integer lineNumber;


    private static Logger init(Object object) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        className = stackTrace[stackTrace.length - 1].getClassName();
        methodName = stackTrace[stackTrace.length - 1].getMethodName();
        lineNumber = stackTrace[stackTrace.length - 1].getLineNumber();
        log.setClassPath(className);
        log.setMethod(methodName);
        log.setLineNumber(lineNumber);
        log.setMsg(object);
        return log;
    }
    public static String Info(Object msg){
        Logger init = init(msg);
        String info = Info(init);
        StringBuffer buffer=new StringBuffer(info);
        Print.print(buffer);
        return info;
    }
    public static String Error(Object msg){
        Logger init = init(msg);
        String error = Error(init);
        StringBuffer buffer=new StringBuffer(error);
        Print.print(buffer);
        return error;
    }
    public static String Warn(Object msg){
        Logger init = init(msg);
        String warn = Warn(init);
        StringBuffer buffer=new StringBuffer(warn);
        Print.print(buffer);
        return warn;
    }
    public static String Debug(Object msg){
        Logger init = init(msg);
        String debug = Debug(init);
        StringBuffer buffer=new StringBuffer(debug);
        Print.print(buffer);
        return debug;
    }

}
