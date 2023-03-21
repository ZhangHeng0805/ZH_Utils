package com.zhangheng.file;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文本文件操作（创建，读写）
 *
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-21 14:25
 */
public class TxtOperation {

    //允许的创建的文件类型
//    private static final String[] fileType={"txt","log","html","js","css","xml","json","java"};

    /**
     * 读取txt文件
     *
     * @param file     文件
     * @param encoding 文件编码
     * @return 文本每行数据集合
     */
    public static List<String> readTxtFile(File file, String encoding) {
        List<String> list = new ArrayList<>();
        try {
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    list.add(lineTxt);
                }
                read.close();
            } else {
                throw new Exception("找不到<" + file.getPath() + ">该文件！cannot find file");
            }
        } catch (Exception e) {
            System.err.println("读取文件内容出错");
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 读取txt文件（默认编码）
     *
     * @param filePath 文件路径
     * @return 文本每行数据集合
     */
    public static List<String> readTxtFile(String filePath) {
        String encoding = CharsetUtil.defaultCharsetName();
        File file = new File(filePath);
        return readTxtFile(file, encoding);
    }

    /**
     * 读取txt文件
     *
     * @param filePath 文件路径
     * @param encoding 文件编码
     * @return 文本每行数据集合
     */
    public static List<String> readTxtFile(String filePath, String encoding) {
        File file = new File(filePath);
        return readTxtFile(file, encoding);
    }

    /**
     * 创建txt文本文件
     *
     * @param path 存储路径
     * @param name 文件名
     * @param type 文件类型（后缀）
     * @return 文本文件
     * @throws IOException
     */
    public static File creatTxtFile(String path, String name, String type) throws IOException {
        return creatTxtFile(path, name, type, true);
    }

    /**
     * 创建txt文本文件
     *
     * @param path        存储路径
     * @param name        文件名
     * @param type        文件类型（后缀）
     * @param isCheckType 是否检查type
     * @return 文本文件
     * @throws IOException
     */
    public static File creatTxtFile(String path, String name, String type, boolean isCheckType) throws IOException {
//        File flag = null;
        name = FileUtil.filterFileName(name);
        String filenameTemp = "";
        String fileType = FiletypeUtil.getFileType(name + "." + type);
        if (isCheckType) {
            if ("text".equals(fileType)) {
                filenameTemp = path + name + "." + type;
            } else {
                filenameTemp = path + name + ".txt";
            }
        } else {
            if (StrUtil.isBlank(type)) {
                filenameTemp = path + name;
            } else {
                filenameTemp = path + name + "." + type;
            }
        }
        File file = new File(filenameTemp);
        if (!file.exists()) {
            FileOperation.mkdirs(path);
            file.createNewFile();
        }
        return file;
    }

    /**
     * 创建txt文本文件
     * 如果文件不存在，则创建，并返回该文件；
     * 存在直接返回该文件
     *
     * @param Path 文件全路径
     * @return 文本文件
     */
    public static File creatTxtFile(String Path) throws IOException {
        File f = new File(Path);
        try {
            if (!f.exists()) {
                Path = Path.replace("\\", "/");
                String p = Path.substring(0, Path.lastIndexOf("/") + 1);
                String n = Path.substring(Path.lastIndexOf("/") + 1, Path.lastIndexOf("."));
                String t = Path.substring(Path.lastIndexOf(".") + 1);
                f = creatTxtFile(p, n, t);
            }
        } catch (Exception e) {
            throw e;
        }
        return f;
    }


    /**
     * 写入txt文件
     *
     * @param newStr         写入字符
     * @param file           写入文件
     * @param isContinuation 是否续写(true追加/false覆盖)
     * @return 是否写入成功
     * @throws IOException
     */
    public static boolean writeTxtFile(String newStr, File file, String charsetName, boolean isContinuation) throws IOException {
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
            isr = new InputStreamReader(fis, charsetName);
            br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();

            if (isContinuation) {
                // 保存该文件原有的内容
                for (int j = 1; (temp = br.readLine()) != null; j++) {
                    buf = buf.append(temp);
                    // System.getProperty("line.separator")
                    // 行与行之间的分隔符 相当于“\n”
                    buf = buf.append(System.getProperty("line.separator"));
                }
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
     * 写入txt文件(追加)
     *
     * @param newStr 写入的字符串
     * @param path   文件路径
     * @return 是否写入成功
     */
    public static boolean writeTxtFile(String newStr, String path) throws IOException {
        boolean f = false;
        try {
            f = writeTxtFile(newStr, creatTxtFile(path), CharsetUtil.defaultCharsetName(), true);
        } catch (Exception e) {
            throw e;
        }
        return f;
    }

    /**
     * 写入txt文件
     *
     * @param newStr         写入的字符串
     * @param path           文件路径
     * @param isContinuation 是否续写(true追加/false覆盖)
     * @return 是否写入成功
     */
    public static boolean writeTxtFile(String newStr, String path, boolean isContinuation) throws IOException {
        boolean f = false;
        try {
            f = writeTxtFile(newStr, creatTxtFile(path), CharsetUtil.defaultCharsetName(), isContinuation);
        } catch (Exception e) {
            throw e;
        }
        return f;
    }

    public static boolean writeTxtFile(String newStr, String path, String charsetName, boolean isContinuation) throws IOException {
        boolean f = false;
        try {
            f = writeTxtFile(newStr, creatTxtFile(path), charsetName, isContinuation);
        } catch (Exception e) {
            throw e;
        }
        return f;
    }
}
