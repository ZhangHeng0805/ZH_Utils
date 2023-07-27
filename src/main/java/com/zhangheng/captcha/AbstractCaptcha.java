package com.zhangheng.captcha;

import cn.hutool.captcha.ICaptcha;
import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.URLUtil;
import com.zhangheng.file.FileUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author: ZhangHeng
 * @email: zhangheng_0805@163.com
 * @date: 2023-07-27 10:27
 * @version: 1.0
 * @description: 抽象验证码<br>
 * 抽象验证码实现了验证码字符串的生成、验证，验证码图片的写出<br>
 * 实现类通过实现{@link #createImage(String)} 方法生成图片对象
 */
public abstract class AbstractCaptcha implements ICaptcha {
    private static final long serialVersionUID = 3180820918087507254L;

    /**
     * 图片的宽度
     */
    protected int width;
    /**
     * 图片的高度
     */
    protected int height;
    /**
     * 验证码干扰元素个数
     */
    protected int interfereCount;
    /**
     * 字体
     */
    protected Font font;
    /**
     * 验证码
     */
    protected String code;
    /**
     * 验证码图片
     */
    protected byte[] imageBytes;
    /**
     * 验证码生成器
     */
    protected CodeGenerator generator;
    /**
     * 背景色
     */
    protected Color background;
    /**
     * 文字透明度
     */
    protected AlphaComposite textAlpha;

    /**
     * 构造，使用随机验证码生成器生成验证码
     *
     * @param width          图片宽
     * @param height         图片高
     * @param codeCount      字符个数
     * @param interfereCount 验证码干扰元素个数
     */
    public AbstractCaptcha(int width, int height, int codeCount, int interfereCount) {
        this(width, height, new RandomGenerator(codeCount), interfereCount);
    }

    /**
     * 构造
     *
     * @param width          图片宽
     * @param height         图片高
     * @param generator      验证码生成器
     * @param interfereCount 验证码干扰元素个数
     */
    public AbstractCaptcha(int width, int height, CodeGenerator generator, int interfereCount) {
        this.width = width;
        this.height = height;
        this.generator = generator;
        this.interfereCount = interfereCount;
        // 字体高度设为验证码高度-2，留边距
        this.font = new Font(Font.SANS_SERIF, Font.ITALIC, (int) (this.height * 0.7));
    }

    @Override
    public void createCode() {
        generateCode();

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImgUtil.writePng(createImage(this.code), out);
        this.imageBytes = out.toByteArray();
    }

    /**
     * 生成验证码字符串
     *
     * @since 3.3.0
     */
    protected void generateCode() {
        this.code = generator.generate();
    }

    /**
     * 根据生成的code创建验证码图片
     *
     * @param code 验证码
     * @return Image
     */
    protected abstract Image createImage(String code);

    @Override
    public String getCode() {
        if (null == this.code) {
            createCode();
        }
        return this.code;
    }

    @Override
    public boolean verify(String userInputCode) {
        return this.generator.verify(getCode(), userInputCode);
    }

    /**
     * 验证码写出到文件
     *
     * @param path 文件路径
     * @throws IORuntimeException IO异常
     */
    public void write(String path) throws IORuntimeException {
        this.write(FileUtil.touch(path));
    }

    /**
     * 验证码写出到文件
     *
     * @param file 文件
     * @throws IORuntimeException IO异常
     */
    public void write(File file) throws IORuntimeException {
        try (OutputStream out = FileUtil.getOutputStream(file)) {
            this.write(out);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    @Override
    public void write(OutputStream out) {
        IoUtil.write(out, false, getImageBytes());
    }

    /**
     * 获取图形验证码图片bytes
     *
     * @return 图形验证码图片bytes
     * @since 4.5.17
     */
    public byte[] getImageBytes() {
        if (null == this.imageBytes) {
            createCode();
        }
        return this.imageBytes;
    }

    /**
     * 获取验证码图
     *
     * @return 验证码图
     */
    public BufferedImage getImage() {
        return ImgUtil.read(IoUtil.toStream(getImageBytes()));
    }

    /**
     * 获得图片的Base64形式
     *
     * @return 图片的Base64
     * @since 3.3.0
     */
    public String getImageBase64() {
        return Base64.encode(getImageBytes());
    }

    /**
     * 获取图片带文件格式的 Base64
     *
     * @return 图片带文件格式的 Base64
     * @since 5.3.11
     */
    public String getImageBase64Data(){
        return getImageBase64Data("image/png");
    }
    /**
     * 获取图片带文件格式的 Base64
     * @param mimeType 图片类型
     * @return 图片带文件格式的 Base64
     * @since 5.3.11
     */
    public String getImageBase64Data(String mimeType){
        return URLUtil.getDataUriBase64(mimeType, getImageBase64());
    }
    /**
     * 自定义字体
     *
     * @param font 字体
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * 获取验证码生成器
     *
     * @return 验证码生成器
     */
    public CodeGenerator getGenerator() {
        return generator;
    }

    /**
     * 设置验证码生成器
     *
     * @param generator 验证码生成器
     */
    public void setGenerator(CodeGenerator generator) {
        this.generator = generator;
    }

    /**
     * 设置背景色
     *
     * @param background 背景色
     * @since 4.1.22
     */
    public void setBackground(Color background) {
        this.background = background;
    }

    /**
     * 设置文字透明度
     *
     * @param textAlpha 文字透明度，取值0~1，1表示不透明
     * @since 4.5.17
     */
    public void setTextAlpha(float textAlpha) {
        this.textAlpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, textAlpha);
    }
}
