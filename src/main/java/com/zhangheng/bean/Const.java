package com.zhangheng.bean;

import cn.hutool.extra.mail.MailAccount;

/**
 * 常量类
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-25 15:55
 */
public class Const {
    /**
     * 高德地图天气查询key
     */
    public static final String AMap_key1="9ff64fc73b08aa754c652b38b56aa85f";//高德地图天气查询key
    /**
     * 邮箱服务器地址
     */
    private static final String Email_host="smtp.163.com";//邮箱服务器地址
//    public static final String Email_host="smtp.qq.com";//邮箱服务器地址
    /**
     * 邮件服务器的SMTP端口
     * SMTP ssl协议：465/994 非ssl协议：25
     * POP ssl：995 非ssl：110
     */
    private static final Integer Email_port=465;
    /**
     * 网易邮件发件人
     */
    private static final String Email_from="zhangheng_0805@163.com";//网易邮件发件人
//    public static final String Email_from="zhangheng.0805@qq.com";//qq邮件发件人
//    public static final String Email_user="zhangheng.0805";//qq邮件用户名
    /**
     * 网易邮件用户名
     */
    private static final String Email_user="zhangheng_0805";//网易邮件用户名
    /**
     * 网易邮件密码（授权码）
     */
    private static final String Email_password="JXYOZMZWFZRQYSPU";//网易邮件密码（授权码）
//    public static final String Email_password="wjaplgpvhslqdcgd";//qq邮件密码（授权码）

    /**
     * 获取构建的MailAccount对象
     * @return 邮箱账户对象
     */
    public static final MailAccount getMailAccount(){
        MailAccount account = new MailAccount();
        account.setHost(Email_host);
        account.setPort(Email_port);
        account.setFrom(Email_from);
        account.setUser(Email_user);
        account.setPass(Email_password);
        account.setAuth(true);
        account.setSslEnable(true);
        return account;
    }
}
