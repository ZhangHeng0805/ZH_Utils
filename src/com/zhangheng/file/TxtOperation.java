package com.zhangheng.file;

import com.zhangheng.Main;
import com.zhangheng.log.printLog.Log;
import com.zhangheng.util.ArrayUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * txt文本文件操作
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-21 14:25
 */
public class TxtOperation {

    private static final String[] fileType={"txt","log"};
    /**
     * 读取txt文件
     * @param file 文件
     * @param encoding 文件编码
     * @return
     */
    public static List<String> readTxtFile(File file,String encoding){
        List<String> list=new ArrayList<String>();
        try {
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    list.add(lineTxt);
                }
                read.close();
            }else{
                throw new Exception("找不到<"+file.getPath()+">该文件！cannot find file");
            }
        } catch (Exception e) {
            System.err.println("读取文件内容出错");
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 读取txt文件（GBK编码）
     * @param filePath 文件路径
     * @return
     */
    public static List<String> readTxtFile(String filePath){
        String encoding="GBK";
        File file=new File(filePath);
        return readTxtFile(file,encoding);
    }

    /**
     * 读取txt文件
     * @param filePath 文件路径
     * @param encoding 文件编码
     * @return
     */
    public static List<String> readTxtFile(String filePath,String encoding){
        File file=new File(filePath);
        return readTxtFile(file,encoding);
    }

    /**
     * 创建txt文本文件
     * @param path 存储路径
     * @param name 文件名
     * @return
     * @throws IOException
     */
    public static File creatTxtFile(String path,String name,String type) throws IOException {
        File flag = null;
        name=name.replace(".","")
                .replace("/","")
                .replace("\\","");
        String filenameTemp="";
        if (ArrayUtil.exist(fileType,type)){
            filenameTemp= path + name + "."+type;
        }else {
            filenameTemp= path + name + ".txt";
        }
        File filename = new File(filenameTemp);
        flag=filename;
        if (!filename.exists()) {
            mkdirs(path);
            filename.createNewFile();
        }
        return flag;
    }

    /**
     * 创建txt文本文件
     * 如果文件不存在，则创建，并返回该文件；
     * 存在直接返回该文件
     * @param Path 文件全路径
     * @return
     */
    public static File creatTxtFile(String Path){
        File f=new File(Path);
        try {
            if (!f.exists()) {
                Path = Path.replace("\\", "/");
                String p = Path.substring(0, Path.lastIndexOf("/") + 1);
                String n = Path.substring(Path.lastIndexOf("/") + 1, Path.lastIndexOf("."));
                String t=Path.substring(Path.lastIndexOf(".")+1);
                f = creatTxtFile(p, n, t);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return f;
    }
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
     * 写入txt文件
     * @param newStr
     * @param file
     * @return
     * @throws IOException
     */
    public static boolean writeTxtFile(String newStr,File file) throws IOException {
        // 先读取原有文件内容，然后进行写入操作
        boolean flag = false;
        String filein = newStr + "\r\n";
        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            // 将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();

            // 保存该文件原有的内容
            for (int j = 1; (temp = br.readLine()) != null; j++) {
                buf = buf.append(temp);
                // System.getProperty("line.separator")
                // 行与行之间的分隔符 相当于“\n”
                buf = buf.append(System.getProperty("line.separator"));
            }
            buf.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            flag = true;
        } catch (IOException e1) {
            throw e1;
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return flag;
    }

    /**
     *
     * @param newStr 写入的字符串
     * @param path 文件路径
     * @return
     */
    public static boolean writeTxtFile(String newStr,String path){
        boolean f=false;
        try {
            f = writeTxtFile(newStr, creatTxtFile(path));
        }catch (Exception e){
            e.printStackTrace();
        }
        return f;
    }

    public static void main(String[] args) throws IOException {
        String path="./res/all-hanzi.txt";
        String path1="D:\\卓面\\账号秘钥.txt";
        String path2="D:\\卓面\\测试.log";
//        boolean b = writeTxtFile("张恒666", path2);
//        System.out.println(creatTxtFile(path).length()/1024);
//        List<String> list = readTxtFile(creatTxtFile(path2),"UTF-8");
//        for (String s : list) {
//            System.out.println(s);
//        }
//        System.out.println(list.size());
        String t=path2.substring(path2.lastIndexOf(".")+1);
        Log.Info(t);
    }
}
