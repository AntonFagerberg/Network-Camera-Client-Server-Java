package local;

import gui.GUI2;
import se.lth.cs.cameraproxy.Axis211A;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class CameraReceiver extends Thread {
	private InputStream inputStream;
    private String url;
    private int port, id;
    private GUI2 gui;

	public CameraReceiver(String url, int port, int id, GUI2 gui) {
		this.url = url;
        this.port = port;
        this.id = id;
        this.gui = gui;

        setPriority(Thread.MAX_PRIORITY);
    }

    public void run() {
        try {
            inputStream = (new Socket(url, port)).getInputStream();
            fetchJPEG();
        } catch (IOException e) {
            System.err.println("CameraReceiver: communication error with server: " + url + ":" + port + ".");
            e.printStackTrace();
        }
    }

    private void fetchJPEG() throws IOException {
        int bytesReceived;
        byte[] JPEGData,
               receivedJPEGData = new byte[Axis211A.IMAGE_BUFFER_SIZE];

        while (true) {
            bytesReceived = inputStream.read(receivedJPEGData, 0, Axis211A.IMAGE_BUFFER_SIZE);

            if (bytesReceived > 0) {
                JPEGData = new byte[bytesReceived];
                System.arraycopy(receivedJPEGData, 0, JPEGData, 0, bytesReceived);
                gui.refreshImageCamera1(receivedJPEGData, false);
            }
        }
    }
}
