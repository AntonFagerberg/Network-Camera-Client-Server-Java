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
        byte[] JPEGData,
               receivedJPEGData = new byte[Axis211A.IMAGE_BUFFER_SIZE];
        int bytesReceived = 0;

        while (true) {
            bytesReceived = inputStream.read(receivedJPEGData, 0, Axis211A.IMAGE_BUFFER_SIZE);
            System.out.println(bytesReceived);

            if (bytesReceived > 0) {
                JPEGData = new byte[bytesReceived];
                System.arraycopy(receivedJPEGData, 0, JPEGData, 0, bytesReceived);
                long timeStamp = 1000L*(((JPEGData[25]<0?256+JPEGData[25]:JPEGData[25])<<24)+((JPEGData[26]<0?256+JPEGData[26]:JPEGData[26])<<16)+((JPEGData[27]<0?256+JPEGData[27]:JPEGData[27])<<8)+(JPEGData[28]<0?256+JPEGData[28]:JPEGData[28]))+10L*(JPEGData[29]<0?256+JPEGData[29]:JPEGData[29]);
//                clientMonitor.storeJPEG(id, new JPEG(JPEGData, timeStamp));
            }
        }
    }
}
