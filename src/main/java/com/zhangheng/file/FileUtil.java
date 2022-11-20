package com.zhangheng.file;

/**
 * 文件工具类
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-11-20 19:02
 */
public class FileUtil {
    /**
     * 文件字节转KB/MB/GB 保留两位小数四舍五入
     * @param size 字节长度
     * @return 文件大小
     */
    public static String getFileSizeString(Long size) {
        double length = Double.valueOf(String.valueOf(size));
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少意义
        if (length < 1024) {
            return length + "B";
        } else {
            length = length / 1024.0;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (length < 1024) {
            return Math.round(length * 100) / 100.0 + "KB";
        } else {
            length = length / 1024.0;
        }
        if (length < 1024) {
            //因为如果以 MB为单位的话，要保留最后1位小数，因此把此数乘以100之后再取余
            return Math.round(length * 100) / 100.0 + "MB";
        } else {
            //否则如果要以为单位的，先除于1024再作同样的处理 GB
            return Math.round(length / 1024 * 100) / 100.0 + "GB";
        }
    }
}
