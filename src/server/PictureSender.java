package server;

import se.lth.cs.fakecamera.Axis211A;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;

public class PictureSender extends Thread {
	private int port;
    private ServerMonitor serverMonitor;

    public PictureSender(int port, ServerMonitor serverMonitor) {
        this.port = port;
        this.serverMonitor = serverMonitor;
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            OutputStream  outputStream = serverSocket.accept().getOutputStream();
            System.out.println("PictureSender started at port: " + port + ".");

            byte[] JPEGdata = new byte[Axis211A.IMAGE_BUFFER_SIZE];
            int length;

            while (true) {
                length = serverMonitor.fillData(JPEGdata);
                outputStream.write(JPEGdata, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
