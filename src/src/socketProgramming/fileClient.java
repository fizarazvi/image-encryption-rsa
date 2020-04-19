package socketProgramming;

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
			input  =  new DataInputStream( 
                new BufferedInputStream(sr.getInputStream())); 
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
        System.out.println("Client > Encrypting image file");
        //encryption done.
        //ftp to send to the server:
        System.out.println("Client > Sending encrypted file to the server");
        try{
        fr = new FileInputStream("Test.txt");
        }
        catch(FileNotFoundException f){
        	System.out.println(f);
        }
        try{
		fr.read(b,0,b.length);
		OutputStream os = sr.getOutputStream();
		os.write(b,0,b.length);
		}
		catch(IOException i){
			System.out.println(i);
		}
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
		/*byte []b = new byte[2002];
		Socket sr = new Socket("localhost", 5000);
		InputStream is = sr.getInputStream();
		FileOutputStream fr = new FileOutputStream("Test2.txt");
		is.read(b,0,b.length);
		fr.write(b,0,b.length);
		sr.close();*/
	}
}