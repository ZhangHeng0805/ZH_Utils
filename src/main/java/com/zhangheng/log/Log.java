package com.zhangheng.log;

import java.io.Console;

/**
 * 日志输出（控制台输出）
 *
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-21 16:00
 */
public class Log extends Logger {

    private static Logger log = new Logger();
    private static String className;
    private static String methodName;
    private static Integer lineNumber;
    private static Boolean isConsole;
    static {
        isConsole=isConsoleOutputConnected();
    }
    private static void init() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        className = stackTrace[stackTrace.length - 1].getClassName();
        methodName = stackTrace[stackTrace.length - 1].getMethodName();
        lineNumber = stackTrace[stackTrace.length - 1].getLineNumber();
    }

    /**
     * 控制台输出info类型的日志
     * @param msg 信息
     * @return 日志对象
     */
    public static Logger info(Object msg) {
        init();
        log.setCode(200);
        log.setType("INFO");
        log.setClassPath(className);
        log.setMethod(methodName);
        log.setLineNumber(lineNumber);
        log.setMsg(msg);
        print(log.toString(),Info_Color);
        return log;
    }

    /**
     * 控制台输出error类型的日志
     * @param msg 信息
     * @return 日志对象
     */
    public static Logger error(Object msg) {
        init();
        log.setCode(500);
        log.setType("ERROR");
        log.setClassPath(className);
        log.setMethod(methodName);
        log.setLineNumber(lineNumber);
        log.setMsg(msg);
        print(log.toString(),Error_Color);
        return log;
    }

    /**
     * 控制台输出warn类型的日志
     * @param msg 信息
     * @return 日志对象
     */
    public static Logger warn(Object msg) {
        init();
        log.setCode(404);
        log.setType("WARN");
        log.setClassPath(className);
        log.setMethod(methodName);
        log.setLineNumber(lineNumber);
        log.setMsg(msg);
        print(log.toString(),Warn_Color);
        return log;
    }

    /**
     * 控制台输出debug类型的日志
     * @param msg 信息
     * @return 日志对象
     */
    public static Logger debug(Object msg) {
        init();
        log.setCode(100);
        log.setType("DEBUG");
        log.setClassPath(className);
        log.setMethod(methodName);
        log.setLineNumber(lineNumber);
        log.setMsg(msg);
        print(log.toString(),Debug_Color);
        return log;
    }

    /**
     * 控制台输出info类型日志
     * @param log 日志对象
     * @return 格式化日志
     */
    protected static String Info(Logger log) {
        log.setCode(200);
        log.setType("INFO");
        print(log.toString(),Info_Color);
        return log.toString();
    }

    /**
     * 控制台输出error类型日志
     * @param log 日志对象
     * @return 格式化日志
     */
    protected static String Error(Logger log) {
        log.setCode(500);
        log.setType("ERROR");
        print(log.toString(),Error_Color);
        return log.toString();
    }

    /**
     * 控制台输出warn类型日志
     * @param log 日志对象
     * @return 格式化日志
     */
    protected static String Warn(Logger log) {
        log.setCode(200);
        log.setType("WARN");
        print(log.toString(),Warn_Color);
        return log.toString();
    }

    /**
     * 控制台输出debug类型日志
     * @param log 日志对象
     * @return 格式化日志
     */
    protected static String Debug(Logger log) {
        log.setCode(100);
        log.setType("DEBUG");
        print(log.toString(),Default_Color);
        return log.toString();
    }

    /**
     * 控制台输出
     * @param message
     * @param type_color
     */
    private static void print(String message,String type_color){
        if (isConsole)
        //普通输出
        System.out.println(message);
        else
        //彩色输出（程序控制台输出）
        System.out.println(type_color+message+Default_Color);
    }
    // 判断方法：检查标准输出是否连接到控制台
    private static boolean isConsoleOutputConnected() {
        Console console = System.console();
        return console != null;
    }
}
