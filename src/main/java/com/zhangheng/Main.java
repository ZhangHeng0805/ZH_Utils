package com.zhangheng;


import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import com.zhangheng.file.FileUtil;
import com.zhangheng.file.TxtOperation;
import com.zhangheng.log.printLog.Log;
import com.zhangheng.util.MathUtil;
import com.zhangheng.util.TimeUtil;
import oshi.hardware.ComputerSystem;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.Sensors;
import oshi.software.os.OperatingSystem;

import java.io.File;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


class Main {

    public static void main(String[] args) throws Exception {

//        File file = new File("./res/all-hanzi.txt");
//        byte[] fileBytes = FileChange.fileToBytes(file);
//        String s = EncryptUtil.enBase64(fileBytes);
//        System.out.println(s);
//        byte[] bytes = EncryptUtil.deBase64(s);
//        File file1 = FileChange.bytesToFile(bytes, "./res/", "1.txt");
//        System.out.println(file1);

//        List<String> list=new ArrayList<>();
//        list.add("zhangheng.0805@qq.com");
//        EmailUtil.send(list,"测试",TimeUtil.getNowTime());


//        List<String> list = TxtOperation.readTxtFile("./res/calendar-conversion.js", "UTF-8");
//        StringBuilder sb = new StringBuilder();
//        for (String s : list) {
//            sb.append(s+"\n");
//        }
//        String s = Convert.unicodeToStr(sb.toString());
//        System.out.println(s);

//        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
//        engine.eval(new FileReader("./res/calendar-conversion.js"));
//            if (engine instanceof Invocable){
//                Invocable in= (Invocable) engine;
//                Object f = in.invokeFunction("f1");
//                System.out.println(JSONUtil.toJsonStr(f));
//            }

//        com.zhangheng.log.Log.info("123");
//        com.zhangheng.log.Log.error("123");
//        com.zhangheng.log.Log.debug("123");
//        com.zhangheng.log.Log.warn("123");
//        PrintStream out = System.out;

//        KillServer.kill_server("8081");

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
//            Log.Info("制作中"+i);
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
//        Log.Info(EmailUtil.send(CollUtil.newArrayList("zhangheng.0805@qq.com"),"测试","邮件来自ZH_Utils发送"));

//        findWord("二氧杂环己烷");

//        List<String> list = FileParse.parseVCF("./res/00003.vcf","UTF-8");
//        for (String s : list) {
//            Log.Info(s);
//        }

//        String path="D:\\Users\\张恒\\IdeaProjects\\happy_shopping\\files\\Goods_Images\\1\\乐在购物网4a5c8_12.png";
//        Log.info(TxtOperation.creatTxtFile(path));
//        String name = FileOperation.filterFileName(path);
//        Log.info(name);
//        FileOperation.deleteFile(name);

//        String s = PinyinUtil.getFirstLetter("12手术ah中风*1", "");
//        Log.info(s);

//        String dyUrl1="https://v.douyin.com/FhHCWKG/";
//        String dyUrl1_1="https://www.iesdouyin.com/share/video/7082368143749860646/?region=CN&amp;mid=7082368286524017421&amp;u_code=ela4lf9h&amp;did=MS4wLjABAAAAgniQwlwTf1Oa4q-PZwdipICKgtkZBOu5U9YnIzyCOuj4Zrw-lXzpclV6KWiecvCd&amp;iid=MS4wLjABAAAA9M5-r8HNz-oJybMGlt43b-aMjFWqnTy0MQsHwFFidoL1eXIMBaiL3l_InIK_0kL4&amp;with_sec_did=1&amp;titleType=title&amp;utm_source=copy&amp;utm_campaign=client_share&amp;utm_medium=android&amp;app=aweme";
//        String dyUrl1_2="https://www.douyin.com/video/7082368143749860646?previous_page=app_code_link";
//        String dyUrl1_3="https://www.douyin.com/video/7082368143749860646";
//        String video_url_1="https://www.dadagui.com/vodsearch/-------------.html?wd=你好&submit=";
//        System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
//        String user_agent="Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36";
//        String body = HttpRequest.get(video_url_1)
//                .header(Header.USER_AGENT, user_agent)
//                .execute().body();
//        String s = HttpUtil.get(dyUrl1_2);
//        System.out.println(body);
//        try {
//            body=body.substring(body.indexOf("<body>"),body.lastIndexOf("</body>"));
//            Document doc = new SAXReader().read(new ByteArrayInputStream(body.getBytes("UTF-8")));
//            String xpath = "";
//            xpath = "//a";
//            List<Node> nodes = doc.selectNodes(xpath);
//            for (Node n : nodes) {
//                System.out.println(n);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }

//        String a="65665623532353.2";
//        System.out.println(FormatUtil.isNumber(a));

//        String encode = QRCodeUtil.encode("124");
//        System.out.println(encode);

//        boolean webUrl = FormatUtil.isWebUrl("https://www.baidu.com");
//        System.out.println(webUrl);

//        String url="http://bmy82.com";
//        String url="https://www.baidu.com/s?ie=UTF-8&wd="+"手机";
//        String url="http://xh.5156edu.com/html3/10088.html";
//        String s = HttpUtil.get(url);
//        System.out.println(s);

//        //获取首字母
//        String firstLetter = PinyinUtil.getFirstLetter("张恒", "");
//        System.out.println(firstLetter);
//
//        //获取全拼
//        String pinyin = PinyinUtil.getPinyin("张恒","");
//        System.out.println(pinyin);

//        Robot rob = new Robot();
//        rob.delay(1000);
//        rob.mouseMove(820,710);
//        rob.delay(500);
//        rob.mouseMove(820,710);
//        rob.delay(500);
//        rob.mouseMove(820,710);
////        rob.delay(1000);
//        rob.mousePress(InputEvent.BUTTON1_MASK );
//        for (int i=1;i<280;i++){
//            rob.mouseMove(820+i,710);
//            rob.delay(10);
//        }
//        rob.mouseRelease(InputEvent.BUTTON1_MASK);


//        ITesseract tesseract = new Tesseract();
//        tesseract.setDatapath("./res/tessdata");
//        tesseract.setLanguage("eng");
//        File file = new File("./res/img/文字识别.png");
//        System.out.println(file.exists());
//        String s = tesseract.doOCR(file);
//        System.out.println(s);


//        System.out.println(TimeUtil.getNowUnix());
//        while (true) {
//            CpuInfo cpuInfo = OshiUtil.getCpuInfo();//
////            CentralProcessor processor = OshiUtil.getProcessor();
//            GlobalMemory memory = OshiUtil.getMemory();
//            HardwareAbstractionLayer hardware = OshiUtil.getHardware();
//            List<HWDiskStore> diskStores = OshiUtil.getDiskStores();
////            System.out.println(processor);
//            System.out.println();
//            System.out.println("CPU利用率：" + cpuInfo.getUsed() + "% - " + "CPU空闲率：" + cpuInfo.getFree() + "%");
//            System.out.println("内存：[可用:"+ FileUtil.getFileSizeString(memory.getAvailable())+"/总计:"+FileUtil.getFileSizeString(memory.getTotal())+"]");
//            for (HWDiskStore diskStore : diskStores) {
//                String name = diskStore.getModel();
//                boolean b = diskStore.updateAttributes();
//                if (b) {
//                    long writeBytes = diskStore.getWriteBytes();
//                    long readBytes = diskStore.getReadBytes();
//                    long size = diskStore.getSize();
//                    System.out.println("硬盘：" + name + " [" + FileUtil.fileSizeFormat(writeBytes) + "/" + FileUtil.fileSizeFormat(size) + "]");
//                }
//            }
//        }
//        JSONObject data = JSONUtil.createObj()
//                .set("name", "张恒")
//                .set("email", "zhangheng_0805@163.com")
//                .set("version","23.01.31")
//                ;
//        String encode = EncryptUtil.signEncodeJson(data, EncryptUtil.getPrivateKey(),EncryptUtil.getPublicKey());
//        System.out.println(encode);
//        JSONObject entries = JSONUtil.parseObj(encode);
//        entries.set("data",data.set("version","23.01.31"));
//        encode=entries.toString();
//        System.out.println(EncryptUtil.signDecodeJson(encode));


//        File src = new File("./log日志/5oiR5rWL6K+VMQ==.log");
//        String s = FileUtil.mainName(src);
//        System.out.println(s);
//        if (Validator.hasChinese(s))
//            s = Base64Encoder.encode(s, Charset.forName("UTF-8"));
//        src = FileUtil.rename(src, s, true, true);
//        System.out.println(src.getName());
//        s = FileUtil.mainName(src);
//        if (Base64.isBase64(s))
//            s = Base64.decodeStr(s, Charset.forName("UTF-8"));
//        System.out.println(s);
//        src = FileUtil.rename(src, s, true, true);

//        long time = new Date().getTime();
//        long time = 1675238189768L;
//        System.out.println(time);
//        String path="src";
//        String key="654321";
//        String signature = EncryptUtil.getSignature(path + time, key);
//        String body = HttpRequest.post("http://127.0.0.1:8089/folder/scan")
//                .body("path="+path+"&token=" + signature )
//                .header("_t",time+"")
//                .execute().body();
//        System.out.println(body);


//        String s = Rot.encode13("zh12345678.@#9恒");
//        System.out.println(s);
//        System.out.println(Rot.decode13(s));


//        EmailUtil proxy = ProxyUtil.proxy(new EmailUtil(), TimeIntervalAspect.class);
//        proxy.setMailAccount(Const.getMailAccount_qq());
//        proxy.send(ListUtil.of("3052847663@qq.com"),"测试","这是测试邮件");


        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
//        扭曲线条遮挡
//        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 6, 15);
//        复杂线圈背景
//        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 8, 200);
//        复杂断线背景
//        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(200, 100,6,1000);
//        动图显示
//        AbstractCaptcha captcha=new CircleCaptcha(200,100,4,80);
//        //图形验证码写出，可以写出到文件，也可以写出到流
//        // 自定义验证码内容为四则运算方式
////        captcha.setGenerator(new MathGenerator());
////
////        String random=new String(RandomUtil.CAPITAL_LETTER)+new String(RandomUtil.LOWERCASE_LETTER)+new String(RandomUtil.NUMBER);
////        captcha.setGenerator(new RandomGenerator(random, 5));
//        captcha.createCode();
//        String code = captcha.getCode();
////        String eval = MathUtil.operation(code);
//        String eval = "";
//        captcha.write("res/"+code+eval+".png");
//        System.out.println(code);
//        //验证图形验证码的有效性，返回boolean值
////        System.out.println(captcha.verify("1234"));
//        System.out.println(eval);


//        SimpleServer server = HttpUtil.createServer(8888)
//                .addAction("/1", (request, response) -> {
//                    String body1 = request.getBody();
////                    String url = request.getParam("url");
//                    String url = "http://127.0.0.1:8082/HttpService/JSONTest";
//                    HttpRequest httpRequest = HttpRequest.get(url);
//                    for (Map.Entry<String, List<String>> entry : request.getHeaders().entrySet()) {
//                        String key = entry.getKey();
//                        String value = request.getHeader(key);
//                        httpRequest=httpRequest.header(key, value);
//                    }
//                    HttpResponse execute = httpRequest.body(body1).execute();
//                    String body = execute.body();
//                    response.write(body, ContentType.JSON.toString());
//                });
//        server.start();

//        String body = HttpRequest.get("https://dashboard.cpolar.com/status").
//                execute().body();
////        System.out.println(body);
//        if (body.startsWith("<a href=\"/login\">")){
//            HttpResponse execute1 = HttpRequest.get("https://dashboard.cpolar.com/login").
//                    execute();
//            String login = execute1.body();
//            String token = Jsoup.parse(login).select("input").select("[name=csrf_token]").attr("value");
////            System.out.println(login);
//            System.out.println(token);
//            String cookieStr1 = execute1.getCookieStr();
//            System.out.println(cookieStr1);
//            HttpResponse execute2 = HttpRequest.post("https://dashboard.cpolar.com/login")
//                    .header("origin","https://dashboard.cpolar.com")
//                    .header("referer","https://dashboard.cpolar.com/login")
//                    .header("cookie", cookieStr1)
//                    .header("content-type","application/x-www-form-urlencoded")
//                    .form("csrf_token", token)
//                    .form("login", "zhangheng_0805@163.com")
//                    .form("password", "dashboard.cpolar.com")
//                    .execute();
//            String content = execute2.body();
//            System.out.println(execute2.getCookieStr());
//            System.out.println(content);
//        }

//        String bigDecimal = MathUtil.simpleOperation("3.6*5=");
//        String operation = MathUtil.operation("(((12+3)-6)*2)/5");
//        System.out.println(operation);
//        System.out.println(bigDecimal);

//        String filePath = "F:\\下载\\Image\\logo_XXXR.png";
////        File file = new File(filePath);
////        String s = FileOperation.fileToBase64(file,true);
////        System.out.println(s);

//        String ug="Mozilla/5.0 (iPhone; CPU iPhone OS 15_4 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 SP-engine/2.69.0 main%2F1.0 baiduboxapp/13.31.0.10 (Baidu; P2 15.4) NABar/1.0 themeUA=Theme/default";
//        String ug="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36 Edg/112.0.1722.58";
////        UserAgent parse = UserAgentUtil.parse(ug);
////        System.out.println(JSONUtil.parse(parse).toStringPretty());

//        String ip = true ? "171.113.125.74, 172.16.4.2" : "0:0:0:0:0:0:0:1";
//        System.out.println(FormatUtil.isIpv4(ip));
//        String s = ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : FormatUtil.isIpv4(ip) ? ip : ip.substring(0, ip.indexOf(','));
//        System.out.println(s);

//        for (int i = 0; i < 20; i++) {
//            Log.Info(i);
////            Thread.sleep(2);
//        }
        GlobalMemory memory = OshiUtil.getMemory();
        long available = memory.getAvailable();
        System.out.println("可用内存："+FileUtil.fileSizeStr(available));
        long total = memory.getTotal();
        System.out.println("总内存："+FileUtil.fileSizeStr(total));
        System.out.println("内存使用百分比："+MathUtil.formatPercent(MathUtil.div(MathUtil.sub(total, available) , total),2));
        CpuInfo cpuInfo = OshiUtil.getCpuInfo(3000);
        System.out.println("CPU核数:"+cpuInfo.getCpuNum());
        System.out.println("CPU利用率:"+cpuInfo.getUsed());
        System.out.println("CPU当前空闲率:"+cpuInfo.getFree());
        Sensors sensors = OshiUtil.getSensors();
        System.out.println("CPU温度："+ MathUtil.numerFormat(sensors.getCpuTemperature())+"℃");
        List<HWDiskStore> diskStores = OshiUtil.getDiskStores();
        //System.out.println(JSONUtil.toJsonPrettyStr(diskStores));
        for (int i = 0; i < diskStores.size(); i++) {
            System.out.println("硬盘【"+diskStores.get(i).getModel()+"】："+FileUtil.fileSizeStr(diskStores.get(i).getSize()));
        }
        ComputerSystem system = OshiUtil.getSystem();
        System.out.println("主板信息："+system.getBaseboard().toString());
        System.out.println("固件信息："+system.getFirmware().toString());
        System.out.println("制作商："+system.getManufacturer());
        System.out.println("序列号："+system.getSerialNumber());
        System.out.println("硬件ID："+system.getHardwareUUID());
        OperatingSystem os = OshiUtil.getOs();
        System.out.println("系统位数："+os.getBitness());

    }


