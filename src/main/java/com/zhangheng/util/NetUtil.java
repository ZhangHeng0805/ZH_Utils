package com.zhangheng.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 网络工具
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-04-08 9:16
 */
public class NetUtil {
    /**
     * 检查本机端口是否被使用
     * @param port 端口号
     * @return 如果被占用返回true 否则返回false
     */
    public static boolean isLocalPortUsing(int port){
        boolean flag = true;
        try {
            //如果该端口还在使用则返回true,否则返回false,127.0.0.1代表本机
            flag = isPortUsing("127.0.0.1", port);
        } catch (Exception e) {
        }
        return flag;
    }
    /***
     * 检查主机Host的port端口是否被使用
     * @param host ip地址
     * @param port 端口号
     * @return 如果被占用返回true 否则返回false
     * @throws UnknownHostException IP地址不通
     */
    public static boolean isPortUsing(String host,int port) throws UnknownHostException {
        boolean flag = false;
        InetAddress Address = InetAddress.getByName(host);
        try {
            Socket socket = new Socket(Address,port);  //建立一个Socket连接测试
            flag = true;
        } catch (IOException e) {
            //如果所检查的端口被占用，那么会抛出异常
            //捕获异常后，什么也不用做
        }
        return flag;
    }
}
