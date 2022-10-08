package com.zhangheng.log;

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

    private static void init() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        className = stackTrace[stackTrace.length - 1].getClassName();
        methodName = stackTrace[stackTrace.length - 1].getMethodName();
        lineNumber = stackTrace[stackTrace.length - 1].getLineNumber();
    }

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

    protected static String Info(Logger log) {
        log.setCode(200);
        log.setType("INFO");
        print(log.toString(),Info_Color);
        return log.toString();
    }

    protected static String Error(Logger log) {
        log.setCode(500);
        log.setType("ERROR");
        print(log.toString(),Error_Color);
        return log.toString();
    }

    protected static String Warn(Logger log) {
        log.setCode(200);
        log.setType("WARN");
        print(log.toString(),Warn_Color);
        return log.toString();
    }

    protected static String Debug(Logger log) {
        log.setCode(100);
        log.setType("DEBUG");
        print(log.toString(),Default_Color);
        return log.toString();
    }

    private static void print(String message,String type_color){
        System.out.println(type_color+message+Default_Color);
    }

}
