package com.zhangheng.util;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.zhangheng.bean.Const;

import java.io.File;
import java.util.List;

/**
 * 邮件直接发送工具
 * 使用作者本人的网易邮箱进行发送
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-04-11 10:04
 */
public class EmailUtil {

    private static MailAccount mailAccount = Const.getMailAccount();

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
     * @return
     */
    public static boolean send(List<String> recipient, String title, String content, boolean isHtml, File[] files){
        String send = MailUtil.send(mailAccount, recipient, title, content, isHtml, files);
        return true;
    }

    /**
     * 发送邮件
     * @param recipient 收件人（可群发）
     * @param title 邮件标题
     * @param content 邮件内容
     * @param isHtml 内容是否为html格式
     * @return
     */
    public static boolean send(List<String> recipient, String title, String content,boolean isHtml){
        String send = MailUtil.send(mailAccount,recipient, title, content, isHtml);
        return true;
    }
    /**
     * 发送邮件
     * @param recipient 收件人（可群发）
     * @param title 邮件标题
     * @param content 邮件内容
     * @return
     */
    public static boolean send(List<String> recipient, String title, String content){
        String send = MailUtil.send(mailAccount,recipient, title, content, false);
        return true;
    }
}
