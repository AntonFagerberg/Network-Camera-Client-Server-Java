package local;

import gui.GUI2;
import se.lth.cs.cameraproxy.Axis211A;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class CameraReceiver extends Thread {
	private InputStream inputStream;
    private String url;
    private int port, cameraIndex;
    private GUI2 gui;
    private StateMonitor stateMonitor;

	public CameraReceiver(String url, int port, int cameraIndex, StateMonitor stateMonitor, GUI2 gui) {
		this.url = url;
        this.port = port;
        this.cameraIndex = cameraIndex;
        this.stateMonitor = stateMonitor;
        this.gui = gui;
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
        long timeStamp;
        int bytesExpected,
            bytesLeft;
        byte[] JPEGData,
               JPEGDataSize = new byte[4];

        while (true) {
            bytesExpected = bytesLeft = 4;
            while (bytesLeft > 0) {
                bytesLeft -= inputStream.read(JPEGDataSize, bytesExpected - bytesLeft, bytesLeft);
            }

            bytesExpected = ByteBuffer.wrap(JPEGDataSize).getInt();
            bytesLeft = bytesExpected;
            JPEGData = new byte[bytesExpected];

            while (bytesLeft > 0) {
                bytesLeft -= inputStream.read(JPEGData, bytesExpected - bytesLeft, bytesLeft);
            }

            timeStamp = 1000L*(((JPEGData[25]<0?256+JPEGData[25]:JPEGData[25])<<24)+((JPEGData[26]<0?256+JPEGData[26]:JPEGData[26])<<16)+
                        ((JPEGData[27]<0?256+JPEGData[27]:JPEGData[27])<<8)+(JPEGData[28]<0?256+JPEGData[28]:JPEGData[28]))+
                        10L*(JPEGData[29]<0?256+JPEGData[29]:JPEGData[29]);

            stateMonitor.synchronizeTimeStamps(cameraIndex, timeStamp);
            gui.refreshCameraImage(JPEGData.clone(),  cameraIndex);
        }
    }
}
