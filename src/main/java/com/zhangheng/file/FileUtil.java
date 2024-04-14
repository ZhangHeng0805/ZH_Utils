package com.zhangheng.file;

import java.util.regex.Pattern;

/**
 * 文件工具类
 *
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-11-20 19:02
 */
public class FileUtil extends cn.hutool.core.io.FileUtil {
    /**
     * 文件字节转B/KB/MB/GB/TB 保留两位小数四舍五入
     * 已废弃，替代方法fileSizeStr()
     * @param size 字节长度
     * @return 文件大小
     */
    @Deprecated
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
//            return Math.round(length / 1024 * 100) / 100.0 + "GB";
            length = length / 1024.0;
        }
        if (length < 1024) {
            return Math.round(length * 100) / 100.0 + "GB";
        } else {
            length = length / 1024.0;
        }
        if (length < 1024) {
            return Math.round(length * 100) / 100.0 + "GB";
        } else {
            return Math.round(length / 1024 * 100) / 100.0 + "TB";
        }
    }

    /**
     * 文件大小格式化
     * @param size 文件字节
     * @return 格式化字符串
     */
    public static String fileSizeStr(Long size) {
        if (size == null) {
            return "0";
        }
        double length = Double.valueOf(String.valueOf(size));
        String[] unit = new String[]{"B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"};
        int i = 0;
        while (true) {
            if (length < 1024) {
                if (i == 0)
                    return length +" "+ unit[i];
                else
                    return Math.round(length * 100) / 100.0+" "+unit[i];
            } else {
                length = length / 1024.0;
            }
            i++;
        }
    }
    /**
     * 过滤文件名中的非法字符（Windows）
     * @param fileName 文件名/文件路径
     * @return 过滤后的文件名
     */
    public static String filterFileName(String fileName){
        Pattern ILLEGAL_CHARACTERS_PATTERN = Pattern.compile("[\\\\/:*?\"<>|]");
        return ILLEGAL_CHARACTERS_PATTERN.matcher(fileName).replaceAll("");
//        String[] illegal={"\\","/",":","*","?","\"","<",">","|"};
//        StringBuilder name=new StringBuilder();
//        fileName=fileName.replace("\\","/");
//        String[] split = fileName.split("/");
//        if (split.length>1){
//            name.append(fileName.substring(0,fileName.lastIndexOf("/")+1)+split[split.length-1]);
//        }else {
//            name.append(split[0]);
//        }
//
//        for (String s : illegal) {
//            name.replace(0,name.length(), StrUtil.removeAll(name,s));
//        }
//        return name.toString();
    }

    /**
     * 获取文件名（不带后缀）
     * @param fileName
     * @return
     */
    public static String getMainName(String fileName){
        String[] p={"tar.bz2", "tar.Z", "tar.gz", "tar.xz"};
        for (String s : p) {
            if (fileName.indexOf(s)>0){
                return fileName.replace("."+s,"");
            }
        }
        return fileName.substring(0,fileName.lastIndexOf("."));
    }
}
