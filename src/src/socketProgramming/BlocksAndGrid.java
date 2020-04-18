package socketProgramming;

import com.sun.imageio.plugins.common.SubImageInputStream;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BlocksAndGrid {


    public static BufferedImage resizeImage(final Image image, int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        //below three lines are for RenderingHints for better image quality at cost of higher processing time
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return bufferedImage;
    }

    public static BufferedImage breakAndArrangeImage(BufferedImage originalImgage, String bg) throws IOException{

        int row = 4; //You should decide the values for rows and cols variables
        int col = 4;
        if(bg.equals("g")){
            row = 2;
            col = 2;
        }

        int xx,yy;
        //total width and total height of an image
        int tWidth = originalImgage.getWidth();
        int tHeight = originalImgage.getHeight();
        if(bg.equals("b")){
            yy = tWidth-(tWidth%8);
            xx = tHeight-(tHeight%8);
            originalImgage = resizeImage(originalImgage, yy,xx);
            tWidth = originalImgage.getWidth();
            tHeight = originalImgage.getHeight();
        }
        int eWidth = tWidth / col;
        int eHeight = tHeight / row;

        BufferedImage newImage = new BufferedImage(tWidth, tHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
        g2.setPaint(Color.BLACK);
        g2.fillRect(0, 0, tWidth, tHeight);
        g2.setColor(oldColor);

        int x = 0;
        int y = 0;
        BufferedImage SubImgage[];
        if(bg.equals("b")) {
            SubImgage = new BufferedImage[16];
        } else {
            SubImgage = new BufferedImage[4];
        }
        System.out.println(tWidth+" "+tHeight+" "+row+" "+col);
        int c=0;
        for (int i = 0; i < row; i++) {
            y = 0;
            for (int j = 0; j < col; j++) {

                System.out.println("creating piece: "+i+" "+j);

                SubImgage[c] = originalImgage.getSubimage(y, x, eWidth, eHeight);
                //File outputfile = new File("C:\\Users\\Fiza\\Pictures\\Screenshots\\Big\\"+c+".jpg");

                //ImageIO.write(SubImgage[i], "jpg", outputfile);
                if(bg.equals("b"))
                    SubImgage[c] = breakAndArrangeImage(SubImgage[c], "g");
                c++;
                y += eWidth;


            }

            x += eHeight;
        }

        System.out.println("Splitting done");

        //writing mini images into image files
        c= SubImgage.length-1;
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                g2.drawImage(SubImgage[c], null, eWidth*j, eHeight*i);
                c--;
            }
        }
        g2.dispose();
        return newImage;

    }

    public static void main(String[] args) throws IOException {

        File file = new File("C:\\Users\\Fiza\\Pictures\\Screenshots\\Big\\pic2.jpg"); // I have bear.jpg in my working directory
        FileInputStream fis = new FileInputStream(file);
        BufferedImage image = ImageIO.read(fis);
        BufferedImage joinedImg = breakAndArrangeImage(image, "b");
        ImageIO.write(joinedImg, "png", new File("C:\\Users\\Fiza\\Pictures\\Screenshots\\Big\\joined.jpg"));

        BufferedImage re = breakAndArrangeImage(joinedImg, "b");

        ImageIO.write(re, "png", new File("C:\\Users\\Fiza\\Pictures\\Screenshots\\Big\\re.jpg"));
    }

}
