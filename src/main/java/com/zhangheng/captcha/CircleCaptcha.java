package com.zhangheng.captcha;

import cn.hutool.core.img.GraphicsUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.ObjectUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author: ZhangHeng
 * @email: zhangheng_0805@163.com
 * @date: 2023-07-27 10:33
 * @version: 1.0
 * @description: 圆圈干扰验证码
 */
public class CircleCaptcha extends AbstractCaptcha {
    private static final long serialVersionUID = -7096627300356535494L;

    /**
     * 构造
     *
     * @param width 图片宽
     * @param height 图片高
     */
    public CircleCaptcha(int width, int height) {
        this(width, height, 5);
    }

    /**
     * 构造
     *
     * @param width 图片宽
     * @param height 图片高
     * @param codeCount 字符个数
     */
    public CircleCaptcha(int width, int height, int codeCount) {
        this(width, height, codeCount, 30);
    }

    /**
     * 构造
     *
     * @param width 图片宽
     * @param height 图片高
     * @param codeCount 字符个数
     * @param interfereCount 验证码干扰元素个数
     */
    public CircleCaptcha(int width, int height, int codeCount, int interfereCount) {
        super(width, height, codeCount, interfereCount);
        // 字体高度设为验证码高度-2，留边距
//        this.font = new Font(Font.SERIF, Font.ITALIC, (int) (this.height * 0.7));
    }

    @Override
    public Image createImage(String code) {
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D g = ImgUtil.createGraphics(image, ObjectUtil.defaultIfNull(this.background, Color.WHITE));
        // 画字符串
        drawString(g, code);
//        shear(g,width,height,ObjectUtil.defaultIfNull(this.background, Color.WHITE));
        // 随机画干扰圈圈
        drawInterfere(g);


        return image;
    }

    // ----------------------------------------------------------------------------------------------------- Private method start
    /**
     * 绘制字符串
     *
     * @param g {@link Graphics2D}画笔
     * @param code 验证码
     */
    private void drawString(Graphics2D g, String code) {
        // 指定透明度
        if (null != this.textAlpha) {
            g.setComposite(this.textAlpha);
        }
        GraphicsUtil.drawStringColourful(g, code, this.font, this.width, this.height);
    }


}
