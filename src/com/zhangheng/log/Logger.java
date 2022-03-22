package com.zhangheng.log;

import com.zhangheng.util.TimeUtil;

import java.util.Date;

/**
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-21 15:55
 */
public class Logger {
    private String time;
    private Integer code;
    private String type;
    private String classPath;
    private String method;
    private int lineNumber;
    private Object msg;

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
     * @return
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

    @Override
    public String toString() {
        String s=null;
        String classPathName = hadnleClassName(classPath);
        if (time==null) {
           time= TimeUtil.toTime(new Date(),TimeUtil.enDateFormat_Detailed);
        }
        s =time+" ["+type+"] "+classPathName+"."+method+"()["+lineNumber+"] --- "+msg.toString();
        return s;
    }
}
