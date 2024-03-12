package com.zhangheng.system;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

/**
 * 网络工具
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-04-08 9:16
 */
public class NetUtil extends cn.hutool.core.net.NetUtil {
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

    /**
     * Linux和Windows通用获取本机内网地址
     * @return
     */
    public static String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("IP地址获取失败" + e.toString());
        }
        return "";
    }

    /**
     * 获取本机主机名
     * @return
     */
    public static String getLocalHostName() {
        return getLocalhost().getHostName();
    }
}
