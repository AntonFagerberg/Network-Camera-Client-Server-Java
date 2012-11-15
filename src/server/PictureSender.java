package server;

import se.lth.cs.fakecamera.Axis211A;
import se.lth.cs.fakecamera.MotionDetector;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;

public class PictureSender extends Thread {
	private final Axis211A camera = new Axis211A();
	private int port;
    private OutputStream outputStream;
    private ServerMonitor monitor;
    private MotionDetector motionDetector = new MotionDetector();

    public PictureSender(int port, ServerMonitor monitor) {
        this.port = port;
        this.monitor = monitor;
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            outputStream = serverSocket.accept().getOutputStream();
            System.out.println("Started server at port: " + port + ".");

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


        while (true) {
            if (motionDetector.detect()) {
                monitor.setMovie();
            } else {
                monitor.setIdle();
            }
            length = camera.getJPEG(jpegData, 0);
            outputStream.write(jpegData, 0, length);
        }
	}
}
