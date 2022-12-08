package com.zhangheng.util;


import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;


/**
 * 加密工具类
 * (SHA加密 ， SHA256加密 ， Md5加密 ， Base64加密和解密，改造的md5)
 *
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-06 22:07
 */
public class EncryptUtil {


    /**
     * （加密类型）SHA
     */
    public final static String SHA = "SHA";
    /**
     * （加密类型）SHA256
     */
    public final static  String SHA256 = "SHA-256";
    /**
     * （加密类型）MD5
     */
    public final static String MD5 = "MD5";

    private final static String MyMd5HexDigits = "OTAxNHoyaDVhNmhneDc4Mw==";

    /**
     * 加密
     *
     * @param algorithm 加密类型
     * @param source    加密内容
     * @return 加密结果
     */
    public static String encrypt(String source, String algorithm) throws Exception {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        char[] charArray = source.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];

        byte[] md5Bytes = md.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    /**
     * SHA加密
     *
     * @param source 加密内容
     * @return 加密结果
     */
    public static String getSHA(String source) throws Exception {
        return encrypt(source, SHA);
    }

    /**
     * SHA256加密
     *
     * @param source 加密内容
     * @return 加密结果
     */
    public static String getSHA256(String source) throws Exception {
        return encrypt(source, SHA256);
    }


    /**
     * BASE64加密
     *
     * @param key 加密数据数组
     * @return 加密内容
     */
    public static String enBase64(byte[] key) {
        return filter(Base64Encoder.encode(key));
    }

    /**
     * BASE64解密
     *
     * @param key 解密的Base64
     * @return 解密的数据数组
     * @throws IOException
     */
    public static byte[] deBase64(String key) {
        return Base64Decoder.decode(key);
    }

    /**
     * 删除BASE64加密时出现的换行符
     *
     * @param str BASE64
     * @return 过滤后的BASE64
     */
    private static String filter(String str) {
        String output = null;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            int asc = str.charAt(i);
            if (asc != 10 && asc != 13) {
                sb.append(str.subSequence(i, i + 1));
            }
        }
        output = new String(sb);
        return output;
    }

    public static String getMd5(String str) throws Exception {
        return getMd5(str, "UTF-8");
    }

    /**
     * MD5 加密（UTF-8编码）
     *
     * @param str      加密内容
     * @param encoding 编码格式
     *                 The name of a supported {{@linkplain java.nio.charset.Charset encoding}
     * @return 加密结果
     */
    public static String getMd5(String str, String encoding) throws Exception {
        MessageDigest messageDigest = null;
        messageDigest = MessageDigest.getInstance(MD5);
        messageDigest.reset();
        messageDigest.update(str.getBytes(encoding));

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();
    }


    /**
     * 生成签名数据
     *
     * @param data 待加密的数据
     * @param key  加密使用的key
     * @return 签名数据
     */
    public static String getSignature(String data, String key) throws Exception {
        //将key进行SHA1加密
        String encrypt = encrypt(key + data + key, SHA256);
        List<Character> set = new ArrayList<>();
        char[] chars = encrypt.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            char c = chars[i];
            if (set.indexOf(c) < 0) {
                set.add(c);
            }
            if (set.size() == 16) {
                break;
            }
        }
        StringBuffer sb = new StringBuffer();
        for (Character c : set) {
            sb.append(c);
        }
        //然后使用自制的Md5加密
        return getMyMd5(data, sb.toString());
    }


    /**
     * 改造md5加密方法
     *
     * @param encodestr 加密的字符串
     * @return 加密内容
     */
    public static String getMyMd5(String encodestr) throws Exception {
        String s = new String(deBase64(MyMd5HexDigits));
        return getMyMd5(encodestr, s);
    }

    /**
     * 改造md5加密方法
     *
     * @param encodestr 加密的字符串
     * @param key       加密的key（长度16）
     * @return 加密内容
     */
    public static String getMyMd5(String encodestr, String key) throws Exception {

        if (key.length() != 16) {
            throw new Exception("The length of the key must be equal to 16（key的长度需要等于16）");
        }
        char[] hexDigits = key.toCharArray();
        byte[] strTemp = encodestr.getBytes();
        MessageDigest mdTemp = MessageDigest.getInstance("MD5");
        mdTemp.update(strTemp);
        byte[] md = mdTemp.digest();
        int j = md.length;
        char[] str = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
            str[(k++)] = hexDigits[(byte0 & 0xF)];
        }
        return new String(str);

    }
}
