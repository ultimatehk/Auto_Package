package cn.gyyx.test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class test_imageAlpha {

/**
* 改变png图片的透明度
* @param srcImageFile 源图像地址
* @param descImageDir 输出图片的路径和名称
* @param alpha 输出图片的透明度1-10
*/
private static void setAlpha(String srcImageFile, String descImageDir,int alpha) {

    try {
    //读取图片
    FileInputStream stream = new FileInputStream(new File(srcImageFile));// 指定要读取的图片

    // 定义一个字节数组输出流，用于转换数组
    ByteArrayOutputStream os = new ByteArrayOutputStream();

    byte[] data =new byte[1024];// 定义一个1K大小的数组
    while (stream.read(data) != -1) {
    os.write(data);
    }

        ImageIcon imageIcon = new ImageIcon(os.toByteArray());
        BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(),
        BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
        g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());

        //判读透明度是否越界
        if (alpha < 0) {
        alpha = 0;
        } else if (alpha > 10) {
        alpha = 10;
        }

        // 循环每一个像素点，改变像素点的Alpha值
        for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage.getHeight(); j1++) {
        for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage.getWidth(); j2++) {
        int rgb = bufferedImage.getRGB(j2, j1);
        rgb = ((alpha * 255 / 10) << 24) | (rgb & 0x00ffffff);
        bufferedImage.setRGB(j2, j1, rgb);
        }
        }
        g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());

        // 生成图片为PNG

        ImageIO.write(bufferedImage, "png", new File(descImageDir));

    } catch (Exception e) {
    e.printStackTrace();
    }

}

public static void main(String[] args) {
setAlpha("F:/gfsciy20110326fscjy1999ppp.png","F:/gfsciy20110326fscjy1999ppp-4.png", 4);

}


}