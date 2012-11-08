package local;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import se.lth.cs.cameraproxy.Axis211A;

public class CameraListener {
	private InputStream inputStream;
	private byte[] jpegData = new byte[Axis211A.IMAGE_BUFFER_SIZE];
	
	public CameraListener() {
		
		try {
			inputStream = (new Socket("localhost", 6077)).getInputStream();
			String result = "";
			
			while (true) {
				int bytesRead = 0;
				int bytesLeft = jpegData.length;
				int status;
				
				do {
					status = inputStream.read(jpegData, bytesRead, bytesLeft);
					
					//System.out.println(bytesLeft);
					if (status > 0) {
						System.out.println(jpegData.length);
						bytesRead += status;
						bytesLeft -= status;
					}
				} while (status >= 0);
				
				//System.out.println("Status: " + status);
				
				if (bytesRead > 0) {
					System.out.println("Received image data (" + bytesRead + " bytes).");
					for (byte b : jpegData)
						System.out.print("[" + b + "] ");
				}
			}
			
			/*while(true) {
				
				System.out.println("Begin read");
				inputStream.read(new byte[11], 0, 11);
				System.out.println("End read");
			}*/
		} catch (IOException e) {
			System.out.println("Error in connect: ");
			e.printStackTrace();
		}
	}
	
	
	/*private String getLine() throws IOException {
		boolean done = false;
		String result = "";
		
		while (!done) {
			int ch = inputStream.read();
			if (ch <= 0 || ch == 10) {
				done = true;
			} else if (ch >= ' ') {
				result += (char) ch;
			}
		}
		
		return result;
	}*/
	
	public static void main(String[] args) {
		new CameraListener();
	}
}
