package com.zhangheng.log.printLog;

import com.zhangheng.util.TimeUtil;
import com.zhangheng.file.TxtOperation;

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
    private static final String baseDir="./log日志/";
    private static String fileName;

    static void print(final StringBuffer buffer){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String name = null;
                try {
                    name = getPath();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    TxtOperation.writeTxtFile(buffer.toString(), name);
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

}
