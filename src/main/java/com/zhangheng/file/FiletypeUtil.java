package com.zhangheng.file;

import java.io.File;

/**
 * 文件类型帮助类
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-04 17:00
 */
public class FiletypeUtil {
    public static final String[][] MIME_MapTable={
            //{后缀名，    类型}
            //视频
            {".3gp",   "video"},
            {".m4u",   "video"},
            {".m4v",   "video"},
            {".mov",   "video"},
            {".mpe",   "video"},
            {".mpeg",  "video"},
            {".mpg",   "video"},
            {".mpg4",  "video"},
            {".mp4",   "video"},
            {".asf",   "video"},
            {".avi",   "video"},

            //音频
            {".m3u8",   "audio"},
            {".m4a",   "audio"},
            {".m4b",   "audio"},
            {".m4p",   "audio"},
            {".mp2",   "audio"},
            {".mp3",   "audio"},
            {".mpga",  "audio"},
            {".rmvb",  "audio"},
            {".aac",   "audio"},
            {".ogg",   "audio"},
            {".wav",   "audio"},
            {".wma",   "audio"},
            {".wmv",   "audio"},

            //图片
            {".gif",   "image"},
            {".bmp",   "image"},
            {".jpeg",  "image"},
            {".png",   "image"},
            {".jpg",   "image"},

            //文本
            {".txt",   "text"},
            {".c",     "text"},
            {".xml",   "text"},
            {".conf",  "text"},
            {".cpp",   "text"},
            {".doc",   "text"},
            {".pdf",   "text"},
            {".h",     "text"},
            {".ppt",   "text"},
            {".xls",   "text"},
            {".xlsx",  "text"},
            {".docx",  "text"},
            {".md",    "text"},
            {".prop",  "text"},
            {".htm",   "text"},
            {".html",  "text"},
            {".java",  "text"},
            {".js",    "text"},
            {".rc",    "text"},
            {".log",   "text"},
            {".sh",    "text"},
            {".css",    "text"},
            {".json",    "text"},

            //应用
            {".class",  "application"},
            {".apk",    "application"},
            {".bin",    "application"},
            {".exe",    "application"},
            {".gtar",   "application"},
            {".gz",     "application"},
            {".jar",    "application"},
            {".mpc",    "application"},
            {".msg",    "application"},
            {".pps",    "application"},
            {".rar",    "application"},
            {".rtf",    "application"},
            {".tar",    "application"},
            {".tgz",    "application"},
            {".wps",    "application"},
            {".z",      "application"},
            {".zip",    "application"},

            //未知类型
            {"",        "unknown"},
            {"",        "unknown"}
    };

    /**
     * 获取文件类型
     * @param file 文件
     * @return 文件类型
     */
    public static String getFileType(File file){
        return getFileType(file.getName());
    }
    /**
     * 获取文件类型
     * @param filename 文件全称（含后缀名）
     * @return 文件类型
     */
    public static String getFileType(String filename)
    {
        String type="unknown";
        String fName=filename;
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if(dotIndex < 0){
            return type;
        }
        /* 获取文件的后缀名 */
        String end=fName.substring(dotIndex).toLowerCase();
        if(end=="")return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for(int i=0;i<MIME_MapTable.length;i++){
            if(end.equals(MIME_MapTable[i][0])) {
                type = MIME_MapTable[i][1];
                break;
            }
        }
        return type;
    }


}