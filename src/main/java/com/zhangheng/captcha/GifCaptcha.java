package com.zhangheng.captcha;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.img.gif.AnimatedGifEncoder;
import cn.hutool.core.util.ObjectUtil;
import com.zhangheng.util.RandomUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: ZhangHeng
 * @email: zhangheng_0805@163.com
 * @date: 2023-07-26 15:37
 * @version: 1.0
 * @description: gif动图干扰验证码
 */
public class GifCaptcha extends AbstractCaptcha {
    private static final long serialVersionUID = 5091627304326538463L;

    //量化器取样间隔 - 默认是10ms
    private int quality = 100;
    // 帧循环次数
    private int repeat = 0;
    //设置随机颜色时，最小的取色范围
    private int minColor = 0;
    //设置随机颜色时，最大的取色范围
    private int maxColor = 255;


    /**
     * 可以设置验证码宽度，高度的构造函数
     *
     * @param width  验证码宽度
     * @param height 验证码高度
     */
    public GifCaptcha(int width, int height) {
        this(width, height, 5);
    }

    /**
     * @param width     验证码宽度
     * @param height    验证码高度
     * @param codeCount 验证码个数
     */
    public GifCaptcha(int width, int height, int codeCount) {
        super(width, height, codeCount, 10);
    }

    public GifCaptcha(int width, int height, int codeCount, int interfereCount) {
        super(width, height, codeCount, interfereCount);
    }

    /**
     * 设置图像的颜色量化(转换质量 由GIF规范允许的最大256种颜色)。
     * 低的值(最小值= 1)产生更好的颜色,但处理显著缓慢。
     * 10是默认,并产生良好的颜色而且有以合理的速度。
     * 值更大(大于20)不产生显著的改善速度
     *
     * @param quality 大于1
     * @return this
     */
    public GifCaptcha setQuality(int quality) {
        if (quality < 1) {
            quality = 1;
        }
        this.quality = quality;
        return this;
    }

    /**
     * 设置GIF帧应该播放的次数。
     * 默认是 0; 0意味着无限循环。
     * 必须在添加的第一个图像之前被调用。
     *
     * @param repeat 必须大于等于0
     * @return this
     */
    public GifCaptcha setRepeat(int repeat) {
        if (repeat >= 0) {
            this.repeat = repeat;
        }
        return this;
    }

    /**
     * 设置验证码字符颜色
     *
     * @param maxColor 颜色
     * @return this
     */
    public GifCaptcha setMaxColor(int maxColor) {
        this.maxColor = maxColor;
        return this;
    }

    /**
     * 设置验证码字符颜色
     *
     * @param minColor 颜色
     * @return this
     */
    public GifCaptcha setMinColor(int minColor) {
        this.minColor = minColor;
        return this;
    }

    @Override
    public void createCode() {
        generateCode();
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        AnimatedGifEncoder gifEncoder = new AnimatedGifEncoder();// gif编码类
        //生成字符
        gifEncoder.start(out);
        gifEncoder.setQuality(quality);//设置量化器取样间隔
        // 帧延迟 (默认100)
        int delay = 400;
        gifEncoder.setDelay(delay);//设置帧延迟
        gifEncoder.setRepeat(repeat);//帧循环次数
        BufferedImage frame;
        char[] chars = code.toCharArray();
//        Color[] fontColor = new Color[chars.length];
        for (int n = 0; n < 1; n++) {
            for (int i = 0; i < chars.length; i++) {
//                fontColor[i] = getRandomColor(minColor, maxColor);
                frame = graphicsImage(chars, i);
                gifEncoder.addFrame(frame);
                frame.flush();
            }
        }
        gifEncoder.finish();
        this.imageBytes = out.toByteArray();
    }

    @Override
    protected Image createImage(String code) {
        return null;
    }


