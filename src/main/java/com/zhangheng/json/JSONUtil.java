package com.zhangheng.json;

/**
 * @author: ZhangHeng
 * @email: zhangheng_0805@163.com
 * @date: 2023-07-06 11:09
 * @version: 1.0
 * @description:
 */
public class JSONUtil extends JSONObject{

    /**
     * json字符串转xml字符串
     * @param jsonStr
     * @return
     */
    public static String toXMLStr(String jsonStr){
        JSONObject jsonObject = new JSONObject(jsonStr);
        return XML.toString(jsonObject,null,XMLParserConfiguration.ORIGINAL);
    }

    /**
     * 字符串转JSONObject
     * @param xmlStr
     * @return
     */
    public static JSONObject toJSONObj(String xmlStr){
        return XML.toJSONObject(xmlStr);
    }
}
