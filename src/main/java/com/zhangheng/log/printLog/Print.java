package com.zhangheng.log.printLog;

import com.zhangheng.file.TxtOperation;
import com.zhangheng.util.TimeUtil;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 将输出的日志保存为日志文件
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-21 16:48
 */
class Print {
    //单个log文件的最大值(Mb)
    private static final double MaxSize=3;
    //日志文件的存储位置
    private static final String baseDir="./zh-log日志/";
    private static String fileName;

    static void print(final StringBuilder buffer){
        String name = null;
        try {
            name = getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final String finalName = name;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TxtOperation.writeTxtFile(buffer.toString(), finalName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private static String getPath() throws IOException {
        int i=0;
        while (true) {
            String name=TimeUtil.toTime(new Date(),"yyyy-MM-dd")+"_"+i+".log";
            File file = TxtOperation.creatTxtFile(baseDir + name);
            double v = (double) file.length() / (1024 * 1024);
            if (v > MaxSize) {
               i++;
            }else {
                fileName=name;
                break;
            }
        }
        return baseDir+fileName;
    }

    public static void main(String[] args) {
        long start=System.nanoTime();
        for (int i = 0; i < 10; i++) {
//            System.out.println(DateUtil.now());
//            System.out.println(TimeUtil.getNowTime());
//            System.out.println(TimeUtil.toTime(new Date(System.currentTimeMillis())));
//            Log.debug(i);
            Log.Info(i);
//            System.out.println(i);
        }
        System.out.println("耗时(ms):"+Double.valueOf(System.nanoTime() -start)/1000000);
    }
}
