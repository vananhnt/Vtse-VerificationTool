package helper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.DoubleToIntFunction;

import javax.imageio.ImageIO;

/**
 * This program demonstrates how to resize an image.
 *
 * @author www.codejava.net
 *
 */
public class ImageResizer {

    public static BufferedImage resize(BufferedImage inputImage, int scaledWidth, int scaledHeight, Boolean keepRatio)
            throws IOException {

        if(keepRatio){
            int originalWidth = inputImage.getWidth();
            int originalHeight= inputImage.getHeight();
            double widthRatio = scaledWidth / 1.0 /originalWidth;
            double heightRatio= scaledHeight/ 1.0 /originalHeight;
            if(widthRatio < heightRatio){
                scaledWidth = (int)(originalWidth * widthRatio);
                scaledHeight = (int)(originalHeight * widthRatio);
            } else {
                scaledWidth = (int)(originalWidth * heightRatio);
                scaledHeight = (int)(originalHeight * heightRatio);
            }
        }
        System.out.println(scaledWidth);
        System.out.println(scaledHeight);
        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage.getScaledInstance(scaledWidth, scaledHeight, Image. SCALE_SMOOTH), 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        return outputImage;
    }
    public static BufferedImage resize(BufferedImage inputImage, int scaledWidth)
            throws IOException {

        int originalWidth = inputImage.getWidth();
        int originalHeight= inputImage.getHeight();
        double widthRatio = scaledWidth / 1.0 /originalWidth;
        int scaledHeight = (int) (originalHeight * widthRatio);
        System.out.println(scaledWidth);
        System.out.println(scaledHeight);
        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage.getScaledInstance(scaledWidth, scaledHeight, Image. SCALE_SMOOTH), 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        return outputImage;
    }

//    /**
//     * Resizes an image by a percentage of original size (proportional).
//     * @param inputImagePath Path of the original image
//     * @param outputImagePath Path to save the resized image
//     * @param percent a double number specifies percentage of the output image
//     * over the input image.
//     * @throws IOException
//     */
//    public static void resize(String inputImagePath,
//                              String outputImagePath, double percent) throws IOException {
//        File inputFile = new File(inputImagePath);
//        BufferedImage inputImage = ImageIO.read(inputFile);
//        int scaledWidth = (int) (inputImage.getWidth() * percent);
//        int scaledHeight = (int) (inputImage.getHeight() * percent);
//        resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
//    }

//    /**
//     * Test resizing images
//     */
//    public static void main(String[] args) {
//        String inputImagePath = "D:/Photo/Puppy.jpg";
//        String outputImagePath1 = "D:/Photo/Puppy_Fixed.jpg";
//        String outputImagePath2 = "D:/Photo/Puppy_Smaller.jpg";
//        String outputImagePath3 = "D:/Photo/Puppy_Bigger.jpg";
//
//        try {
//            // resize to a fixed width (not proportional)
//            int scaledWidth = 1024;
//            int scaledHeight = 768;
//            ImageResizer.resize(inputImagePath, outputImagePath1, scaledWidth, scaledHeight);
//
//            // resize smaller by 50%
//            double percent = 0.5;
//            ImageResizer.resize(inputImagePath, outputImagePath2, percent);
//
//            // resize bigger by 50%
//            percent = 1.5;
//            ImageResizer.resize(inputImagePath, outputImagePath3, percent);
//
//        } catch (IOException ex) {
//            System.out.println("Error resizing the image.");
//            ex.printStackTrace();
//        }
//    }

}
