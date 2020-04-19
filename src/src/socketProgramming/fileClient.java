package socketProgramming;

import jdk.jfr.events.ExceptionThrownEvent;

import java.math.BigInteger;
import java.net.*;
import java.io.*; 

public class fileClient{
	Socket sr = null;
	DataInputStream input = null;
	FileInputStream fr =null;
	public fileClient(String address, int port){
		byte []b = new byte[2002];
		try{
			sr = new Socket(address, port);
			System.out.println("Client > Connected to server");
			input  =  new DataInputStream(new BufferedInputStream(sr.getInputStream()));
		}
		catch(UnknownHostException u){
			System.out.println(u);
		}
		catch(IOException i){
			System.out.println(i);
		}
		String Keys = "";
		try
        { 
        	Keys = input.readUTF();
        	System.out.println(Keys);
  		} 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
        System.out.println("Client > Keys received");
        //encrypt using the keys:
		String str[] = Keys.split("\\s+");
		ImagePix imgPix = new ImagePix("C:\\Users\\Fiza\\Pictures\\Client\\pic.jpg", new BigInteger(str[0]), new BigInteger(str[1]));
        String dimensions = imgPix.encrypt();

		System.out.println("Client > Encrypting image file");
        //encryption done.
        //ftp to send to the server:
        System.out.println("Client > Sending encrypted file to the server");
        try{
        	fr = new FileInputStream("C:\\Users\\Fiza\\Pictures\\Client\\encrypted.txt");
        }
        catch(FileNotFoundException f){
        	System.out.println(f);
        }
        try{
			fr.read(b,0,b.length);
			OutputStream os = sr.getOutputStream();
			int count;
			while((count = fr.read(b))>0)
				os.write(b, 0, count);
		}
		catch(IOException i){
			System.out.println(i);
		}

        try{
			DataOutputStream dos = new DataOutputStream(sr.getOutputStream());
			dos.writeUTF(dimensions);
			System.out.println(dimensions);
		}catch(Exception e){System.out.println(e);}
		//ftp end.
		System.out.println("Client > Closing Connection");
		try{
		sr.close();
		}
		catch(IOException i){
			System.out.println(i);
		}
        
	}
	public static void main(String[] args) {
		fileClient fc = new fileClient("localhost",4333);
	}
}