    /**
     * 画随机码图
     *
     * @param words 字符数组
     * @param flag  透明度使用
     * @return BufferedImage
     */
    private BufferedImage graphicsImage(char[] words, int flag) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //或得图形上下文
        Graphics2D g2d = image.createGraphics();
        //利用指定颜色填充背景
        g2d.setColor(ObjectUtil.defaultIfNull(this.background, Color.WHITE));
        g2d.fillRect(0, 0, width, height);
        AlphaComposite ac;
        // 字符的y坐标
        float y = (height >> 1) + (font.getSize() >> 1);
        y += RandomUtil.randomDouble(-height * 0.15, height * 0.15);
        int length = words.length;
        float m = 1.0f * (width - (length * font.getSize())) / length;
        //字符的x坐标
        float x = Math.max(m / 2.0f, 2);
        x += RandomUtil.randomDouble(-width * 0.15, width * 0.15);
        g2d.setFont(font);
        // 指定透明度
        if (null != this.textAlpha) {
            g2d.setComposite(this.textAlpha);
        }
        final ThreadLocalRandom random = RandomUtil.getRandom();
        float alpha;

//        int i1 = (length - 2) / 2;
//        int[][] alphaIndex = new int[2][i1];
//        int index = 0;
//        for (int i = 0; i < alphaIndex.length; i++) {
//            int[] ints = new int[i1];
//            for (int j = 0; j < i1; j++) {
//                if (index == i1)
//                    continue;
//                ints[j] = index;
//                index++;
//            }
//            index++;
//            alphaIndex[i] = ints;
//        }

        boolean b = RandomUtil.randomBoolean();
        int alphaIndex;
        if (code.charAt(length - 1)=='=')
            alphaIndex= RandomUtil.randomInt(length - 1);
        else
            alphaIndex = RandomUtil.randomInt(length);
        for (int i = 0; i < length; i++) {
            alpha = 1.0f;
            if (b && i == alphaIndex) {
                alpha = 0;
            } else {
                if (i != length - 1)
                    alpha = getAlpha(length, flag, i);
            }
            ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
            g2d.setComposite(ac);
            g2d.setColor(ImgUtil.randomColor(random));
//            g2d.setColor(fontColor[i]);
            // 绘制字符
            g2d.drawString(words[i] + "", x + (font.getSize() + m) * i, y);
            //扭曲
//            shear(g2d,this.width, this.height, ObjectUtil.defaultIfNull(this.background, Color.white));
            //干扰
            drawInterfere(g2d);
        }
        g2d.dispose();
        return image;
    }

//    public static void main(String[] args) throws MyException {
//        String generate = new MathGenerator().generate();
//        System.out.println(generate+ MathUtil.operation(generate));
////        int[] indexArray = getIndexArray(6);
////        System.out.println(ArrayUtil.toString(indexArray));
////        System.out.println(ArrayUtil.toString(ArrayUtil.shuffle(indexArray)));
//    }
//    private static int[] getIndexArray(int codeLength){
//        int i1 = (codeLength - 2) / 2;
//        int[] alphaIndex = new int[i1*2];
//        int index = 0;
//        for (int i = 0; i < alphaIndex.length; i++) {
//            alphaIndex[i] = index;
//            if (index==i1-1)
//                index++;
//            index++;
//        }
//        return alphaIndex;
//    }
    /**
     * 画随机干扰
     *
     * @param g {@link Graphics2D}
     */
//    private void drawInterfere(Graphics2D g) {
//        final ThreadLocalRandom random = RandomUtil.getRandom();
//        for (int i = 0; i < this.interfereCount; i++) {
//            g.setColor(ImgUtil.randomColor(random));
//            g.drawOval(random.nextInt(width), random.nextInt(height), random.nextInt(height >> 1), random.nextInt(height >> 1));
//        }
//    }

    /**
     * 获取透明度,从0到1,自动计算步长
     *
     * @param v 字符数
     * @param i 第几帧画面
     * @param j 第几个字符
     * @return float 透明度
     */
    private float getAlpha(int v, int i, int j) {
        int num = i + j;
        if (num == 0)
            return 1;
//        num = num == 0 ? v : num;
        float r = (float) 1 / v;
        float s = (v + 1) * r;
        return num > v ? (num * r - s) : num * r;
    }

    /**
     * 通过给定范围获得随机的颜色
     *
     * @return Color 获得随机的颜色
     */
    private Color getRandomColor(int min, int max) {
        if (min > 255) {
            min = 255;
        }
        if (max > 255) {
            max = 255;
        }
        if (min < 0) {
            min = 0;
        }
        if (max < 0) {
            max = 0;
        }
        if (min > max) {
            min = 0;
            max = 255;
        }
        return new Color(
                RandomUtil.randomInt(min, max),
                RandomUtil.randomInt(min, max),
                RandomUtil.randomInt(min, max));
    }
}
