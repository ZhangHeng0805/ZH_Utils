package com.zhangheng.captcha;

import cn.hutool.core.img.GraphicsUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.ObjectUtil;
import com.zhangheng.util.RandomUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author: ZhangHeng
 * @email: zhangheng_0805@163.com
 * @date: 2023-07-28 20:15
 * @version: 1.0
 * @description: 扭曲斜线干扰
 */
public class ShearCaptcha extends AbstractCaptcha {
    private static final long serialVersionUID = -7096627300356535494L;

    /**
     * 构造
     *
     * @param width 图片宽
     * @param height 图片高
     */
    public ShearCaptcha(int width, int height) {
        this(width, height, 5);
    }

    /**
     * 构造
     *
     * @param width 图片宽
     * @param height 图片高
     * @param codeCount 字符个数
     */
    public ShearCaptcha(int width, int height, int codeCount) {
        this(width, height, codeCount, 4);
    }

    /**
     * 构造
     *
     * @param width 图片宽
     * @param height 图片高
     * @param codeCount 字符个数
     * @param thickness 干扰线宽度
     */
    public ShearCaptcha(int width, int height, int codeCount, int thickness) {
        super(width, height, codeCount, thickness);
    }

    @Override
    public Image createImage(String code) {
        final BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D g = GraphicsUtil.createGraphics(image, ObjectUtil.defaultIfNull(this.background, Color.WHITE));

        // 画字符串
        drawString(g, code);

        // 扭曲
        shear(g, this.width, this.height, ObjectUtil.defaultIfNull(this.background, Color.WHITE));
        // 画干扰线
        drawInterfere(g, 0, RandomUtil.randomInt(this.height) + 1, this.width, RandomUtil.randomInt(this.height) + 1, this.interfereCount, ImgUtil.randomColor());

        return image;
    }

    // ----------------------------------------------------------------------------------------------------- Private method start
    /**
     * 绘制字符串
     *
     * @param g {@link Graphics}画笔
     * @param code 验证码
     */
    private void drawString(Graphics2D g, String code) {
        // 指定透明度
        if (null != this.textAlpha) {
            g.setComposite(this.textAlpha);
        }
        GraphicsUtil.drawStringColourful(g, code, this.font, this.width, this.height);
    }



    /**
     * 干扰线
     *
     * @param g {@link Graphics}
     * @param x1 x1
     * @param y1 y1
     * @param x2 x2
     * @param y2 y2
     * @param thickness 粗细
     * @param c 颜色
     */
    @SuppressWarnings("SameParameterValue")
    private void drawInterfere(Graphics g, int x1, int y1, int x2, int y2, int thickness, Color c) {

        // The thick line is in fact a filled polygon
        g.setColor(c);
        int dX = x2 - x1;
        int dY = y2 - y1;
        // line length
        double lineLength = Math.sqrt(dX * dX + dY * dY);

        double scale = (double) (thickness) / (2 * lineLength);

        // The x and y increments from an endpoint needed to create a
        // rectangle...
        double ddx = -scale * (double) dY;
        double ddy = scale * (double) dX;
        ddx += (ddx > 0) ? 0.5 : -0.5;
        ddy += (ddy > 0) ? 0.5 : -0.5;
        int dx = (int) ddx;
        int dy = (int) ddy;

        // Now we can compute the corner points...
        int[] xPoints = new int[4];
        int[] yPoints = new int[4];

        xPoints[0] = x1 + dx;
        yPoints[0] = y1 + dy;
        xPoints[1] = x1 - dx;
        yPoints[1] = y1 - dy;
        xPoints[2] = x2 - dx;
        yPoints[2] = y2 - dy;
        xPoints[3] = x2 + dx;
        yPoints[3] = y2 + dy;

        g.fillPolygon(xPoints, yPoints, 4);
    }
}
