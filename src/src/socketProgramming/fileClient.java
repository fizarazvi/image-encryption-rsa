package socketProgramming;

import jdk.jfr.events.ExceptionThrownEvent;

import java.math.BigInteger;
import java.net.*;
import java.io.*; 

public class fileClient{
	Socket sr = null;

	public fileClient(String address, int port) throws IOException {
		sr = new Socket(address, port);
		System.out.println("Client> Socket connected...");
		DataInputStream dis = new DataInputStream(sr.getInputStream());
		String keys = dis.readUTF();
		System.out.println(keys);

		String str[] = keys.split("\\s+");
		BigInteger e = new BigInteger(str[0]);
		BigInteger n = new BigInteger(str[1]);

		ImagePix img = new ImagePix("C:\\Users\\Fiza\\Pictures\\Client\\pic.jpg", e, n);
		String dimensions = img.encrypt();

		System.out.println("Sending file...");
		FileInputStream fr = new FileInputStream("C:\\Users\\Fiza\\Pictures\\Client\\encrypted.txt");
		byte[] b = new byte[8192];
		int count;
		OutputStream os = sr.getOutputStream();
		while((count = fr.read(b))>0)
			os.write(b, 0, count);

		System.out.println("File rec");
		sr.close();

	}
	public static void main(String[] args) throws IOException {
		fileClient fc = new fileClient("localhost",4333);
	}
}