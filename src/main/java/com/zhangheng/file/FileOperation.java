package com.zhangheng.file;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import com.zhangheng.log.printLog.Log;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * 文件(夹)操作
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-04-20 18:45
 */
public class FileOperation {
    /**
     * 自动创建多层目录
     * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
     * @param destPath 文件夹路径
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
     * 如果删除后文件夹为空，同时删除文件夹
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
                    List<String> list = FolderFileScannerUtil.scanFilesWithRecursion(dirPath);
                    if (list.size() <= 0) {
                        boolean b = deleteDir(dirPath);
                        if (b) {
                            Log.info("空文件夹"+dirPath+"清除成功!");
                        } else {
                            Log.info("空文件夹"+dirPath+"清除失败!");
                        }
                    } else {
                        boolean flag = true;
                        for (String f : list) {
                            if (new File(f).isFile()) {
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
            throw new Exception("删除文件的不存在："+path);
        }
        return false;
    }
    /**
     * 递归删除文件夹
     * @param path 文件夹路径
     * @return 是否删除成功
     */
    public static boolean deleteDir(String path) throws Exception{
        File file = new File(path);
        if (file.isDirectory()) {
            try {
                FileUtils.deleteDirectory(file);
                return true;
            } catch (IOException e) {
                throw e;
            }
        }else {
            throw new Exception("删除文件夹失败:"+file.toString());
        }
    }
    /**
     * BufferedImage 编码转换为 base64
     * @param bufferedImage 图片的BufferedImage
     * @return base64数据
     */
    public static String bufferedImageToBase64(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
        try {
            ImageIO.write(bufferedImage, "png", baos);//写入流中
        } catch (IOException e) {
            throw e;
        }finally {
            try {
                baos.close();
            } catch (IOException e) {
                throw e;
            }
        }
        byte[] bytes = baos.toByteArray();//转换成字节
//        BASE64Encoder encoder = new BASE64Encoder();
//        String png_base64 = encoder.encodeBuffer(bytes).trim();//转换成base64串
        String png_base64= Base64Encoder.encode(bytes).trim();
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
//        System.out.println("值为：" + "data:image/jpg;base64," + png_base64);
        return "data:image/jpg;base64," + png_base64;
    }

    /**
     * base64 编码转换为 BufferedImage
     * @param base64 图片的base64
     * @return BufferedImage数据
     */
    public static BufferedImage base64ToBufferedImage(String base64) throws IOException {
//        BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        try {
//            byte[] bytes1 = decoder.decodeBuffer(base64);
            byte[] bytes1 = Base64Decoder.decode(base64);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            return ImageIO.read(bais);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 文件转换byte数组
     * @param file 文件
     * @return byte[]
     */
    public static byte[] fileToBytes(File file) throws IOException {
        FileInputStream is = null;
        byte[] fileBytes=null;
        try {
            is = new FileInputStream(file);
            long length = file.length();
            fileBytes= new byte[(int) length];
            is.read(fileBytes);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (is!=null) {
                    is.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
        return fileBytes;
    }

    /**
     * byte数组转换文件
     * @param bytes byte数组
     * @param filePath 文件路径
     * @param fileName 文件名称(带后缀)
     * @return
     */
    public static File bytesToFile(byte[] bytes, String filePath, String fileName) throws IOException {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            file = new File(filePath + fileName);
            if (!file.getParentFile().exists()){
                //文件夹不存在 生成
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            throw e;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    throw e;
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
        return file;
    }

}
