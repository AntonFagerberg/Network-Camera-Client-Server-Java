package client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class CameraClient extends Thread {
    private GUI gui;
	private InputStream[] inputStreams;
    private ArrayList<byte[]> JPEGData = new ArrayList<byte[]>(2);
    private byte[] JPEGDataSize = new byte[4];
    private long[] timeStamps = new long[2];
    private final static long SYNC_DELAY = 200;
    public volatile boolean alive;
    private ClientStateSender css1,css2;
    private ClientStateReceiver csr1,csr2;
    private ClientStateMonitor clientStateMonitor;

	public CameraClient(GUI gui, ClientStateMonitor clientStateMonitor, String serverAddress1, int serverPicturePort1, int serverReceivePort1, int serverSendPort1, String serverAddress2, int serverPicturePort2, int serverReceivePort2, int serverSendPort2) {
		this.gui = gui;
		this.clientStateMonitor = clientStateMonitor;
        
		try {
            inputStreams = new InputStream[]{
                (new Socket(serverAddress1, serverPicturePort1)).getInputStream(),
                (new Socket(serverAddress2, serverPicturePort2)).getInputStream()
            };

            css1 = new ClientStateSender(serverReceivePort1, clientStateMonitor);
            css1.start();
            css2 = new ClientStateSender(serverReceivePort2, clientStateMonitor);
            css2.start();
            csr1 = new ClientStateReceiver(serverAddress1, serverSendPort1, clientStateMonitor,gui);
            csr1.start();
            csr2 = new ClientStateReceiver(serverAddress2, serverSendPort2, clientStateMonitor,gui);
            csr2.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
		alive = true;

    }

    private void fillData(int i) {
        int bytesLeft, bytesExpected;

        try {
            bytesExpected = bytesLeft = 4;
            while (bytesLeft > 0) {
                bytesLeft -= inputStreams[i].read(JPEGDataSize, bytesExpected - bytesLeft, bytesLeft);
            }

            bytesLeft = bytesExpected = ByteBuffer.wrap(JPEGDataSize).getInt();
            JPEGData.add(i, new byte[bytesExpected]);

            while (bytesLeft > 0) {
                bytesLeft -= inputStreams[i].read(JPEGData.get(i), bytesExpected - bytesLeft, bytesLeft);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        timeStamps[i] =
            1000L*(((JPEGData.get(i)[25]<0?256+JPEGData.get(i)[25]:JPEGData.get(i)[25])<<24)+((JPEGData.get(i)[26]<0?256+JPEGData.get(i)[26]:JPEGData.get(i)[26])<<16)+
            ((JPEGData.get(i)[27]<0?256+JPEGData.get(i)[27]:JPEGData.get(i)[27])<<8)+(JPEGData.get(i)[28]<0?256+JPEGData.get(i)[28]:JPEGData.get(i)[28]))+
            10L*(JPEGData.get(i)[29]<0?256+JPEGData.get(i)[29]:JPEGData.get(i)[29]);
    }
    public byte[] getJPEG() {
		return JPEGData.get(0);
    	
    }
    

    public void run() {
        int synchronizedMode;

        while (alive) {
            for (int i = 0; i < 2; i++) {
                while (timeStamps[i] < 0 || Math.abs(System.currentTimeMillis() - timeStamps[i]) > 5000000) {
                    fillData(i);
                }
            }

            synchronizedMode = gui.getSyncFromGui();

            if (synchronizedMode == GUI.SYNC_AUTO) {
            	if (Math.abs(timeStamps[0] - timeStamps[1]) > SYNC_DELAY) gui.changeSyncLabel(GUI.SYNC_ASYNC);
        		else gui.changeSyncLabel(GUI.SYNC_SYNC);
            }

            if (synchronizedMode != GUI.SYNC_ASYNC && timeStamps[0] > timeStamps[1]) {
                gui.refreshCameraImage(JPEGData.get(1), 2);
                gui.printDelay(System.currentTimeMillis() - timeStamps[1], 2);
                timeStamps[1] = -1;
            } else if (synchronizedMode != GUI.SYNC_ASYNC && timeStamps[0] < timeStamps[1]) {
                gui.refreshCameraImage(JPEGData.get(0), 1);
                gui.printDelay(System.currentTimeMillis() - timeStamps[0], 1);
                timeStamps[0] = -1;
            } else {
                gui.refreshCameraImage(JPEGData.get(0), 1);
                gui.refreshCameraImage(JPEGData.get(1), 2);
                gui.printDelay(System.currentTimeMillis() - timeStamps[0], 1);
                gui.printDelay(System.currentTimeMillis() - timeStamps[1], 2);
                timeStamps[0] = -1;
                timeStamps[1] = -1;
            }
        }
//        css1.alive = false;
//        css2.alive = false;
//        csr1.alive = false;
//        csr2.alive = false;
    }
}
