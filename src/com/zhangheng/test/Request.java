package com.zhangheng.test;

import cn.hutool.http.HttpUtil;

import java.util.Map;

/**
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

    public Request(String url) {
        this.url = url;
    }
    public String get(){
        return HttpUtil.get(url);
    }
    public String get(Map<String,Object> parameter){
        return HttpUtil.get(url,parameter);
    }
    public String post(Map<String,Object> parameter){
        return HttpUtil.post(url,parameter);
    }
    public String post(){
        return post(null);
    }
}
