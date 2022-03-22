package com.zhangheng.log.printLog;

import com.zhangheng.util.TimeUtil;
import com.zhangheng.file.TxtOperation;

import java.io.File;
import java.util.Date;

/**
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-21 16:48
 */
class Print {
    //单个log文件的最大值Mb
    private static final double MaxSize=0.01;
    private static final String baseDir="./log日志/";
    private static String fileName;

    static void print(String str){
        String name = getPath();
        TxtOperation.writeTxtFile(str, name);
    }
    private static String getPath(){
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