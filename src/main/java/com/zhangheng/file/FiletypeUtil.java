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

            {".gif",   "image"},
            {".bmp",   "image"},
            {".jpeg",  "image"},
            {".png",   "image"},
            {".jpg",   "image"},

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
            {".wsdl",  "text"},
            {".xsd",   "text"},
            {".sql",   "text"},

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

            {"",        "unknown"}
    };

    public static final String[][] Content_Type={
            {".txt",  "text/plain"},
            {".htm",  "text/html"},
            {".html",  "text/html"},
            {".shtml",  "text/html"},
            {".jsp",  "text/html"},
            {".xml",   "text/xml"},
            {".wsdl",  "text/xml"},
            {".xsd",  "text/xml"},
            {".xsl",  "text/xml"},
            {".css",  "text/css"},
            {".vcf",  "text/x-vcard"},

            {".json",  "application/json"},
            {".pdf",  "application/pdf"},
            {".doc",  "application/msword"},
            {".docx",  "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls",  "application/vnd.ms-excel"},
            {".xlsx",  "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".ppt",  "application/application/vnd.ms-powerpoint"},
            {".pptx",  "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".apk",  "application/vnd.android.package-archive"},
            {".js",  "application/x-javascript"},
            {".gz",  "application/x-gzip"},
            {".gzip",  "application/x-gzip"},
            {".zip",  "application/zip"},
            {".7zip",  "application/zip"},
            {".rar",  "application/rar"},
            {".tar",  "application/x-tar"},
            {".tgz",  "application/x-tar"},
            {".rtf",  "application/rtf"},
            {".wps",  "application/kswps"},
            {".psd",  "application/x-photoshop"},
            {".swf",  "application/x-shockwave-flash"},
            {".xht",  "application/xhtml+xml"},
            {".xhtml",  "application/xhtml+xml"},
            {".php",  "application/x-httpd-php"},
            {".php3",  "application/x-httpd-php"},
            {".php4",  "application/x-httpd-php"},
            {".phtml",  "application/x-httpd-php"},
            {".jar",  "application/java-archive"},
            {".exe",  "application/octet-stream"},

            {".mp4",  "video/mp4"},
            {".avi",  "video/avi"},
            {".mpg",  "video/mpeg"},
            {".mpe",  "video/mpeg"},
            {".mpeg",  "video/mpeg"},
            {".qt",  "video/quicktime"},
            {".mov",  "video/quicktime"},
            {".m4v",  "video/x-m4v"},
            {".wmv",  "video/x-ms-wmv"},
            {".avi",  "video/x-msvideo"},
            {".webm",  "video/webm"},
            {".flv",  "video/x-flv"},

            {".mp3",  "audio/mp3"},
            {".mpeg",  "audio/mpeg"},
            {".mid",  "audio/midi"},
            {".midi",  "audio/midi"},
            {".wav",  "audio/x-wav"},
            {".m3u",  "audio/x-mpegurl"},
            {".m4a",  "audio/x-m4a"},
            {".ogg",  "audio/ogg"},
            {".ra",  "audio/x-realaudio"},
            {".ra",  "audio/x-realaudio"},

            {".png",  "image/png"},
            {".jpg",  "image/jpeg"},
            {".jpeg",  "image/jpeg"},
            {".gif",  "image/gif"},
            {".tif",  "image/tiff"},
            {".tiff",  "image/tiff"},
            {".bmp",  "image/bmp"},
            {".svg",  "image/svg+xml"},
            {".svgz",  "image/svg+xml"},
            {".webp",  "image/webp"},


            //二进制流，不知道下载文件类型
            {"",        "application/octet-stream"}
    };

    /**
     * 获取文件类型
     * @param file 文件
     * @return
     */
    public static String getFileType(File file){
        return getFileType(file.getName());
    }

    public static String getFileType(String filename){
        return getFileType(filename,MIME_MapTable,"unknown");
    }

    /**
     * 获取文件的ContentType
     * @param filename 文件全称（含后缀名）
     * @return
     */
    public static String getFileContentType(String filename){
        return getFileType(filename,Content_Type,"application/octet-stream");
    }
    /**
     * 获取文件类型
     * @param filename 文件全称（含后缀名）
     * @param MIME_MapTable 类型映射二维数值
     * @return
     */
    public static String getFileType(String filename,String[][] MIME_MapTable,String defaultType)
    {
        String type=defaultType;
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
            if(end.equalsIgnoreCase(MIME_MapTable[i][0])) {
                type = MIME_MapTable[i][1];
                break;
            }
        }
        return type;
    }

}