    /**
     * 控制台输出list
     *
     * @param list
     */
    public static void printList(List<Object> list) {
        for (Object o : list) {
            System.out.println(o.toString());
        }
    }

    /**
     * 获取文本文件中文本的位置
     *
     * @param str
     */
    public static void findWord(String str) throws Exception {
        String path = "./res/百度分词词库.txt";
        File file = new File(path);
        List<String> list = TxtOperation.readTxtFile(file, "UTF-8");
        int i = list.indexOf(str);
        if (i > -1) {
            Log.info("[" + str + "]存在于" + (i + 1) + "行");
        } else {
            Log.info("[" + str + "]不存在");
        }
    }

    /**
     * 构造Excel表格数据
     *
     * @return
     */
    private static List<Map<String, Object>> structExcel(String name, int rows) {
        List<Map<String, Object>> lists = new LinkedList<Map<String, Object>>();
        Map<String, Object> row = null;
        for (int i = 1; i <= rows; i++) {
            row = new LinkedHashMap<String, Object>();
            row.put("编号", NumberUtil.decimalFormat("000", i));
            row.put("名称", name + "【" + i + "】");
            row.put("数字", NumberUtil.decimalFormat("###", i));
            lists.add(row);
        }
        return lists;
    }


    /**
     * 通过读取Excel表格获取城市code
     *
     * @param index
     * @return
     */
    public static String fingAdcode(String index) {
        String weather_xml = "D:\\我的下载\\AMap_adcode_citycode_20210406.xlsx";
        ExcelReader reader = null;
        try {
            reader = ExcelUtil.getReader(weather_xml);
            List<List<Object>> objects = reader.read();
            for (List<Object> obj : objects) {
                if (obj != null && obj.size() > 0) {
                    if (obj.get(0).toString().equals(index)) {
                        return obj.get(1).toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
        return null;
    }

    /**
     * 解析返回的天气JSON信息
     *
     * @param json
     * @return
     */
    public static String printWeather(String json) throws ParseException {
        JSONObject jsonObject1 = JSONUtil.parseObj(json);
        List<JSONObject> lives = jsonObject1.getBeanList("lives", JSONObject.class);
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
            return province + "[" + city + "]当前天气：" + weather + " " + temperature + "℃，" + winddirection + "风" + windpower + "级,湿度：" + humidity + "%（更新与：" + TimeUtil.timeDifference(reporttime, TimeUtil.Minutes) + "分钟前）";
        }
        return null;
    }
}
