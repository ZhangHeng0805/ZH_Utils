package com.zhangheng.bean;

import com.zhangheng.util.TimeUtil;

import java.util.Date;

/**
 * 消息实体类
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-04 13:25
 */
public class Message {
    private String time;//时间戳
    private int code;//状态码(200:成功; 100:提示; 404:警告; 500:错误）
    private String title;//标题
    private String message;//内容
    private Object obj;//对象
    public Message() {
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "Message{" +
                "time='" + time + '\'' +
                ", code=" + code +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", obj=" + obj +
                '}';
    }

    /**
     * 控制台打印日志
     * @param msg
     */
    public static void printLog(Object msg){
        String time = TimeUtil.toTime(new Date(),TimeUtil.enDateFormat_Detailed);
        System.out.println(time+" === "+msg.toString());
    }
}
