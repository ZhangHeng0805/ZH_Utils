package com.zhangheng.test;

import cn.hutool.http.HttpUtil;

import java.util.Map;

/**
 * 简单的请求对象
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-05-04 下午4:51
 */
public class Request {
    static {
        System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
    }
    private String url;

    /**
     *
     * @param url 请求地址
     */
    public Request(String url) {
        this.url = url;
    }

    /**
     * get请求
     * @return 请求结果
     */
    public String get(){
        return HttpUtil.get(url);
    }

    /**
     * get请求
     * @param parameter 请求参数
     * @return 请求结果
     */
    public String get(Map<String,Object> parameter){
        return HttpUtil.get(url,parameter);
    }

    /**
     * post请求
     * @param parameter 请求参数
     * @return 请求结果
     */
    public String post(Map<String,Object> parameter){
        return HttpUtil.post(url,parameter);
    }

    /**
     * post请求
     * @return 请求结果
     */
    public String post(){
        return post(null);
    }
}
