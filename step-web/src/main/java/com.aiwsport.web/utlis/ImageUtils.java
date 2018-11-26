package com.aiwsport.web.utlis;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by yangjian9 on 2018/10/13.
 */
public class ImageUtils {

    /**
     * 导入本地图片到缓冲区
     */
    public static BufferedImage loadImageLocal(String imgName) {
        try {
            return ImageIO.read(new File(imgName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 通过网络获取图片
     *
     * @param url
     * @return
     */
    public static BufferedImage getUrlByBufferedImage(String url) {
        try {
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            // 连接超时
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(25000);
            // 读取超时 --服务器响应比较慢,增大时间
            conn.setReadTimeout(25000);
            conn.setRequestMethod("GET");
            conn.addRequestProperty("Accept-Language", "zh-cn");
            conn.addRequestProperty("Content-type", "image/jpeg");
            conn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727)");
            conn.connect();
            BufferedImage bufImg = ImageIO.read(conn.getInputStream());
            conn.disconnect();
            return bufImg;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 缩小Image，此方法返回源图像按给定宽度、高度限制下缩放后的图像
     *
     * @param inputImage
     * @param newWidth
     *            ：压缩后宽度
     * @param newHeight
     *            ：压缩后高度
     * @throws java.io.IOException
     *             return
     */
    public static BufferedImage scaleByPercentage(BufferedImage inputImage, int newWidth, int newHeight) throws Exception {
        // 获取原始图像透明度类型
        int type = inputImage.getColorModel().getTransparency();
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        // 开启抗锯齿
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 使用高质量压缩
        renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        BufferedImage img = new BufferedImage(newWidth, newHeight, type);
        Graphics2D graphics2d = img.createGraphics();
        graphics2d.setRenderingHints(renderingHints);
        graphics2d.drawImage(inputImage, 0, 0, newWidth, newHeight, 0, 0, width, height, null);
        graphics2d.dispose();
        return img;
    }

    /**
     * 传入的图像必须是正方形的 才会 圆形 如果是长方形的比例则会变成椭圆的
     *
     * @param bi1
     *            用户头像地址
     * @return
     * @throws IOException
     */
    public static BufferedImage convertCircular(BufferedImage bi1) throws IOException {

//		BufferedImage bi1 = ImageIO.read(new File(url));

        // 这种是黑色底的
//		BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(), BufferedImage.TYPE_INT_RGB);

        // 透明底的图片
        BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1.getHeight());
        Graphics2D g2 = bi2.createGraphics();
        g2.setClip(shape);
        // 使用 setRenderingHint 设置抗锯齿
        g2.drawImage(bi1, 0, 0, null);
        // 设置颜色
        g2.setBackground(Color.green);
        g2.dispose();
        return bi2;
    }

    /**
     * 生成新图片到本地
     */
    public static void writeImageLocal(String newImage, BufferedImage img) {
        if (newImage != null && img != null) {
            try {
                File outputfile = new File(newImage);
                ImageIO.write(img, "jpg", outputfile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
     */
    public static BufferedImage modifyImage(BufferedImage img, Object content, int x, int y) {

        Font font = new Font("Times New Roman", Font.ITALIC, 36);// 添加字体的属性设置

        Graphics2D g = null;

        int fontsize = 0;

        int x1 = 0;

        int y1 = 0;

        try {
            int w = img.getWidth();
            int h = img.getHeight();
            g = img.createGraphics();
            g.setBackground(Color.WHITE);
            g.setColor(Color.white);//设置字体颜色
            g.setFont(font);
            // 验证输出位置的纵坐标和横坐标
            if (x >= h || y >= w) {
                x1 = h - fontsize + 2;
                y1 = w;
            } else {
                x1 = x;
                y1 = y;
            }
            if (content != null) {
                g.drawString(content.toString(), x1, y1);
            }
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return img;
    }

    public static BufferedImage modifyImagetogeter(BufferedImage b, BufferedImage d) {
        Graphics2D g = null;
        try {
            int w = b.getWidth();
            int h = b.getHeight();


            g = d.createGraphics();
            g.drawImage(b, 15, 15, w, h, null);
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return d;
    }

    public static BufferedImage buildImg(String text, String imgUrl, String baseImgFile) throws Exception{
        BufferedImage d = loadImageLocal(baseImgFile);
        BufferedImage url = getUrlByBufferedImage(imgUrl);
        BufferedImage convertImage = scaleByPercentage(url, 100, 100);
        convertImage = convertCircular(url);
        convertImage = modifyImagetogeter(convertImage, d);
        convertImage = modifyImage(convertImage,text,360,230);
        return convertImage;
    }

    public static void main(String[] args) throws Exception{
        BufferedImage d = buildImg("杨% 今日行走 48993 步",
                "https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epnRkt8ueSLqcIUPr7XKaNwmn3y0OvoyFjaNRXwByvtKeZ1JlCdCbuqrELauJflWuPkvVcF7s48Bw/132",
                "/Users/yangjian9/Desktop/paobu.jpg");

        writeImageLocal("/Users/yangjian9/Desktop/rh.jpg",d);
        System.out.printf("融合成功");

    }

}
