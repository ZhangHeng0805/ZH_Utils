package com.zhangheng.util;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
    public final static String SHA256 = "SHA-256";
    /**
     * （加密类型）MD5
     */
    public final static String MD5 = "MD5";

    private final static String MyMd5HexDigits = "OTAxNHoyaDVhNmhneDc4Mw==";
    private static final Sign sign = SecureUtil.sign(SignAlgorithm.SHA512withRSA);

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
     * BASE64编码
     *
     * @param key 加密数据数组
     * @return 加密内容
     */
    public static String enBase64(byte[] key) {
        return filter(Base64Encoder.encode(key));
    }

    /**
     * BASE64编码(UTF-8)
     *
     * @param key 加密的字符串
     * @return
     */
    public static String enBase64Str(String key) {
        return enBase64Str(key, CharsetUtil.defaultCharsetName());
    }

    /**
     * BASE64编码
     *
     * @param key     加密的字符串
     * @param charset 字符串的编码名
     * @return
     */
    public static String enBase64Str(String key, String charset) {
        return Base64Encoder.encode(key, CharsetUtil.charset(charset));
    }

    /**
     * BASE64解码
     *
     * @param base64 解密的Base64
     * @return 解密的字节数组
     * @throws IOException
     */
    public static byte[] deBase64(String base64) {
        return Base64Decoder.decode(base64);
    }

    /**
     * BASE64解码为字符串(UTF-8)
     *
     * @param base64 解密的Base64
     * @return
     */
    public static String deBase64Str(String base64) {
        return deBase64Str(base64, CharsetUtil.defaultCharsetName());
    }

    /**
     * BASE64解码为字符串
     *
     * @param base64  解密的Base64
     * @param charset 字符串的编码名
     * @return
     */
    public static String deBase64Str(String base64, String charset) {
        return Base64Decoder.decodeStr(base64, CharsetUtil.charset(charset));
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

    /**
     * MD5 加密（UTF-8编码）
     *
     * @param str 加密内容
     * @return
     * @throws Exception
     */
    public static String getMd5(String str) throws Exception {
        return getMd5(str, CharsetUtil.defaultCharsetName());
    }

    /**
     * MD5 加密
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
        return getSignature(data, key, SHA256,CharsetUtil.defaultCharsetName());
    }
    public static String getSignature(String data, String key,String charset) throws Exception {
        return getSignature(data, key, SHA256, charset);
    }

    /**
     * 生成签名数据
     *
     * @param data 待加密的数据
     * @param key  加密使用的key
     * @param type 加密类型
     * @param charset 字符编码
     * @return 签名数据
     */
    public static String getSignature(String data, String key, String type,String charset) throws Exception {
        //将key进行加密
        String encrypt = encrypt(key + data + key, type);
        encrypt = EncryptUtil.enBase64Str(encrypt,charset);
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
     * @param str 加密的字符串
     * @return 加密内容
     */
    public static String getMyMd5(String str) throws Exception {
        String s = new String(deBase64(MyMd5HexDigits));
        return getMyMd5(str, s);
    }

    /**
     * 改造md5加密方法
     *
     * @param str 加密的字符串
     * @param key 加密的key（长度16）
     * @return 加密内容
     */
    public static String getMyMd5(String str, String key) throws Exception {
        if (key.length() != 16) {
            throw new Exception("The length of the key must be equal to 16（key的长度需要等于16）");
        }
        char[] hexDigits = key.toCharArray();
        byte[] strTemp = str.getBytes();
        MessageDigest mdTemp = MessageDigest.getInstance("MD5");
        mdTemp.update(strTemp);
        byte[] md = mdTemp.digest();
        int j = md.length;
        char[] strs = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            strs[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
            strs[(k++)] = hexDigits[(byte0 & 0xF)];
        }
        return new String(strs);
    }

    /**
     * 生成签名json字符串
     *
     * @param data       签名的json对象
     * @param privateKey 私钥字节数组 ,为null自动生成
     * @param publicKey  公钥字节数组 ,为null自动生成
     * @return 生成带签名的json字符串 {data:json对象,updateDate:生成时间,token:校验码,pubKey:公钥,priKey:私钥}
     */
    public static String signEncodeJson(JSONObject data, byte[] privateKey, byte[] publicKey) throws Exception{
        Date date = new Date();
        String nowTime = TimeUtil.toTime(date, TimeUtil.EnDateFormat_Detailed);
        if (privateKey == null || publicKey == null)
            privateKey = publicKey = null;
        Sign sign = SecureUtil.sign(SignAlgorithm.SHA512withRSA, privateKey, publicKey);
        String token = nowTime + data.toString() + date.getTime();
        byte[] signByte = sign.sign(token, Charset.forName("UTF-8"));
        String publicKeyBase64 = sign.getPublicKeyBase64();
        String privateKeyBase64 = sign.getPrivateKeyBase64();
        String key = EncryptUtil.enBase64(signByte);
        String json = JSONUtil.createObj()
                .set("data", data)
                .set("updateDate", nowTime)
                .set("token", key)
                .set("pubKey", publicKeyBase64)
                .set("priKey", privateKeyBase64)
                .toStringPretty();
        return json;
    }

    /**
     * 校验签名json字符串
     *
     * @param signJson 带签名的json字符串 {data:json对象,updateDate:生成时间,token:校验码,pubKey:公钥,priKey:私钥}
     * @return 带签名的json字符串内容是否合法（是否被篡改[true:合法,没有篡改]）
     * @throws ParseException
     */
    public static boolean signDecodeJson(String signJson) throws Exception {
        boolean isJson = JSONUtil.isTypeJSON(signJson);
        if (isJson) {
            JSONObject obj = JSONUtil.parseObj(signJson);
            String key = obj.getStr("token");
            String time = obj.getStr("updateDate");
            String publicKey = obj.getStr("pubKey");
            String privateKey = obj.getStr("priKey");
            byte[] signByte = EncryptUtil.deBase64(key);
            JSONObject data = obj.getJSONObject("data");
            String token = time + data.toString() + TimeUtil.toDate(time, TimeUtil.EnDateFormat_Detailed).getTime();
            Sign sign = SecureUtil.sign(SignAlgorithm.SHA512withRSA, privateKey, publicKey);
            return sign.verify(token.getBytes(), signByte);
        }
        return false;
    }

    /**
     * 生成签名Base64字符串
     *
     * @param data     签名的json对象
     * @param privateKey 私钥字节数组 ,为null自动生成
     * @param publicKey  公钥字节数组 ,为null自动生成
     * @return 签名Base64字符串
     */
    public static String signEncodeBase64(JSONObject data, byte[] privateKey, byte[] publicKey) throws Exception {
        return StrUtil.str(Base64Encoder.encode(signEncodeJson(data, privateKey, publicKey).getBytes(), true, true), Charset.forName("UTF-8"));
    }

    /**
     * 校验签名json字符串
     *
     * @param signBase64 签名Base64字符串
     * @return 签名Base64字符串内容是否合法（是否被篡改[true:合法,没有篡改]）
     * @throws Exception
     */
    public static boolean signDecodeBase64(String signBase64) throws Exception {
        if (!Base64.isBase64(signBase64))
            return false;
        return signDecodeJson(Base64Decoder.decodeStr(signBase64, Charset.forName("UTF-8")));
    }

    /**
     * 获取公钥字节数组
     * @return
     */
    public static byte[] getPublicKey() {
        return sign.getPublicKey().getEncoded();
    }

    /**
     * 获取私钥钥字节数组
     * @return
     */
    public static byte[] getPrivateKey() {
        return sign.getPrivateKey().getEncoded();
    }
}
