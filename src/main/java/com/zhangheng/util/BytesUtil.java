package com.zhangheng.util;

import java.io.*;

public class BytesUtil {
    /**
     * int 转 byte 数组
     *
     * @param val int 值
     * @return byte 数组
     */
    public static byte[] intToBytes(int val) {

        return new byte[]{
                (byte) (val),
                (byte) (val >> 8),
                (byte) (val >> 16),
                (byte) (val >> 24),
        };
    }


    /**
     * 小文件转字节
     * 大文件有内存溢出的风险
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] fileToBytes(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        outputStream.close();
        return outputStream.toByteArray();
    }

    /**
     * 字节转int
     *
     * @param bytes
     * @return
     */
    public static int bytesToInt(byte[] bytes) {
        return bytesToInt(bytes, 0);
    }

    /**
     * 字节转int
     *
     * @param buf    字节数组
     * @param offset 开始位置
     * @return
     */
    public static int bytesToInt(byte[] buf, int offset) {
        return buf[offset] & 0xff
                | (buf[offset + 1] & 0xff) << 8
                | (buf[offset + 2] & 0xff) << 16
                | (buf[offset + 3] & 0xff) << 24;
    }


    /**
     * long 转字节数组
     *
     * @param values
     * @return
     */
    public static byte[] longToBytes(long values) {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; i++) {
            int offset = 64 - (i + 1) * 8;
            buffer[i] = (byte) ((values >> offset) & 0xff);
        }
        return buffer;
    }

    /**
     * 字节数组 转 long
     *
     * @param buffer
     * @return
     */
    public static long bytesToLong(byte[] buffer) {
        return bytesToLong(buffer, 0);
    }

    /**
     * 字节数组 转 long
     *
     * @param buffer
     * @return
     */
    public static long bytesToLong(byte[] buffer, int offset) {
        long values = 0;
        for (int i = 0; i < 8; i++) {
            values <<= 8;
            values |= (buffer[offset + i] & 0xff);
        }
        return values;
    }

    /**
     * 将对象-->byte[]
     *
     * @param object
     * @return
     */
    public static byte[] objectToBytes(Object object) throws IOException {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                oos.close();
                baos.close();
            } catch (IOException e) {
                throw e;
            }
        }
    }


    /**
     * 将byte[] -->Object
     *
     * @param bytes
     * @return
     */
    public static <T> Object bytesToObject(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
           throw e;
        } finally {
            try {
                bais.close();
            } catch (IOException e) {
                throw e;
            }
        }
    }
}
