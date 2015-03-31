package cn.gyyx.AuxiliaryTools;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageSynthesis {
	public static void main(String[] args) {

		try {
			/**
			 * 下面代码的"D:\\1.jpg"，是我把商品图片(需要被加水印的图片)
			 * 保存在D盘，并起名为1.jpg，这里您根据您的图片的实际位置来输 入正确的文件路径。
			 */
			File formerFile = new File("C:\\Users\\Administrator\\Desktop\\图片合成实验\\icon.png");
			//java.net.URL formerFile = Image.class.getResource("C:\\Users\\Administrator\\Desktop\\图片合成实验\\icon.png");
			BufferedImage formerImage = ImageIO.read(formerFile);
			// 以下2行代码分别获得图片的宽(width)和高(height)
			int width = formerImage.getWidth(null);
			int height = formerImage.getHeight(null);
			System.out.println("原始图片的宽为：" + width + "\n原始图片的高为：" + height);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(formerImage, 0, 0, width, height, null);

			/**
			 * 下面代码的"D:\\sy.gif"，是我把水印图片保存在D盘， 并起名为sy.gif，这里您根据您的图片的实际位置来输
			 * 入正确的文件路径。
			 */
			File waterMarkFile = new File(
					"C:\\Users\\Administrator\\Desktop\\图片合成实验\\corner.png");
			BufferedImage waterMarkImage = ImageIO.read(waterMarkFile);
			int widthWMI = waterMarkImage.getWidth(null);
			int heightWMI = waterMarkImage.getHeight(null);
			/**
			 * 以下2行代码的x，y分别表示水印图片在原始图片的位置。 x,y为坐标。width，height为商品图片的宽和高。 width *
			 * 0.5 表示水印图片的水平位置覆盖在商品图片 水平位置的正中间。height * 0.5 表示垂直位置。
			 * 最终无论商品图片的宽高是多少，水印图片都会显示在 商品图片的正中间。 您可以根据您的需求，更改0.5这个数值，达到您想要的效果。
			 * 这里我说的商品图片就是要被水印覆盖的图片。
			 */
			int x = (int) (width * 0.000001); // "0.5"小数越大，水印越向左移动。
			int y = (int) (height * 0.000001); // "0.5"小数越大，水印越向上移动。
			// g.drawImage(waterMarkImage, width - widthWMI - x, height -
			// heightWMI - y, widthWMI, heightWMI, null);

			/**
			 * 输出被加上水印的图片，也就是最终的效果。 注意！下面代码的"D:\\1.jpg"是最后输出
			 * 的文件，如果跟你原始文件的路径和名字相同 的话，那么会覆盖掉原始文件。 如：我的原始文件位于"D:\\1.jpg"，而下
			 * 面的代码运行之后，我的原始文件就会丢失被 覆盖掉。 您可以根据您的需要把加上水印后的图片放到 您指定的文件路径。
			 */
			g.dispose();
			// FileOutputStream out = new
			// FileOutputStream("C:\\Users\\Administrator\\Desktop\\图片合成实验\\3.png");
			ImageIO.write(image, "jpg", new File(
					"C:\\Users\\Administrator\\Desktop\\图片合成实验\\3.png"));
			// 下面代码将被加上水印的图片转换为JPEG、JPG文件
			// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			// encoder.encode(image);
			// out.close();
			System.out.println("水印已经添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
