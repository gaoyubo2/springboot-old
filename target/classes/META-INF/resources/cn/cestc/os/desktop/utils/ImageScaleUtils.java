package cn.cestc.os.desktop.utils;

/**
 * 图片缩小算法，方形区域抽样
 */

import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Description:图片缩略图方法类
 *
 * @author bo.xu
 * 2015年8月4日 下午1:06:52
 */
public class ImageScaleUtils
{

    private int width;
    private int height;
    private int zoomWidth;
    private int zoomHeight;
    private File destFile;
    private BufferedImage srcBufferImage;

    protected ImageScaleUtils(File srcFile, File destFile, int zoomWidth, int zoomHeight) throws IOException
    {
        this.destFile = destFile;
        this.zoomWidth = zoomWidth;
        this.zoomHeight = zoomHeight;
        this.srcBufferImage = ImageIO.read(srcFile);
        this.width = this.srcBufferImage.getWidth();
        this.height = this.srcBufferImage.getHeight();
        if (width <= zoomWidth && height <= zoomHeight)
        {
            FileUtils.copyFile(srcFile, destFile);
        } else
        {
            resizeFix();
        }
    }

    protected ImageScaleUtils(BufferedImage srcBufferImage, File destFile, int zoomWidth, int zoomHeight) throws IOException
    {
        this.destFile = destFile;
        this.zoomWidth = zoomWidth;
        this.zoomHeight = zoomHeight;
        this.srcBufferImage = srcBufferImage;
        this.width = this.srcBufferImage.getWidth();
        this.height = this.srcBufferImage.getHeight();
        resizeFix();
    }

    public static void resizeFix(File srcFile, File destFile, int width, int height) throws IOException
    {
        new ImageScaleUtils(srcFile, destFile, width, height);
    }

    public static void resizeFix(BufferedImage bufImg, File destFile, int width, int height) throws IOException
    {
        new ImageScaleUtils(bufImg, destFile, width, height);
    }

    public static void main(String[] args) throws IOException
    {
        long start = System.currentTimeMillis();
        ImageScaleUtils.resizeFix(new File("C:/Users/BFD_506/Desktop/1.gif"), new File("C:/Users/BFD_506/Desktop/5.jpg"), 150, 105);
        long end = System.currentTimeMillis();
        System.out.println("success:" + (end - start));
    }

    /**
     * 压缩图片
     *
     * @throws IOException
     */
    protected void resizeFix() throws IOException
    {
        if (width <= zoomWidth && height <= zoomHeight)
        {
            resize(width, height);
        } else
        {
            resize(zoomWidth, Math.round((float) zoomWidth * height / width));
        }
        //为了和原php效果一样，注掉下面代码，代码else的效果是不变形图片，平铺
//		} else if ((float) width / height > (float) zoomWidth / zoomHeight) {
//			resize(zoomWidth, Math.round((float) zoomWidth * height / width));
//		} else {
//			resize(Math.round((float) zoomHeight * width / height), zoomHeight);
//		}
    }

    private void resize(int w, int h) throws IOException
    {
        BufferedImage imgBuf = scaleImage(w, h);
        File parent = destFile.getParentFile();
        if (!parent.exists())
        {
            parent.mkdirs();
        }
        ImageIO.write(imgBuf, "jpeg", destFile);
    }

    private BufferedImage scaleImage(int outWidth, int outHeight)
    {
        int[] rgbArray = srcBufferImage.getRGB(0, 0, width, height, null, 0, width);
        BufferedImage pbFinalOut = new BufferedImage(outWidth, outHeight, BufferedImage.TYPE_INT_RGB);
        double hScale = ((double) width) / ((double) outWidth);// 宽缩小的倍数
        double vScale = ((double) height) / ((double) outHeight);// 高缩小的倍数

        int winX0, winY0, winX1, winY1;
        int valueRGB = 0;
        long R, G, B;
        int x, y, i, j;
        int n;
        for (y = 0; y < outHeight; y++)
        {
            winY0 = (int) (y * vScale + 0.5);// 得到原图高的Y坐标
            if (winY0 < 0)
            {
                winY0 = 0;
            }
            winY1 = (int) (winY0 + vScale + 0.5);
            if (winY1 > height)
            {
                winY1 = height;
            }
            for (x = 0; x < outWidth; x++)
            {
                winX0 = (int) (x * hScale + 0.5);
                if (winX0 < 0)
                {
                    winX0 = 0;
                }
                winX1 = (int) (winX0 + hScale + 0.5);
                if (winX1 > width)
                {
                    winX1 = width;
                }
                R = 0;
                G = 0;
                B = 0;
                for (i = winX0; i < winX1; i++)
                {
                    for (j = winY0; j < winY1; j++)
                    {
                        valueRGB = rgbArray[width * j + i];
                        R += getRedValue(valueRGB);
                        G += getGreenValue(valueRGB);
                        B += getBlueValue(valueRGB);
                    }
                }
                n = (winX1 - winX0) * (winY1 - winY0);
                R = (int) (((double) R) / n + 0.5);
                G = (int) (((double) G) / n + 0.5);
                B = (int) (((double) B) / n + 0.5);
                valueRGB = comRGB(clip((int) R), clip((int) G), clip((int) B));
                pbFinalOut.setRGB(x, y, valueRGB);
            }
        }
        return pbFinalOut;
    }

    private int clip(int x)
    {
        if (x < 0)
            return 0;
        if (x > 255)
            return 255;
        return x;
    }

    private int getRedValue(int rgbValue)
    {
        int temp = rgbValue & 0x00ff0000;
        return temp >> 16;
    }

    private int getGreenValue(int rgbValue)
    {
        int temp = rgbValue & 0x0000ff00;
        return temp >> 8;
    }

    private int getBlueValue(int rgbValue)
    {
        return rgbValue & 0x000000ff;
    }

    private int comRGB(int redValue, int greenValue, int blueValue)
    {

        return (redValue << 16) + (greenValue << 8) + blueValue;
    }
}
