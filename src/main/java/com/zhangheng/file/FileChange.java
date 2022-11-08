package com.zhangheng.file;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 文件转换
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-08-02 14:57
 */
public class FileChange {

    /**
     * BufferedImage 编码转换为 base64
     * @param bufferedImage 图片的BufferedImage
     * @return base64数据
     */
    public static String BufferedImageToBase64(BufferedImage bufferedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
        try {
            ImageIO.write(bufferedImage, "png", baos);//写入流中
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        byte[] bytes = baos.toByteArray();//转换成字节
//        BASE64Encoder encoder = new BASE64Encoder();
//        String png_base64 = encoder.encodeBuffer(bytes).trim();//转换成base64串
        String png_base64=Base64Encoder.encode(bytes).trim();
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
//        System.out.println("值为：" + "data:image/jpg;base64," + png_base64);
        return "data:image/jpg;base64," + png_base64;
    }

    /**
     * base64 编码转换为 BufferedImage
     * @param base64 图片的base64
     * @return BufferedImage数据
     */
    public  static BufferedImage base64ToBufferedImage(String base64) {
//        BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        try {
//            byte[] bytes1 = decoder.decodeBuffer(base64);
            byte[] bytes1 = Base64Decoder.decode(base64);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            return ImageIO.read(bais);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
