package socketProgramming;



import java.io.*;
import java.net.*;

public class fileServer{
	ServerSocket s = null;
	Socket sr = null;
	public fileServer(int port) throws IOException {
		s = new ServerSocket(port);
		System.out.println("Waiting for client");
		Socket sr = s.accept();

		System.out.println("Server> Client connected...");

		DataOutputStream dos = new DataOutputStream(sr.getOutputStream());
		RSA rsa = new RSA(1024);
		String keys = rsa.publicKey+" "+rsa.modulus;
		dos.writeUTF(keys);
		dos.flush();
		System.out.println("Sending keys to client..");

		InputStream is = sr.getInputStream();
		String path = "..\\res\\server\\";
		FileOutputStream fr = new FileOutputStream("C:\\Users\\Fiza\\Pictures\\Server\\encryptedNew.txt");
		byte[] b = new byte[8192];
		int count;
		while((count = is.read(b))>0)
			fr.write(b, 0 , count);
		System.out.println("File rec server");

		ImagePix img = new ImagePix(rsa);
		img.decrypt("C:\\Users\\Fiza\\Pictures\\Server\\encryptedNew.txt");

	}

	public static void main(String[] args) throws Exception{
		fileServer fs = new fileServer(4333);
	}
}