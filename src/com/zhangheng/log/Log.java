package com.zhangheng.log;

/**
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-21 16:00
 */
public class Log extends Logger{

    private static Logger log = new Logger();

    public static Logger info(Object msg){
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
        log.setCode(200);
        log.setType("INFO");
        log.setClassPath(className);
        log.setMethod(methodName);
        log.setLineNumber(lineNumber);
        log.setMsg(msg);
        System.out.println(log.toString());
        return log;
    }
    public static Logger error(Object msg){
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
        log.setCode(500);
        log.setType("ERROR");
        log.setClassPath(className);
        log.setMethod(methodName);
        log.setLineNumber(lineNumber);
        log.setMsg(msg);
        System.err.println(log.toString());
        return log;
    }
    public static Logger warn(Object msg){
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
        log.setCode(404);
        log.setType("WARN");
        log.setClassPath(className);
        log.setMethod(methodName);
        log.setLineNumber(lineNumber);
        log.setMsg(msg);
        System.out.println(log.toString());
        return log;
    }
    public static Logger debug(Object msg){
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
        log.setCode(100);
        log.setType("DEBUG");
        log.setClassPath(className);
        log.setMethod(methodName);
        log.setLineNumber(lineNumber);
        log.setMsg(msg);
        System.out.println(log.toString());
        return log;
    }
    protected static String Info(Logger log){
        log.setCode(200);
        log.setType("INFO");
        System.out.println(log.toString());
        return log.toString();
    }
    protected static String Error(Logger log){
        log.setCode(500);
        log.setType("ERROR");
        System.err.println(log.toString());
        return log.toString();
    }
    protected static String Warn(Logger log){
        log.setCode(200);
        log.setType("WARN");
        System.out.println(log.toString());
        return log.toString();
    }
    protected static String Debug(Logger log){
        log.setCode(100);
        log.setType("DEBUG");
        System.out.println(log.toString());
        return log.toString();
    }

}
