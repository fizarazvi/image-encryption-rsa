package socketProgramming;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BlocksAndGrid {

    public static BufferedImage breakAndArrangeImage(BufferedImage image, String bg) throws IOException{


        int rows = 4; //You should decide the values for rows and cols variables
        int cols = 4;
        if(bg.equals("g")){
            rows = 2;
            cols = 2;
        }
        int chunks = rows * cols;
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        int chunkWidth = image.getWidth() / cols; // determines the chunk width and height
        int chunkHeight = image.getHeight() / rows;

        BufferedImage newImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
        g2.setPaint(Color.BLACK);
        g2.fillRect(0, 0, imageWidth, imageHeight);
        g2.setColor(oldColor);
        int count = 0;
        BufferedImage imgs[] = new BufferedImage[chunks]; //Image array to hold image chunks
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                //Initialize the image array with image chunks
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

                // draws the image chunk
                Graphics2D gr = imgs[count++].createGraphics();
                gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                gr.dispose();
                if(bg.equals("b"))
                    imgs[count-1] = breakAndArrangeImage(imgs[count-1], "g");

            }
        }

        System.out.println("Splitting done");

        //writing mini images into image files
        int h=0, w=0;
        for (int i = imgs.length-1; i >=0; i--) {
            g2.drawImage(imgs[i], null, w, h);
            w +=chunkWidth;
            if(w>=imageWidth){
                w=0;
                h += chunkHeight;
            }
            if(bg.equals("g"))
                ImageIO.write(imgs[i], "jpg", new File("img" + i + ".jpg"));
        }
        System.out.println("Mini images created");
        g2.dispose();
        return newImage;

    }

    public static void main(String[] args) throws IOException {

        File file = new File("C:\\Users\\Fiza\\Pictures\\Screenshots\\Big\\pic.jpg"); // I have bear.jpg in my working directory
        FileInputStream fis = new FileInputStream(file);
        BufferedImage image = ImageIO.read(fis);
        BufferedImage joinedImg = breakAndArrangeImage(image, "b");
        ImageIO.write(joinedImg, "png", new File("C:\\Users\\Fiza\\Pictures\\Screenshots\\Big\\joined.jpg"));


    }

}
