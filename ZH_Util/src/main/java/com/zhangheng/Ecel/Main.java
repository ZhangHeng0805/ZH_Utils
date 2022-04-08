package com.zhangheng.Ecel;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.zhangheng.log.printLog.Log;

import java.util.List;

/**
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-25 14:05
 */
public class Main {
    public static void main(String[] args) {
        String weather_xml="D:\\我的下载\\AMap_adcode_citycode_20210406.xlsx";
        ExcelReader reader = ExcelUtil.getReader(weather_xml);
        List<List<Object>> read = reader.read();
        for (List<Object> obj : read) {
            Log.info(obj);
//                System.out.println(obj.toString());

        }
    }
}
