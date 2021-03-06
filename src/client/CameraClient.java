package client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class CameraClient extends Thread {
    private final GUI gui;
	private InputStream[] inputStreams;
    private final ArrayList<byte[]> JPEGData = new ArrayList<byte[]>(2){{
        add(new byte[0]);
        add(new byte[0]);
    }};
    private final byte[] JPEGDataSize = new byte[4];
    private final long[] timeStamps = new long[2];
    private final static long SYNC_DELAY = 200;
    private final HTTPMonitor httpMonitor;
    private final String serverAddress1, serverAddress2;
    private final int serverPicturePort1, serverPicturePort2;

	public CameraClient(GUI gui, ClientStateMonitor clientStateMonitor, HTTPMonitor httpMonitor, String serverAddress1, int serverPicturePort1, int serverReceivePort1, int serverSendPort1, String serverAddress2, int serverPicturePort2, int serverReceivePort2, int serverSendPort2) {
		this.gui = gui;
		this.httpMonitor = httpMonitor;
        this.serverAddress1 = serverAddress1;
        this.serverAddress2 = serverAddress2;
        this.serverPicturePort1 = serverPicturePort1;
        this.serverPicturePort2 = serverPicturePort2;

        (new ClientStateSender(serverReceivePort1, clientStateMonitor)).start();
        (new ClientStateSender(serverReceivePort2, clientStateMonitor)).start();
        (new ClientStateReceiver(serverAddress1, serverSendPort1, clientStateMonitor, gui)).start();
        (new ClientStateReceiver(serverAddress2, serverSendPort2, clientStateMonitor,gui)).start();
    }

    private void fillData(int i) throws IOException {
        int bytesLeft, bytesExpected, length;

        bytesExpected = bytesLeft = 4;
        while (bytesLeft > 0) {
            length = inputStreams[i].read(JPEGDataSize, bytesExpected - bytesLeft, bytesLeft);
            if (length == -1) {
                throw new IOException("Stream ended.");
            } else {
                bytesLeft -= length;
            }
        }

        bytesLeft = bytesExpected = ByteBuffer.wrap(JPEGDataSize).getInt();
        JPEGData.set(i, new byte[bytesExpected]);

        while (bytesLeft > 0) {
            length = inputStreams[i].read(JPEGData.get(i), bytesExpected - bytesLeft, bytesLeft);

            if (length == -1) {
                throw new IOException("Stream ended.");
            } else {
                bytesLeft -= length;
            }
        }

        timeStamps[i] =
            1000L*(((JPEGData.get(i)[25]<0?256+JPEGData.get(i)[25]:JPEGData.get(i)[25])<<24)+((JPEGData.get(i)[26]<0?256+JPEGData.get(i)[26]:JPEGData.get(i)[26])<<16)+
            ((JPEGData.get(i)[27]<0?256+JPEGData.get(i)[27]:JPEGData.get(i)[27])<<8)+(JPEGData.get(i)[28]<0?256+JPEGData.get(i)[28]:JPEGData.get(i)[28]))+
            10L*(JPEGData.get(i)[29]<0?256+JPEGData.get(i)[29]:JPEGData.get(i)[29]);
    }

    public void run() {
        int synchronizedMode;

        while (true) {
            try {
                inputStreams = new InputStream[]{
                    (new Socket(serverAddress1, serverPicturePort1)).getInputStream(),
                    (new Socket(serverAddress2, serverPicturePort2)).getInputStream()
                };

                while (true) {
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

                    httpMonitor.storeImage(JPEGData.get(0));
                }
            } catch (IOException e) {
                System.out.println("[CameraClient] No connection to server: " + serverAddress1 + " on port: " + serverPicturePort1 + " or " + serverAddress2 + " on port: " + serverPicturePort2 + ". Reconnecting in 1 second.");
                try { sleep(1000); } catch (InterruptedException e1) { e1.printStackTrace(); }
            }
        }
    }
}
