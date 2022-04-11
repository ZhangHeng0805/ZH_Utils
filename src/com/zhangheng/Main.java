package com.zhangheng;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.*;
import com.zhangheng.bean.Const;
import com.zhangheng.bean.Message;

import com.zhangheng.file.TxtOperation;
import com.zhangheng.log.printLog.Log;
import com.zhangheng.system.KillServer;
import com.zhangheng.util.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.File;
import java.util.*;


public class Main {

    public static void main(String[] args) throws InterruptedException {

//        String now = TimeUtil.toTime(new Date(),TimeUtil.enDateFormat_Detailed);
////        System.out.println(now);
////        String now1 = TimeUtil.toTime(new Date(),TimeUtil.enDateFormat_Detailed);
////        System.out.println(now1);

//        String ago="2022-03-04 13:50:00";
//        int difference = TimeUtil.timeDifference(now, now1,TimeUtil.enDateFormat_Detailed,TimeUtil.MilliSecond);
//        System.out.println(difference);

//        System.out.println(TimeUtil.fewDaysAgo(TimeUtil.toTime(new Date()),2));
//        System.out.println(FormatUtil.isLetterDigit(RandomrUtil.createPassWord(12,RandomrUtil.Number+RandomrUtil.Lowercase)));

//        System.out.println(RandomrUtil.createRandom(2));

//        Message.printLog(MathUtil.twoDecimalPlaces(16.6656));

//        Message.printLog(EncryptUtil.getSignature("13733430842","10120812"));
//        Message.printLog(EncryptUtil.getSignature("13733430842","10120812."));
//        Message.printLog(EncryptUtil.getMyMd5("888888"));
//        Message.printLog(EncryptUtil.encrypt("888888",EncryptUtil.SHA1));

//        String datformat=TimeUtil.dateFormatArr.get(TimeUtil.Month)+"-"+TimeUtil.dateFormatArr.get(TimeUtil.Day);
//        String s = TimeUtil.toTime(TimeUtil.toDate("2022年03月18日 11:08:25", TimeUtil.cnDateFormat), datformat);
//        Message.printLog(s);
//        int i=0;
//        while (i<100){
//            Log.Info("张恒"+i);
//            i++;
//        }

        //  生成随机密码
//        String passWord = RandomrUtil.createPassWord(32, "012");
//        Log.Info(passWord);

//        String html1="./文件/百度.html";
//        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("city", "北京");

        //淘宝商品查询
//        String goods="红米手机";
//        String taobaoUrl="https://suggest.taobao.com/sug?code=utf-8&q="+goods+"&callback=cb";
//        System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
//        String result1= HttpUtil.get(taobaoUrl);
//        result1=JSONUtil.formatJsonStr(result1);
//        TxtOperation.writeTxtFile(result1,"./文件/淘宝商品/淘宝-"+goods+".json",false);
//        System.out.println(result1);

        //天气查询
//        String cityCode = fingAdcode("武昌区");
//        if (cityCode==null){
//            cityCode="110000";
//        }
//        String weatherUrl="https://restapi.amap.com/v3/weather/weatherInfo?city="+cityCode+"&key="+ Const.AMap_key1;
//        System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
//        String result1= HttpUtil.get(weatherUrl);
//        System.out.println(printWeather(result1));

        //生成Excel
//        String name="测试3";
//        String xlsxPath="./文件/表格/"+name+".xlsx";
//        File file = TxtOperation.creatTxtFile(xlsxPath);
//        ExcelWriter writer = ExcelUtil.getWriter(true);
////        writer.passCurrentRow();//跳过当前行，既第一行
//        // 定义单元格样式
//        StyleSet style = writer.getStyleSet();
//        //头部样式
//        CellStyle headCellStyle = style.getHeadCellStyle();
//        Font font = writer.createFont();
//        font.setBold(true);
//        font.setColor(Font.COLOR_NORMAL);
//        headCellStyle.setFont(font);
//        //合并单元格
//        writer.merge(2,name,true);
//        // 一次性写出内容，使用默认样式，强制输出标题
//        writer.write(structExcel(name,50),true);
//        writer.flush(file);
//        writer.close();

        //检查端口是否占用
//        Log.Info(NetUtil.isLocalPortUsing(8888));

        //关闭服务进程
//        KillServer.kill_server();

        //验证格式
//        Log.Info(FormatUtil.isIP("218.89.171.135"));
//        Log.Info(FormatUtil.isPort("6666"));
//        Log.info(FormatUtil.isEmail("zhangheng.0805@qq.com"));
//        Log.info(FormatUtil.isIDCard(""));


        //邮箱发送
//        Log.Info(EmailUtil.send(CollUtil.newArrayList("zhangheng.0805@qq.com"),"测试","邮件来自ZH_Utils发送",false,new File("D:\\卓面\\学习资料.txt")));
    }


    /**
     * 构造Excel表格数据
     * @return
     */
    private static List<Map<String,Object>> structExcel(String name,int rows){
        List<Map<String,Object>> lists=new LinkedList<Map<String, Object>>();
        Map<String,Object> row=null;
        for (int i=1;i<=rows;i++){
            row=new LinkedHashMap<String, Object>();
            row.put("编号",NumberUtil.decimalFormat("000",i));
            row.put("名称",name+"【"+i+"】");
            row.put("数字",NumberUtil.decimalFormat("###",i));
            lists.add(row);
        }
        return lists;
    }


    /**
     * 通过读取Excel表格获取城市code
     * @param index
     * @return
     */
    public static String fingAdcode(String index){
        String weather_xml="D:\\我的下载\\AMap_adcode_citycode_20210406.xlsx";
        ExcelReader reader = null;
        try {
            reader = ExcelUtil.getReader(weather_xml);
            List<List<Object>> objects = reader.read();
            for (List<Object> obj : objects) {
                if (obj!=null&&obj.size()>0){
                    if (obj.get(0).toString().equals(index)){
                        return obj.get(1).toString();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            reader.close();
        }
        return null;
    }

    /**
     * 解析返回的天气JSON信息
     * @param json
     * @return
     */
    public static String printWeather(String json) {
        JSONObject jsonObject1 = JSONUtil.parseObj(json);
        List<JSONObject> lives = jsonObject1.getBeanList("lives",JSONObject.class);
        if (lives != null && lives.size() > 0) {
            JSONObject jsonObject = lives.get(0);
            String province = jsonObject.get("province").toString();//省份
            String city = jsonObject.get("city").toString();//城市
            String weather = jsonObject.get("weather").toString();//天气
            String temperature = jsonObject.get("temperature").toString();//温度
            String winddirection = jsonObject.get("winddirection").toString();//风向
            String windpower = jsonObject.get("windpower").toString();//风力
            String humidity = jsonObject.get("humidity").toString();//湿度
            String reporttime = jsonObject.get("reporttime").toString();//更新时间
            return province+"["+ city + "]当前天气：" + weather + " " + temperature + "℃，" + winddirection + "风" + windpower + "级,湿度：" + humidity + "%（更新与：" + TimeUtil.timeDifference(reporttime, TimeUtil.Minutes) + "分钟前）";
        }
        return null;
    }
}
