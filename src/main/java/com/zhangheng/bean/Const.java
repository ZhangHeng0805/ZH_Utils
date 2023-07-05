package com.zhangheng.bean;

import cn.hutool.extra.mail.MailAccount;
import com.zhangheng.util.EncryptUtil;

/**
 * 常量类
 *
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-25 15:55
 */
public class Const {
    private Const() {

    }

    /**
     * 高德地图天气查询key
     */
    public static final String AMap_key1 = "OWZmNjRmYzczYjA4YWE3NTRjNjUyYjM4YjU2YWE4NWY=";//高德地图天气查询key

    /**
     * `
     * 邮箱服务器地址
     */
    private static final String Email_host_smtp_163 = "smtp.163.com";//网易邮箱服务器地址
    private static final String Email_host_smtp_qq="smtp.qq.com";//qq邮箱服务器地址
    /**
     * 邮件服务器的SMTP端口
     * SMTP ssl协议：465/994 非ssl协议：25
     * POP ssl：995 非ssl：110
     */
    private static final Integer Email_port_smtp = 465;
    private static final Integer Email_port_pop = 995;
    /**
     * 邮件发件人
     */
    private static final String Email_from_163 = "星曦向荣<zhangheng_0805@163.com>";//网易邮件发件人
    private static final String Email_from_qq="星曦向荣<zhangheng.0805@qq.com>";//qq邮件发件人
    /**
     * 网易邮件用户名
     */
    private static final String Email_user_163 = "zhangheng_0805";//网易邮件用户名
    private static final String Email_user_qq="zhangheng.0805";//qq邮件用户名
    /**
     * 网易邮件密码（授权码）
     */
    private static final String Email_password_163 = "SlhZT1pNWldGWlJRWVNQVQ==";//网易邮件密码（授权码）
    private static final String Email_password_qq="d2phcGxncHZoc2xxZGNnZA==";//qq邮件密码（授权码）

    /**
     * 获取构建网易的MailAccount对象
     *
     * @return 邮箱账户对象
     */
    public static final MailAccount getMailAccount_163() {
        MailAccount account = new MailAccount();
        account.setHost(Email_host_smtp_163);
        account.setPort(Email_port_smtp);
        account.setFrom(Email_from_163);
        account.setUser(Email_user_163);
        account.setPass(new String(EncryptUtil.deBase64(Email_password_163)));
        account.setAuth(true);
        account.setSslEnable(true);
        return account;
    }
    /**
     * 获取构建qq的MailAccount对象
     *
     * @return 邮箱账户对象
     */
    public static final MailAccount getMailAccount_qq() {
        MailAccount account = new MailAccount();
        account.setHost(Email_host_smtp_qq);
        account.setPort(Email_port_smtp);
        account.setFrom(Email_from_qq);
        account.setUser(Email_user_qq);
        account.setPass(new String(EncryptUtil.deBase64(Email_password_qq)));
        account.setAuth(true);
        account.setSslEnable(true);
        return account;
    }

    public static void main(String[] args) {
//        EmailUtil.send()
        System.out.println(EncryptUtil.deBase64Str(Email_password_163));
    }
}
