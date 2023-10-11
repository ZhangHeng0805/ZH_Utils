package com.zhangheng.log;

import com.zhangheng.util.TimeUtil;

import java.util.Date;

/**
 * 日志对象
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-21 15:55
 */
public class Logger {

    /**
     * \033[30-39m 设置控制台文字颜色
     * \033[40-49m 设置控制台文字背景颜色
     * \033[90-99m 设置控制台文字高亮颜色
     * \033[m 清除控制台所有颜色，恢复默认
     */
    /**
     * 默认颜色文字
     *
     * \033[30-39m 设置控制台文字颜色
     * \033[40-49m 设置控制台文字背景颜色
     * \033[90-99m 设置控制台文字高亮颜色
     * \033[m 清除控制台所有颜色，恢复默认
     */
    public final static String Default_Color="\033[m";
    /**
     * 绿色文字
     *
     * \033[30-39m 设置控制台文字颜色
     * \033[40-49m 设置控制台文字背景颜色
     * \033[90-99m 设置控制台文字高亮颜色
     * \033[m 清除控制台所有颜色，恢复默认
     */
    public final static String Info_Color ="\033[32m";
    /**
     * 红色
     *
     * \033[30-39m 设置控制台文字颜色
     * \033[40-49m 设置控制台文字背景颜色
     * \033[90-99m 设置控制台文字高亮颜色
     * \033[m 清除控制台所有颜色，恢复默认
     */
    public final static String Error_Color ="\033[31m";
    /**
     * 蓝色文字
     *
     * \033[30-39m 设置控制台文字颜色
     * \033[40-49m 设置控制台文字背景颜色
     * \033[90-99m 设置控制台文字高亮颜色
     * \033[m 清除控制台所有颜色，恢复默认
     */
    public final static String Debug_Color ="\033[34m";
    /**
     * 黄色文字
     *
     * \033[30-39m 设置控制台文字颜色
     * \033[40-49m 设置控制台文字背景颜色
     * \033[90-99m 设置控制台文字高亮颜色
     * \033[m 清除控制台所有颜色，恢复默认
     */
    public final static String Warn_Color ="\033[33m";


    private String time;//时间
    private Integer code;//状态码
    private String type;//类型
    private String classPath;//调用的类路径
    private String method;//调用的方法名
    private int lineNumber;//调用的文件行数
    private Object msg;//消息对象

    /**
     *
     * @param time 时间
     * @param code 状态码
     * @param type 类型
     * @param classPath 调用的类路径
     * @param method 调用的方法名
     * @param lineNumber 调用的文件行数
     * @param msg 消息对象
     */
    public Logger(String time, Integer code, String type, String classPath, String method, int lineNumber, Object msg) {
        this.time = time;
        this.code = code;
        this.type = type;
        this.classPath = classPath;
        this.method = method;
        this.lineNumber = lineNumber;
        this.msg = msg;
    }

    public Logger() {
    }

    public String getTime() {
        return time;
    }


    public Integer getCode() {
        return code;
    }

    protected void setCode(Integer code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    protected void setType(String type) {
        this.type = type;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * 处理类路径过长
     * @param classPath
     * @return 处理过的类路径
     */
    private String hadnleClassName(String classPath){
        StringBuffer classPathName = new StringBuffer();
        String name = classPath;
        String[] split = name.split("\\.");
        if (split.length>3){
            for (int i=0;i<split.length;i++){
                if (i!=split.length-1){
                    classPathName.append(split[i].substring(0,1)+".");
                }else {
                    classPathName.append(split[i]);
                }
            }
        }else {
            classPathName.append(name);
        }
        return classPathName.toString();
    }

    /**
     * 字符串格式化对象
     * @return time[type]classPathName.method()[lineNumber] --- msg
     */
    @Override
    public String toString() {
        String s=null;
        String classPathName = hadnleClassName(classPath);

        time= TimeUtil.toTime(new Date(),TimeUtil.EnDateFormat_Detailed);
//        time= "123";

        if (msg==null){
            msg="null";
        }
        s =time+" ["+type+"] "+classPathName+"."+method+"()["+lineNumber+"] --- "+msg.toString();
        return s;
    }
}
