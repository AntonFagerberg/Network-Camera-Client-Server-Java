package old;

import client.GUI;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class CameraReceiver extends Thread {
	private InputStream[] inputStreams;
    private GUI gui;
    private StateMonitor stateMonitor;
    private ArrayList<byte[]> JPEGData = new ArrayList<byte[]>(2);
    private byte[] JPEGDataSize = new byte[4];
    private long[] timeStamps = new long[2];
    private final static long SYNC_DELAY = 200;
    private int bytesLeft, bytesExpected, synchronizedMode = GUI.SYNC_AUTO;

	public CameraReceiver(String url1, int port1, String url2, int port2, StateMonitor stateMonitor, GUI gui) {
        this.stateMonitor = stateMonitor;
        this.gui = gui;

        try {
            inputStreams = new InputStream[]{
                (new Socket(url1, port1)).getInputStream(),
                (new Socket(url2, port2)).getInputStream()
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillData(int i) {
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

    public void run() {
        while (true) {
            for (int i = 0; i < 2; i++) {
                while (timeStamps[i] < 0 || Math.abs(System.currentTimeMillis() - timeStamps[i]) > 5000000) {
                    fillData(i);
                }
            }

            synchronizedMode = gui.getSyncFromGui();

            if (synchronizedMode == GUI.SYNC_AUTO) {
            	if (Math.abs(timeStamps[0] - timeStamps[1]) > SYNC_DELAY)
            		gui.changeSyncLabel(GUI.SYNC_ASYNC);
        		else
        			gui.changeSyncLabel(GUI.SYNC_SYNC);
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
    }
}
