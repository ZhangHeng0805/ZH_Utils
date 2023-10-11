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
    private static StringBuilder bufferMsg=new StringBuilder();


    private static Logger init(Object object) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        className = stackTrace[stackTrace.length - 1].getClassName();
        methodName = stackTrace[stackTrace.length - 1].getMethodName();
        lineNumber = stackTrace[stackTrace.length - 1].getLineNumber();
        log.setClassPath(className);
        log.setMethod(methodName);
        log.setLineNumber(lineNumber);
        log.setMsg(object);
        clearMsg();
        return log;
    }
    private static void clearMsg(){
        bufferMsg.setLength(0);
        bufferMsg.trimToSize();
    }

    /**
     * 控制台和日志文件输出Info类型日志
     * @param msg 信息
     * @return 格式化日志信息
     */
    public static String Info(Object msg){
        Logger init = init(msg);
        String info = Info(init);
        bufferMsg.append(info);
        Print.print(bufferMsg);
        return info;
    }

    /**
     * 控制台和日志文件输出Error类型日志
     * @param msg 信息
     * @return 格式化日志信息
     */
    public static String Error(Object msg){
        Logger init = init(msg);
        String error = Error(init);
        StringBuilder buffer=new StringBuilder(error);
        Print.print(buffer);
        return error;
    }

    /**
     * 控制台和日志文件输出Warn类型日志
     * @param msg 信息
     * @return 格式化日志信息
     */
    public static String Warn(Object msg){
        Logger init = init(msg);
        String warn = Warn(init);
        StringBuilder buffer=new StringBuilder(warn);
        Print.print(buffer);
        return warn;
    }

    /**
     * 控制台和日志文件输出Debug类型日志
     * @param msg 信息
     * @return 格式化日志信息
     */
    public static String Debug(Object msg){
        Logger init = init(msg);
        String debug = Debug(init);
        StringBuilder buffer=new StringBuilder(debug);
        Print.print(buffer);
        return debug;
    }

}
