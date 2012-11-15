package local;

import common.JPEG;
import se.lth.cs.cameraproxy.Axis211A;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class CameraReceiver extends Thread {
	private InputStream inputStream;
    private String url;
    private int port, id;
    private ClientMonitor clientMonitor;

	public CameraReceiver(String url, int port, int id, ClientMonitor clientMonitor) {
		this.url = url;
        this.port = port;
        this.clientMonitor = clientMonitor;
        this.id = id;
    }

    public void run() {
        try {
            inputStream = (new Socket(url, port)).getInputStream();
            fetchJPEG();
        } catch (IOException e) {
            System.out.println("Communication error with server: " + url + ":" + port + ".");
            e.printStackTrace();
        }
    }

    private void fetchJPEG() throws IOException {
        byte[] receivedJPEGData = new byte[Axis211A.IMAGE_BUFFER_SIZE];
        int bytesReceived = 0;

        while (true) {
            bytesReceived = inputStream.read(receivedJPEGData, 0, Axis211A.IMAGE_BUFFER_SIZE);

            if (bytesReceived > 0) {
                byte[] jpeg = new byte[bytesReceived];
                System.arraycopy(receivedJPEGData, 0, jpeg, 0, bytesReceived);
                long timeStamp = 1000L*(((jpeg[25]<0?256+jpeg[25]:jpeg[25])<<24)+((jpeg[26]<0?256+jpeg[26]:jpeg[26])<<16)+((jpeg[27]<0?256+jpeg[27]:jpeg[27])<<8)+(jpeg[28]<0?256+jpeg[28]:jpeg[28]))+10L*(jpeg[29]<0?256+jpeg[29]:jpeg[29]);
                clientMonitor.storeJPEG(id, new JPEG(jpeg, timeStamp));
            }
        }
    }
}
