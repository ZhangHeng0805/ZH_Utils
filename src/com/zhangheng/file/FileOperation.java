package com.zhangheng.file;

import com.zhangheng.log.printLog.Log;
import com.zhangheng.util.FolderFileScannerUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 文件操作
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-04-20 18:45
 */
public class FileOperation {
    /**
     * 自动创建多层目录
     * @param destPath
     */
    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }
    /**
     * 删除文件
     * 同时清空空文件夹
     * @param path 文件路径
     * @return is 否删除成功
     */
    public static boolean deleteFile(String path) throws Exception {
        File file = new File(path);
        if (file.exists()){
            boolean delete = file.delete();
            if (delete) {
                Log.info("文件删除成功："+path);
                String[] split = path.split("/");
                for (int i=split.length-2;i>=0;i--) {
                    String dirPath="";
                    for (int n=0;n<=i;n++) {
                        dirPath+= split[n]+"/";
                    }
                    if (dirPath==""){
                        dirPath=split[i];
                    }
                    File dir = new File(dirPath);
                    //删除文件后判断文件夹内是否还有文件
                    ArrayList<Object> list = FolderFileScannerUtil.scanFilesWithRecursion(dirPath);
                    if (list.size() <= 0) {
                        boolean b = deleteDir(dirPath);
                        if (b) {
                            Log.info("空文件夹"+dirPath+"清除成功!");
                        } else {
                            Log.info("空文件夹"+dirPath+"清除失败!");
                        }
                    } else {
                        boolean flag = true;
                        for (Object f : list) {
                            if (new File(f.toString()).isFile()) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            boolean b = deleteDir(dirPath);
                            if (b) {
                                Log.info("空文件夹"+dirPath+"清除成功!");
                            } else {
                                Log.info("空文件夹"+dirPath+"清除失败!");
                            }
                        }
                    }
                }
                return true;
            }else {
                Log.error("文件删除失败："+path);
            }
        }else {
            Log.error("删除文件的不存在："+path);
        }
        return false;
    }
    /**
     * 递归删除文件夹
     * @param path 文件夹路径
     * @return
     */
    public static boolean deleteDir(String path){
        File file = new File(path);
        if (file.isDirectory()) {
            try {
                FileUtils.deleteDirectory(file);
                return true;
            } catch (IOException e) {
                Log.error(e.getMessage());
            }
        }else {
            Log.warn("删除文件夹失败:"+file.toString());
        }
        return false;
    }

    /**
     * 过滤文件名中的非法字符
     * @param fileName
     * @return
     */
    public static String filterFileName(String fileName){
        String name=null;
        fileName=fileName.replace("\\","/");
        String[] split = fileName.split("/");
        if (split.length>1){
            name= fileName.substring(0,fileName.lastIndexOf("/")+1)+split[split.length-1].replace("\\","").replace("/","")
                    .replace(":","").replace("*","")
                    .replace("?","").replace("\"","")
                    .replace("<","").replace(">","")
                    .replace("|","");
        }else {
            name= split[0].replace("\\","").replace("/","")
                    .replace(":","").replace("*","")
                    .replace("?","").replace("\"","")
                    .replace("<","").replace(">","")
                    .replace("|","");
        }
        return name;
    }
}