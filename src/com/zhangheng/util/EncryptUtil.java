package com.zhangheng.util;

import com.zhangheng.bean.Message;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * 加密工具类
 * @author 张恒(SHA加密，SHA1加密，Md5加密，Base64加密和解密)
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-06 22:07
 */
public class EncryptUtil {


    public static final String SHA = "SHA";
    public static final String SHA1 = "SHA1";
    public static final String MD5 = "MD5";


    private static final String HMAC_SHA1 = "HmacSHA1";

    /**
     * 加密
     * @param algorithm 加密算法
     * @param source 加密对象
     * @return
     */
    public static String encrypt(String source,String algorithm) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            Message.printError(e.getMessage());
            return "";
        }
        char[] charArray = source.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];

        byte[] md5Bytes = md5.digest(byteArray);

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
     * SHA加密 并转换为16进制大写字符串
     * @param source
     * @return
     */
    public static String getSHA(String source)
    {
        try {
            MessageDigest sha = MessageDigest.getInstance(SHA);
            sha.update(source.getBytes());
            byte[] bytes = sha.digest();

            StringBuilder stringBuilder = new StringBuilder("");
            if (bytes == null || bytes.length <= 0) {
                return null;
            }
            for (int i = 0; i < bytes.length; i++) {
                int v = bytes[i] & 0xFF;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }
                stringBuilder.append(hv);
            }
            return stringBuilder.toString().toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * SHA加密 并转换为16进制大写字符串
     * @param source
     * @return
     */
    public static String getSHA1(String source)
    {
        try {
            MessageDigest sha = MessageDigest.getInstance(SHA1);
            sha.update(source.getBytes());
            byte[] bytes = sha.digest();

            StringBuilder stringBuilder = new StringBuilder("");
            if (bytes == null || bytes.length <= 0) {
                return null;
            }
            for (int i = 0; i < bytes.length; i++) {
                int v = bytes[i] & 0xFF;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }
                stringBuilder.append(hv);
            }
            return stringBuilder.toString().toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * BASE64加密
     * @param key
     * @return
     * @throws Exception
     */
    public static String enBase64(byte[] key) {
        return filter((new BASE64Encoder()).encodeBuffer(key));
    }

    /**
     * BASE64解密
     * @param key
     * @return
     * @throws IOException
     */
    public static byte[] deBase64(String key) throws IOException {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * 删除BASE64加密时出现的换行符
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
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

    /**
     * MD5 加密（UTF-8编码）
     */
    public static String getMd5(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(MD5);
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
     * @param data 待加密的数据
     * @param key  加密使用的key
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static  byte[] getSignature(String data,String key) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] keyBytes=key.getBytes();
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1);
        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal((data).getBytes());
        return rawHmac;
    }


    /**
     * 改造md5加密方法
     */
    public static String getMyMd5(String encodestr)
    {
        try
        {
            char[] hexDigits = { '9', '0', '1', '4', 'g', '2', 'a', '5', 'p', '6', 'l', 'u', '7', '8', '3', 'e' };
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
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException {
        String str="13733430842";
        String str1="10120812zhanghen....";
        String md5 = getMd5(str1);
        System.out.println(md5);
        String myMd5 = getMyMd5(str1);
        System.out.println(myMd5.length());
        System.out.println(encrypt(str1,MD5));

    }
}
