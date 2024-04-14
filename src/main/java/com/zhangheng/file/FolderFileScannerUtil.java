package com.zhangheng.file;

import com.zhangheng.bean.MyException;
import com.zhangheng.util.TimeUtil;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 文件夹扫描工具类
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-04 17:00
 */
public class FolderFileScannerUtil {

//    private static List<String> scanFiles = new LinkedList<>();

    /**linkedList实现**/
    private static LinkedList<File> queueFiles = new LinkedList<File>();

    /**
     * 递归扫描指定文件夹下面的指定文件
     * @param folderPath 需要进行文件扫描的文件夹路径
     * @return ArrayList<Object> 文件夹中所有文件的绝对路径
     * @throws Exception
     */
    @Deprecated
    public static List<String> scanFilesWithRecursion(String folderPath) throws Exception {
        List<String> scanFiles = new LinkedList<>();
        List<String> dirctorys = new LinkedList<>();
        File directory = new File(folderPath);
        if (!directory.exists()){
            throw new MyException("路径错误",'"' + folderPath + '"' + "(文件夹路径不存在) input path is not a Directory , please input the right path of the Directory. ^_^...^_^");
        }
        if(!directory.isDirectory()){
            throw new MyException("文件类型错误",'"' + folderPath + '"' + "(不是文件夹路径) input path is not a Directory , please input the right path of the Directory. ^_^...^_^");
        } else {
            File[] filelist = directory.listFiles();
            if (filelist==null)
                return scanFiles;
            for(int i = 0; i < filelist.length; i ++){
                //如果当前是文件夹，进入递归扫描文件夹
                if(filelist[i].isDirectory()){
                    dirctorys.add(filelist[i].getAbsolutePath());
                    //递归扫描下面的文件夹
                    scanFilesWithRecursion(filelist[i].getAbsolutePath());
                }
                //非文件夹
                else{
                    scanFiles.add(filelist[i].getCanonicalPath());
                }
            }
        }
        return scanFiles;
    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
//        List<Map<String, Object>> list = scanAllFiles(FileUtil.getUserHomePath());
//        System.out.println(list.size());
        List<String> strings = scanFilesWithNoRecursion(FileUtil.getUserHomePath());
        System.out.println(strings.size());
        long end = System.currentTimeMillis();
        System.out.println(TimeUtil.formatMSToCn((int) (end-start)));
    }


    /**
     * 扫描路径中的所有文件（包含子目录中的文件）
     * @param folderPath
     * @return [{path:文件路径,size:文件大小}]
     * @throws Exception
     */
    public static List<Map<String,Object>> scanAllFiles(String folderPath) throws Exception {
        List<Map<String,Object>> scanFiles = new LinkedList<>();
        File directory = new File(folderPath);
        if(!directory.isDirectory()){
            throw new MyException("文件类型错误",'"' + folderPath + '"' + "(不是文件夹路径) input path is not a Directory , please input the right path of the Directory. ^_^...^_^");
        }
        else{
            //首先将第一层目录扫描一遍
            File[] files = directory.listFiles();
            //遍历扫出的文件数组，如果是文件夹，将其放入到linkedList中稍后处理
            for(int i = 0; i < files.length; i ++){
                if(files[i].isDirectory()){
                    queueFiles.add(files[i]);
                }else{
                    //暂时将文件名放入scanFiles中
                    Map<String,Object> map=new LinkedHashMap<>();
                    map.put("path",files[i].getAbsolutePath());
                    map.put("size",files[i].length());
                    scanFiles.add(map);
                }
            }
            //如果linkedList非空遍历linkedList
            while(!queueFiles.isEmpty()){
                //移出linkedList中的第一个
                File headDirectory = queueFiles.removeFirst();
                File[] currentFiles = headDirectory.listFiles();
                if (currentFiles==null)
                    continue;
                for(int j = 0; j < currentFiles.length; j ++){
                    if(currentFiles[j].isDirectory()){
                        //如果仍然是文件夹，将其放入linkedList中
                        queueFiles.add(currentFiles[j]);
                    }else{
                        Map<String,Object> map=new LinkedHashMap<>();
                        map.put("path",currentFiles[j].getAbsolutePath());
                        map.put("size",currentFiles[j].length());
                        scanFiles.add(map);
                    }
                }
            }
        }

        return scanFiles;
    }
    /**
     *
     * 非递归方式扫描指定文件夹下面的所有文件(速度更快)
     * @param folderPath 需要进行文件扫描的文件夹路径
     * @return ArrayList<Object> 文件夹中所有文件的绝对路径
     * @throws Exception
     */
    public static List<String> scanFilesWithNoRecursion(String folderPath) throws Exception {
        List<String> scanFiles = new LinkedList<>();
        File directory = new File(folderPath);
        if (!directory.exists()){
            throw new MyException("路径错误",'"' + folderPath + '"' + "(文件夹路径不存在) input path is not a Directory , please input the right path of the Directory. ^_^...^_^");
        }
        if(!directory.isDirectory()){
            throw new MyException("文件类型错误",'"' + folderPath + '"' + "(不是文件夹路径) input path is not a Directory , please input the right path of the Directory. ^_^...^_^");
        }
        else{
            //首先将第一层目录扫描一遍
            File[] files = directory.listFiles();
            //遍历扫出的文件数组，如果是文件夹，将其放入到linkedList中稍后处理
            for(int i = 0; i < files.length; i ++){
                if(files[i].isDirectory()){
                    queueFiles.add(files[i]);
                }else{
                    //暂时将文件名放入scanFiles中
                    scanFiles.add(files[i].getAbsolutePath());
                }
            }
            //如果linkedList非空遍历linkedList
            while(!queueFiles.isEmpty()){
                //移出linkedList中的第一个
                File headDirectory = queueFiles.removeFirst();
                File[] currentFiles = headDirectory.listFiles();
                if (currentFiles==null)
                    continue;
                for(int j = 0; j < currentFiles.length; j ++){
                    if(currentFiles[j].isDirectory()){
                        //如果仍然是文件夹，将其放入linkedList中
                        queueFiles.add(currentFiles[j]);
                    }else{
                        scanFiles.add(currentFiles[j].getAbsolutePath());
                    }
                }
            }
        }

        return scanFiles;
    }

}
