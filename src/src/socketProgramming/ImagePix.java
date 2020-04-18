package socketProgramming;
import socketProgramming.RSA;
import socketProgramming.BlocksAndGrid;
import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;

import static socketProgramming.BlocksAndGrid.*;


public class ImagePix {

    public static RSA rsa;



    public static void main(String[] args) {
        try {
            System.out.println("Processing the image...");
            rsa = new RSA(1024);
            // Upload the image

            BufferedImage image = ImageIO.read(new File("C:\\Users\\Fiza\\Pictures\\Screenshots\\Big\\pic2.jpg"));
            image = breakAndArrangeImage(image, "b");
            ImageIO.write(image, "png", new File("C:\\Users\\Fiza\\Pictures\\Screenshots\\Big\\blockedAndGrid.jpg"));
            int width = image.getWidth();
            int height = image.getHeight();
            int[] pixels = new int[width * height];
            long startTime , elapsedTime;


            // Retrieve pixel info and store in 'pixels' variable
            PixelGrabber pgb = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width);
            pgb.grabPixels();

            //Write pixels to file and return encrypted pixel data
            startTime = System.nanoTime();
            int[] encryptedImagePixels = writeTextFile("C:\\Users\\Fiza\\Pictures\\Screenshots\\Big\\encrypted.txt", pixels, width);
            textToImage("C:\\Users\\Fiza\\Pictures\\Screenshots\\Big\\encrypted.jpeg", width, height, encryptedImagePixels);
            elapsedTime = System.nanoTime() - startTime;
            System.out.println("for enc it takes "+ elapsedTime/1000000+"ms.");
            // It's supposed that user modifies pixels file here
            System.out.println("Done! Cast your spells with the text file and press Enter...");
            System.in.read();


            //Reading the encrypted file
            startTime = System.nanoTime();
            int[] parsedPixels = readTextFile("C:\\Users\\Fiza\\Pictures\\Screenshots\\Big\\encrypted.txt", width, height);

            // Convert pixels to image and save
            textToImage("C:\\Users\\Fiza\\Pictures\\Screenshots\\Big\\decrypted.jpeg", width, height, parsedPixels);
            elapsedTime = System.nanoTime() - startTime;
            System.out.println("for dec it takes "+ elapsedTime/1000000+"ms.");

            image = ImageIO.read(new File("C:\\Users\\Fiza\\Pictures\\Screenshots\\Big\\decrypted.jpeg"));
            image = breakAndArrangeImage(image, "b");

            ImageIO.write(image, "png", new File("C:\\Users\\Fiza\\Pictures\\Screenshots\\Big\\finalDecrypt.jpg"));


        } catch (Exception exc) {
            System.out.println("Interrupted: " + exc.getMessage());
        }

    }

    private static int[] writeTextFile(String path, int[] data, int width) throws IOException {


        int encImage[] = new int[data.length];
        int p = data[0];
        System.out.println("Before Encryption: "+p);
        BigInteger[] filearr = new BigInteger[data.length];
        try{
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            //FileWriter fw = new FileWriter("C:\\Users\\Fiza\\Pictures\\Screenshots\\Big\\encryptedNew.txt");
            BigInteger x,y;
            int al = 255,t;
            int i=0;
            for(i = 0; i < filearr.length; i++) {
                p = data[i];
                filearr[i] = rsa.encrypt(new BigInteger(String.valueOf(p & 0xffffff)));
                //fw.write(filearr[i]+" ");
                t = al<<24 | (filearr[i].mod(new BigInteger("16777216"))).intValue();
                if(i==0){
                    System.out.println("After Encryption: "+p+" "+t+" "+filearr[i]);
                    System.out.println((p>>24)&0xff);
                    System.out.println((p>>16)&0xff);
                    System.out.println((p>>8)&0xff);
                    System.out.println((p)&0xff);
                }

                encImage[i] = t;
            }
            objectOut.writeObject(filearr);
            System.out.println("filearr "+filearr.length+"i "+i);
        }catch(Exception e){System.out.println(e);}
        return encImage;
        //f.close();
    }

    private static int[] readTextFile(String path, int width, int height) throws IOException {
        System.out.println("Processing text file...");



        BigInteger arr[] = new BigInteger[4];
        int a,r,g,b,p;
        a=b=r=g=p=0;
        try{
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object obj = objectIn.readObject();
            BigInteger[] filearr = (BigInteger[])obj;
            // following line were to directly store biginteger into the file
            //BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Fiza\\Pictures\\Screenshots\\Big\\encryptedNew.txt"));

            //String str[] = br.readLine().split(" ");
            //al = (ArrayList) obj;
            //System.out.println(al.size()+"size");
            int[] data = new int[filearr.length];
            BigInteger x, y;
            int t=0,al=255,q;
            //System.out.println("len "+str.length);
            System.out.println("len arr "+filearr.length);
            for(int i=0;i<filearr.length;i++){
                y = rsa.decrypt(filearr[i]);
                q = al<<24 | y.intValue();
                if(i==0){System.out.println("After Decryp "+" "+q);}
                data[i] = q;
            }
            return data;
        }catch(Exception e) {System.out.println(e);return null;}


    }

    private static void textToImage(String path, int width, int height, int[] data) throws IOException {
        MemoryImageSource mis = new MemoryImageSource(width, height, data, 0, width);
        Image im = Toolkit.getDefaultToolkit().createImage(mis);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(im, 0, 0, null);
        ImageIO.write(bufferedImage, "jpg", new File(path));
        System.out.println("Done! Check the result");
    }
}