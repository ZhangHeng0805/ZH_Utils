package com.zhangheng.util;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.zhangheng.bean.Const;

import java.io.File;
import java.util.List;

/**
 * 邮件直接发送工具
 * 使用作者本人的邮箱进行发送
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-04-11 10:04
 */
public class EmailUtil {

    public static MailAccount mailAccount163 = Const.getMailAccount_163();
    public static MailAccount mailAccountqq = Const.getMailAccount_qq();

    private MailAccount mailAccount;



    public EmailUtil(MailAccount mailAccount) {
        this.mailAccount = mailAccount;
    }
    public EmailUtil(){}

    public MailAccount getMailAccount() {
        return mailAccount;
    }

    public void setMailAccount(MailAccount mailAccount) {
        this.mailAccount = mailAccount;
    }

    static {
        //解决邮件标题、附件名称中文乱码问题
        System. setProperty("mail.mime.splitlongparameters", "false");
        System. setProperty(" mail.mime.charset", "UTF-8");
    }

    /**
     * 发送邮件
     * @param recipient 收件人（可群发）
     * @param title 邮件标题
     * @param content 邮件内容
     * @param isHtml 内容是否为html格式
     * @param files 邮件附件
     * @return 是否发送成功
     */
    public boolean send(List<String> recipient, String title, String content, boolean isHtml, File... files){
        String send = MailUtil.send(mailAccount, recipient, title, content, isHtml, files);
        return true;
    }

    /**
     * 发送邮件
     * @param recipient 收件人（可群发）
     * @param title 邮件标题
     * @param content 邮件内容
     * @param isHtml 内容是否为html格式
     * @return 是否发送成功
     */
    public boolean send(List<String> recipient, String title, String content,boolean isHtml){
        String send = MailUtil.send(mailAccount,recipient, title, content, isHtml);
        return true;
    }
    /**
     * 发送邮件
     * @param recipient 收件人（可群发）
     * @param title 邮件标题
     * @param content 邮件内容
     * @return 是否发送成功
     */
    public boolean send(List<String> recipient, String title, String content){
        String send = MailUtil.send(mailAccount,recipient, title, content, false);
        return true;
    }

    public static void main(String[] args) {
        EmailUtil emailUtil = new EmailUtil(mailAccount163);
        emailUtil.send(ListUtil.of("zhangh@keyvalues.cn"),"测试标题",TimeUtil.getNowTime());
    }
}
