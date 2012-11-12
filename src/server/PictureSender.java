package server;

import se.lth.cs.fakecamera.Axis211A;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;

public class PictureSender {
	private final static int port = 6077;
	private final Axis211A camera = new Axis211A();
    private OutputStream outputStream;

    public PictureSender() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            outputStream = serverSocket.accept().getOutputStream();
            System.out.print("Started server at port: " + port + ".");

            if (camera.connect()){
                System.out.println("Connected to camera.");
                transferJPEG();
            } else {
                System.out.println("Could not connect to camera.");
                System.exit(1);
            }
        } catch (IOException e) {
            camera.close();
            e.printStackTrace();
        }

    }
	
	private void transferJPEG() throws IOException {
		int length;
		byte[] jpegData = new byte[Axis211A.IMAGE_BUFFER_SIZE];

//        while (true) {
        for (int i = 0; i < 5; i++) {
            length = camera.getJPEG(jpegData, 0);
            System.out.println("Sending: " + length + ". First two bytes: " + jpegData[0] + " " + jpegData[1] + ". Last two bytes: " + jpegData[length - 2] + " " + jpegData[length - 1] + ".");
            outputStream.write(jpegData, 0, length);
        }
	}
	
	public static void main(String[] args) {
		new PictureSender();
	}
}
