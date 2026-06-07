package com.zhangheng.bean;

import com.zhangheng.util.TimeUtil;

/**
 * 消息实体类
 *
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-04 13:25
 */
public class Message<T> {
    private String time;//时间戳
    private int code;//状态码(0:成功; 100:提示; 404:警告; 500:错误）
    private String title;//标题
    private String message;//内容
    private T data;//附加对象
    private boolean success;//code为0成功，否则失败

    /**
     * @param time    时间
     * @param code    状态码(200:成功; 100:提示; 404:警告; 500:错误）
     * @param title   标题
     * @param message 内容
     * @param data    附加对象
     */
    public Message(String time, int code, String title, String message, T data) {
        this.time = time;
        this.code = code;
        this.title = title;
        this.message = message;
        this.data = data;
        this.success = code == 0;
    }

    public Message(int code, String title, String message, T data) {
        this(TimeUtil.getNowTime(), code, title, message, data);
    }

    public Message(int code, String title, String message) {
        this(code, title, message, null);
    }

    public Message() {
        this(0, null, null);
    }

    /**
     * 获取信息时间
     *
     * @return 时间
     */
    public String getTime() {
        return time;
    }

    /**
     * 设置信息时间
     *
     * @param time 时间
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 获取信息状态码
     * (200:成功; 100:提示; 404:警告; 500:错误）
     *
     * @return 状态码
     */
    public int getCode() {
        return code;
    }

    /**
     * 设置信息状态码
     * (200:成功; 100:提示; 404:警告; 500:错误）
     *
     * @param code 状态码
     */
    public void setCode(int code) {
        this.code = code;
        success = code == 0;
    }

    /**
     * 获取信息标题
     *
     * @return 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置信息标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取信息内容
     *
     * @return 信息内容
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置信息内容
     *
     * @param message 信息内容
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取信息附加对象
     *
     * @return 附加对象
     */
    public T getData() {
        return data;
    }

    /**
     * 设置信息附加对象
     *
     * @param data 附加对象
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * 获取消息是否成功
     *
     * @return
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * 设置消息是否成功
     *
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * 字符串格式化对象
     *
     * @return 字符串对象信息
     */
    @Override
    public String toString() {
        return "Message{" +
                "time='" + time + '\'' +
                ", code=" + code +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", success=" + success +
                '}';
    }

    /**
     * 控制台打印日志
     *
     * @param msg 打印对象
     */
    public static void printLog(Object msg) {
        System.out.println(TimeUtil.getNowTime() + " === " + msg.toString());
    }

}
