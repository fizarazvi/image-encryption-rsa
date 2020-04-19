package socketProgramming;

import java.net.*;
import java.io.*; 

public class fileServer{
	ServerSocket s = null;
	Socket sr = null;
	public fileServer(int port){
		int width = 0, height = 0;

		try{
			byte []b = new byte[2002];
			s = new ServerSocket(port);
			System.out.println("Server > Waiting for client to connect...");
			sr = s.accept();
			System.out.println("Server > Client connected...");
			DataOutputStream dos = new DataOutputStream(sr.getOutputStream());
			System.out.println("Server > Sending keys to client...");
			RSA rsa = new RSA(1024);

			try{
         	    String keys = rsa.publicKey.toString() + " " + rsa.modulus.toString();
        		dos.writeUTF(keys); 
       	 	} 
        	catch(IOException i) { 
            	System.out.println(i); 
        	}

        	System.out.println("Server > Receiving file from client...");
        	//receive file from the client ftp:

			InputStream is = sr.getInputStream();

			FileOutputStream fr = new FileOutputStream("C:\\Users\\Fiza\\Pictures\\Server\\encrypted.txt");
			int count;
			while((count = is.read(b))>0) {
				fr.write(b, 0, count);
				System.out.println(count);
			}

			try{
				DataInputStream dis = new DataInputStream(is);
				String dim = dis.readUTF();
				String str[] = dim.split("\\s+");
				width = Integer.parseInt(str[0]);
				height = Integer.parseInt(str[1]);
				System.out.println(width+" "+height);
			}catch (Exception e){System.out.println("server dimenions");}

        	//ftp end.
        }
        catch(IOException e){}
		System.out.println("decrypting file");
		ImagePix.decrypt("C:\\Users\\Fiza\\Pictures\\Server\\encrypted.txt", width, height);
        System.out.println("Server > File received");
        System.out.println("Server > Closing Connection");
        try{
			sr.close();
		}
		catch(IOException i){
			System.out.println(i);
		}

	}
	public static void main(String[] args) throws Exception{
		fileServer fs = new fileServer(4333);
	}
}