package com.zhangheng.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.zhangheng.file.TxtOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 解析读取配置文件
 *
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-12-05 00:22
 */
public class SettingUtil {
    private List<String> data;
    private Map<String, Object> map;

    /**
     * 根据配置文件路径构造
     * @param path 配置文件的路径
     */
    public SettingUtil(String path) {
        List<String> data = TxtOperation.readTxtFile(path);
        analysis();
    }

    /**
     * 解析配置
     */
    private void analysis() {
        map = new HashMap<>();
        if (data != null) {
            for (String d : data) {
                if (!d.startsWith("#")) {
                    String[] split = d.split("=");
                    int length = split.length;
                    if (length >= 2) {
                        if (length > 2) {
                            StringBuilder sb = new StringBuilder();
                            for (int i = 1; i < length; i++) {
                                if (i == length - 1) {

                                    if (StrUtil.isEmpty(split[i].trim())) {
                                        sb.append("=");
                                    } else {
                                        sb.append(split[i]);
                                    }
                                    int index = d.indexOf(sb.toString()) + sb.length();
                                    if (index !=d.length()){
                                        sb.append(d.substring(index));
                                    }
                                } else {
                                    sb.append(split[i] + "=");
                                }
                            }
                            map.put(split[0].trim(), sb.toString().trim());
                        } else {
                            map.put(split[0].trim(), split[1].trim());
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取配置文件中解析的所有配置
     * @return 解析的所有配置
     */
    public Map<String, Object> getAllSetting() {
        return map;
    }

    /**
     * 获取配置文件中的所有内容
     *
     * @return 每行内容
     */
    public List<String> getAllContent(){
        return data;
    }

    /**
     * 获取key的配置值
     * @param key 配置名
     * @return String类型的配置值
     */
    public String getStr(String key) {
        return Convert.toStr(map.get(key));
    }

    /**
     * 获取key的配置值
     * @param key 配置名
     * @return 整型类型的配置值
     */
    public Integer getInt(String key) {
        return Convert.toInt(map.get(key));
    }

    /**
     * 获取key的配置值
     * @param key 配置名
     * @return 布尔类型的配置值
     */
    public Boolean getBool(String key) {
        return Convert.toBool(map.get(key));
    }

    /**
     * 获取key的配置值
     * @param key 配置名
     * @return Double类型的配置值
     */
    public Double getDouble(String key) {
        return Convert.toDouble(map.get(key));
    }
    /**
     * 获取key的配置值
     * @param key 配置名
     * @return Float类型的配置值
     */
    public Float getFloat(String key) {
        return Convert.toFloat(map.get(key));
    }
    /**
     * 获取key的配置值
     * @param key 配置名
     * @return 字符类型的配置值
     */
    public Character getChar(String key) {
        return Convert.toChar(map.get(key));
    }
}
