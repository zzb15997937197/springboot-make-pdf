package com.example.cn.service;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

/***
 * 对图片进行操作 压缩图片
 *
 * @author ShilinMao
 * @since 2020年3月20日17:59:34
 *
 */
@Slf4j
public class ImageHelper {

    private static ImageHelper imageHelper = null;

    public static ImageHelper getImageHelper() {
        if (imageHelper == null) {
            imageHelper = new ImageHelper();
        }
        return imageHelper;
    }

    /***
     * 按指定的比例缩放图片
     *
     * @param fileSource
     *      源文件
     * @param destinationPath
     *      改变大小后图片的地址
     * @param scale
     *      缩放比例，如1.2
     */
    public static void  scaleImage(InputStream fileSource,
                                                      String destinationPath,
                                                      double scale) {

        //BufferedImage来读取图片
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(fileSource);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();

            width = parseDoubleToInt(width * scale);
            height = parseDoubleToInt(height * scale);

            BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB );
            image.getGraphics().drawImage(bufferedImage, 0, 0, width, height, null); // 绘制缩小后的图
            File destFile = new File(destinationPath);
            FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
            // 可以正常实现bmp、png、gif转jpg
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image); // JPEG编码
            out.close();

        } catch (IOException e) {
            log.error("压缩图片出现未知错误!", e);
        }
    }

    /***
     * 将图片缩放到指定的高度或者宽度
     * @param sourceImagePath 图片源地址
     * @param destinationPath 压缩完图片的地址
     * @param width 缩放后的宽度
     * @param height 缩放后的高度
     * @param auto 是否自动保持图片的原高宽比例
     * @param format 图图片格式 例如 jpg
     */
    public static void scaleImageWithParams(String sourceImagePath,
                                            String destinationPath, int width, int height, boolean auto, String format) {

        try {
            File file = new File(sourceImagePath);
            BufferedImage bufferedImage = null;
            bufferedImage = ImageIO.read(file);
            if (auto) {
                ArrayList<Integer> paramsArrayList = getAutoWidthAndHeight(bufferedImage, width, height);
                width = paramsArrayList.get(0);
                height = paramsArrayList.get(1);
            }

            Image image = bufferedImage.getScaledInstance(width, height,
                    Image.SCALE_DEFAULT);
            BufferedImage outputImage = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics graphics = outputImage.getGraphics();
            graphics.drawImage(image, 0, 0, null);
            graphics.dispose();
            ImageIO.write(outputImage, format, new File(destinationPath));
        } catch (Exception e) {
            log.error("压缩图片出现未知错误!", e);
        }

    }

    /**
     * 将double类型的数据转换为int，四舍五入原则
     *
     * @param sourceDouble
     * @return
     */
    private static int parseDoubleToInt(double sourceDouble) {
        int result = 0;
        result = (int) sourceDouble;
        return result;
    }

    /***
     *
     * @param bufferedImage 要缩放的图片对象
     * @param width_scale 要缩放到的宽度
     * @param height_scale 要缩放到的高度
     * @return 一个集合，第一个元素为宽度，第二个元素为高度
     */
    private static ArrayList<Integer> getAutoWidthAndHeight(BufferedImage bufferedImage,
                                                            int width_scale,
                                                            int height_scale) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        double scale_w = getDot2Decimal(width_scale, width);
        double scale_h = getDot2Decimal(height_scale, height);
        if (scale_w < scale_h) {
            arrayList.add(parseDoubleToInt(scale_w * width));
            arrayList.add(parseDoubleToInt(scale_w * height));
        } else {
            arrayList.add(parseDoubleToInt(scale_h * width));
            arrayList.add(parseDoubleToInt(scale_h * height));
        }
        return arrayList;

    }


    /***
     * 返回两个数a/b的小数点后三位的表示
     * @param a
     * @param b
     * @return
     */
    public static double getDot2Decimal(int a, int b) {
        BigDecimal bigDecimal_1 = new BigDecimal(a);
        BigDecimal bigDecimal_2 = new BigDecimal(b);
        BigDecimal bigDecimal_result = bigDecimal_1.divide(bigDecimal_2,
                new MathContext(4));
        Double double1 = new Double(bigDecimal_result.toString());
        return double1;
    }

}

