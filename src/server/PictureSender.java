package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import se.lth.cs.fakecamera.Axis211A;

public class PictureSender {
	private int port = 6077;
	private Axis211A camera = new Axis211A();
	private static final byte[] CRLF = { 13, 10 };
	
	public PictureSender() {
		
	}
	
	private void sendImage() throws IOException {
		
		int length;
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("Started HTTP server at port: " + port + ".");
		
		Socket clientSocket = serverSocket.accept();
		OutputStream outputStream = clientSocket.getOutputStream();
		camera.connect();
		
		/*while (true) {
			length = camera.getJPEG(jpegData, 0);
			//System.out.println("Length: " + length);
			outputStream.write(jpegData, 0, length);
			outputStream.write(-1);
		}*/
		
		
		
		byte[] jpegData = new byte[Axis211A.IMAGE_BUFFER_SIZE];
		length = camera.getJPEG(jpegData, 0);
		outputStream.write(jpegData, 0, length);
		System.out.println(1 + ": " + length);

		outputStream.write((byte) -2);
		
		jpegData = new byte[Axis211A.IMAGE_BUFFER_SIZE];
		length = camera.getJPEG(jpegData, 0);
		outputStream.write(jpegData, 0, length);
		System.out.println(2 + ": " + length);
		
		outputStream.write((byte) -2);
		
		/*jpegData = new byte[Axis211A.IMAGE_BUFFER_SIZE];
		length = camera.getJPEG(jpegData, 0);
		outputStream.write(jpegData, 0, length);
		System.out.println(3 + ": " + length);*/
				
		//while (true) {
			
			//outputStream.write(1);
			//outputStream.write(2);
			//outputStream.write(3);
			/*if (!camera.connect()) {
				System.out.println("Failed to connect to camera!");
				System.exit(1);
			}
			
			length = camera.getJPEG(jpegData, 0);
			camera.close();*/
			
		//}
	}
	
	public static void main(String[] args) {
		PictureSender pictureSender = new PictureSender();
		try {
			pictureSender.sendImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
