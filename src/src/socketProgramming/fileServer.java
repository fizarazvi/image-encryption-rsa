import java.net.*; 
import java.io.*; 

public class fileServer{
	ServerSocket s = null;
	Socket sr = null;
	public fileServer(int port){
		try{
			byte []b = new byte[2002];
			s = new ServerSocket(5000);
			System.out.println("Server > Waiting for client to connect...");
			sr = s.accept();
			System.out.println("Server > Client connected...");
			DataOutputStream dos = new DataOutputStream(sr.getOutputStream());
			System.out.println("Server > Sending keys to client...");
			try{ 
         	    String keys = "Hola, next key"; 
        		dos.writeUTF(keys); 
       	 	} 
        	catch(IOException i) { 
            	System.out.println(i); 
        	}
        	System.out.println("Server > Receiving file from client...");
        	//receive file from the client ftp:
        	InputStream is = sr.getInputStream();
			FileOutputStream fr = new FileOutputStream("Test2.txt");
			is.read(b,0,b.length);
			fr.write(b,0,b.length);
        	//ftp end.
        }
        catch(IOException e){}
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
		fileServer fs = new fileServer(5000);
		/*byte []b = new byte[2002];
		ServerSocket s = new ServerSocket(5000);
		System.out.println("Waiting for client to client...");
		Socket sr = s.accept();
		System.out.println("Client connected...");
		FileInputStream fr = new FileInputStream("Test.txt");
		fr.read(b,0,b.length);
		OutputStream os = sr.getOutputStream();
		os.write(b,0,b.length);
		sr.close();*/
	}
}