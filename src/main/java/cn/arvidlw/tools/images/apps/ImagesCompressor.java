package cn.arvidlw.tools.images.apps;

/**
 * Created by lw_co on 2017/1/5.
 * 这是个单独的类
 */

import sun.awt.image.GifImageDecoder;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImagesCompressor {

    public static void main(String[] args) {
        //zipWidthHeightImageFile(new File("D:\\360Downloads\\test\\1.png"),new File("D:\\360Downloads\\test\\1.png"),484,211,0.7f);

        zipImageFile(new File("D:\\360Downloads\\test1\\cat.png"),new File("D:\\360Downloads\\test1\\cat1.jpg"),0,0,0.7f);
        //zipImageFile(new File("D:\\360Downloads\\test\\test1.gif"),new File("D:\\360Downloads\\test\\test1-1.gif"),0,0,0.9f);

        //zipImageFile(new File("C:\\spider\\3.jpg"),new File("C:\\spider\\3-3.jpg"),425,638,0.7f);

        System.out.println("ok");
    }

    /**
     * 根据设置的宽高等比例压缩图片文件<br> 先保存原文件，再压缩、上传
     * @param oldFile  要进行压缩的文件
     * @param newFile  新文件
     * @param width  宽度 //设置宽度时（高度传入0，等比例缩放）
     * @param height 高度 //设置高度时（宽度传入0，等比例缩放）
     * @param quality 质量
     * @return 返回压缩后的文件的全路径
     */
    public static String zipImageFile(File oldFile,File newFile, int width, int height,float quality) {
        if (oldFile == null) {
            return null;
        }
        try {
            /** 对服务器上的临时文件进行处理 */
            Image srcFile = ImageIO.read(oldFile);
            int w = srcFile.getWidth(null);
            int h = srcFile.getHeight(null);
            double bili;
            //当图片的宽高小于设定的宽高时保持原长宽，否则则进行缩放
            if(w<width){
                width=w;
                height=h;
            }
            else if(width>0){
                bili=width/(double)w;
                height = (int) (h*bili);
            }else{
                if(height>0){
                    bili=height/(double)h;
                    width = (int) (w*bili);
                }else {
                    width=w;
                    height=h;
                }
            }

            String srcImgPath = newFile.getAbsoluteFile().toString();//getAbsoluteFile()方法返回file对象。
            System.out.println(srcImgPath);
            String subfix = "jpg";
            subfix = srcImgPath.substring(srcImgPath.lastIndexOf(".")+1,srcImgPath.length());

            BufferedImage buffImg = null;
            if(subfix.equals("png")){
                buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            }else{
                buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            }

            Graphics2D graphics = buffImg.createGraphics();
            graphics.setBackground(new Color(255,255,255));
            graphics.setColor(new Color(255,255,255));
            graphics.fillRect(0, 0, width, height);//填充矩形
            /*
             * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的
             * 优先级比速度高 生成的图片质量比较好 但速度慢
             */
            graphics.drawImage(srcFile.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

            ImageIO.write(buffImg, subfix, new File(srcImgPath));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile.getAbsolutePath();
    }

//    /**
//     * 按设置的宽度高度压缩图片文件<br> 先保存原文件，再压缩、上传
//     * @param oldFile  要进行压缩的文件全路径
//     * @param newFile  新文件
//     * @param width  宽度
//     * @param height 高度
//     * @param quality 质量
//     * @return 返回压缩后的文件的全路径
//     */
//    public static String zipWidthHeightImageFile(File oldFile,File newFile, int width, int height,float quality) {
//        if (oldFile == null) {
//            return null;
//        }
//        if(width==0||height==0){
//            System.out.println("please input the width and height of images that you want.");
//            return null;
//        }
//        String newImage = null;
//        try {
//            /** 对服务器上的临时文件进行处理 */
//            Image srcFile = ImageIO.read(oldFile);
//
//            String srcImgPath = newFile.getAbsoluteFile().toString();
//            System.out.println(srcImgPath);
//            String subfix = "jpg";
//            subfix = srcImgPath.substring(srcImgPath.lastIndexOf(".")+1,srcImgPath.length());
//
//            BufferedImage buffImg = null;
//            if(subfix.equals("png")){
//                buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//            }else{
//                buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//            }
//
//            Graphics2D graphics = buffImg.createGraphics();
//            graphics.setBackground(new Color(255,255,255));
//            graphics.setColor(new Color(255,255,255));
//            graphics.fillRect(0, 0, width, height);
//            graphics.drawImage(srcFile.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
//
//            ImageIO.write(buffImg, subfix, new File(srcImgPath));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return newImage;
//    }
    /*public void test(){
        GifEncoder
        GifImageDecoder gifImageDecoder= new GifImageDecoder();
    }*/

}