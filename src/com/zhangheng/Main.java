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

import com.zhangheng.file.FileParse;
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
//        String s = TimeUtil.toTime(TimeUtil.toDate("2022???03???18??? 11:08:25", TimeUtil.cnDateFormat), datformat);
//        Message.printLog(s);
//        int i=0;
//        while (i<100){
//            Log.Info("?????????"+i);
//            i++;
//        }

        //  ??????????????????
//        String passWord = RandomrUtil.createPassWord(32, "012");
//        Log.Info(passWord);

//        String html1="./??????/??????.html";
//        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("city", "??????");

        //??????????????????
//        String goods="????????????";
//        String taobaoUrl="https://suggest.taobao.com/sug?code=utf-8&q="+goods+"&callback=cb";
//        System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
//        String result1= HttpUtil.get(taobaoUrl);
//        result1=JSONUtil.formatJsonStr(result1);
//        TxtOperation.writeTxtFile(result1,"./??????/????????????/??????-"+goods+".json",false);
//        System.out.println(result1);

        //????????????
//        String cityCode = fingAdcode("?????????");
//        if (cityCode==null){
//            cityCode="110000";
//        }
//        String weatherUrl="https://restapi.amap.com/v3/weather/weatherInfo?city="+cityCode+"&key="+ Const.AMap_key1;
//        System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
//        String result1= HttpUtil.get(weatherUrl);
//        System.out.println(printWeather(result1));

        //??????Excel
//        String name="??????3";
//        String xlsxPath="./??????/??????/"+name+".xlsx";
//        File file = TxtOperation.creatTxtFile(xlsxPath);
//        ExcelWriter writer = ExcelUtil.getWriter(true);
////        writer.passCurrentRow();//??????????????????????????????
//        // ?????????????????????
//        StyleSet style = writer.getStyleSet();
//        //????????????
//        CellStyle headCellStyle = style.getHeadCellStyle();
//        Font font = writer.createFont();
//        font.setBold(true);
//        font.setColor(Font.COLOR_NORMAL);
//        headCellStyle.setFont(font);
//        //???????????????
//        writer.merge(2,name,true);
//        // ???????????????????????????????????????????????????????????????
//        writer.write(structExcel(name,50),true);
//        writer.flush(file);
//        writer.close();

        //????????????????????????
//        Log.Info(NetUtil.isLocalPortUsing(8888));

        //??????????????????
//        KillServer.kill_server();

        //????????????
//        Log.Info(FormatUtil.isIP("218.89.171.135"));
//        Log.Info(FormatUtil.isPort("6666"));
//        Log.info(FormatUtil.isEmail("zhangheng.0805@qq.com"));
//        Log.info(FormatUtil.isIDCard(""));

        //????????????
//        Log.Info(EmailUtil.send(CollUtil.newArrayList("zhangheng.0805@qq.com"),"??????","????????????ZH_Utils??????",false,new File("D:\\??????\\????????????.txt")));

//        findWord("??????????????????");
        List<String> list = FileParse.parseVCF("./res/00001.vcf","UTF-8");
        for (String s : list) {
            Log.Info(s);
        }

    }


    /**
     * ???????????????list
     * @param list
     */
    public static void printList(List<String> list){
        for (Object o : list) {
            System.out.println(o.toString());
        }
    }

    /**
     * ????????????????????????????????????
     * @param str
     */
    public static void findWord(String str){
        String path="./res/??????????????????.txt";
        File file = new File(path);
        List<String> list = TxtOperation.readTxtFile(file,"UTF-8");
        int i = list.indexOf(str);
        if (i >-1){
            Log.info("["+str+"]?????????"+(i+1)+"???");
        }else {
            Log.info("["+str+"]?????????");
        }
    }

    /**
     * ??????Excel????????????
     * @return
     */
    private static List<Map<String,Object>> structExcel(String name,int rows){
        List<Map<String,Object>> lists=new LinkedList<Map<String, Object>>();
        Map<String,Object> row=null;
        for (int i=1;i<=rows;i++){
            row=new LinkedHashMap<String, Object>();
            row.put("??????",NumberUtil.decimalFormat("000",i));
            row.put("??????",name+"???"+i+"???");
            row.put("??????",NumberUtil.decimalFormat("###",i));
            lists.add(row);
        }
        return lists;
    }


    /**
     * ????????????Excel??????????????????code
     * @param index
     * @return
     */
    public static String fingAdcode(String index){
        String weather_xml="D:\\????????????\\AMap_adcode_citycode_20210406.xlsx";
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
     * ?????????????????????JSON??????
     * @param json
     * @return
     */
    public static String printWeather(String json) {
        JSONObject jsonObject1 = JSONUtil.parseObj(json);
        List<JSONObject> lives = jsonObject1.getBeanList("lives",JSONObject.class);
        if (lives != null && lives.size() > 0) {
            JSONObject jsonObject = lives.get(0);
            String province = jsonObject.get("province").toString();//??????
            String city = jsonObject.get("city").toString();//??????
            String weather = jsonObject.get("weather").toString();//??????
            String temperature = jsonObject.get("temperature").toString();//??????
            String winddirection = jsonObject.get("winddirection").toString();//??????
            String windpower = jsonObject.get("windpower").toString();//??????
            String humidity = jsonObject.get("humidity").toString();//??????
            String reporttime = jsonObject.get("reporttime").toString();//????????????
            return province+"["+ city + "]???????????????" + weather + " " + temperature + "??????" + winddirection + "???" + windpower + "???,?????????" + humidity + "%???????????????" + TimeUtil.timeDifference(reporttime, TimeUtil.Minutes) + "????????????";
        }
        return null;
    }
}
