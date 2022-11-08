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
    private Object obj;//附加对象

    /**
     *
     * @param time 时间
     * @param code 状态码(200:成功; 100:提示; 404:警告; 500:错误）
     * @param title 标题
     * @param message 内容
     * @param obj 附加对象
     */
    public Message(String time, int code, String title, String message, Object obj) {
        this.time = time;
        this.code = code;
        this.title = title;
        this.message = message;
        this.obj = obj;
    }

    public Message() {
    }

    /**
     * 获取信息时间
     * @return 时间
     */
    public String getTime() {
        return time;
    }

    /**
     * 设置信息时间
     * @param time 时间
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 获取信息状态码
     * (200:成功; 100:提示; 404:警告; 500:错误）
     * @return 状态码
     */
    public int getCode() {
        return code;
    }

    /**
     * 设置信息状态码
     * (200:成功; 100:提示; 404:警告; 500:错误）
     * @param code 状态码
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 获取信息标题
     * @return 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置信息标题
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取信息内容
     * @return 信息内容
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置信息内容
     * @param message 信息内容
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取信息附加对象
     * @return 附加对象
     */
    public Object getObj() {
        return obj;
    }

    /**
     * 设置信息附加对象
     * @param obj 附加对象
     */
    public void setObj(Object obj) {
        this.obj = obj;
    }

    /**
     * 字符串格式化对象
     * @return 字符串对象信息
     */
    @Override
    public String toString() {
        return "Message{" +
                "time='" + time + '\'' +
                ", code=" + code +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", obj=" + obj.toString() +
                '}';
    }

    /**
     * 控制台打印日志
     * @param msg 打印对象
     */
    public static void printLog(Object msg){
        String time = TimeUtil.toTime(new Date(),TimeUtil.EnDateFormat_Detailed);
        System.out.println(time+" === "+msg.toString());
    }

    /**
     * 控制台打印错误信息
     * @param msg 错误信息对象
     */
    public static void printError(Object msg){
        String time = TimeUtil.toTime(new Date(),TimeUtil.EnDateFormat_Detailed);
        try {
            throw new Exception(time+" === 错误(error)："+msg.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
