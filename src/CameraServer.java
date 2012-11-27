/* REMOVE ON C-BUILD */
import se.lth.cs.fakecamera.Axis211A;
import se.lth.cs.fakecamera.MotionDetector;
/* END REMOVE ON C-BUILD */

/* ADD ON C-BUILD */
//import se.lth.cs.camera.Axis211A;
//import se.lth.cs.camera.MotionDetector;
/* END ADD ON C-BUILD */

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class CameraServer extends Thread {
    private ServerStateMonitor serverStateMonitor = new ServerStateMonitor();
    private MotionDetector motionDetector = new MotionDetector();
    private final static int WAIT_TIME = 5000;
    private Axis211A camera;
    private int port;

    public CameraServer(int picturePort, int stateSendPort, String stateReceiveAddress, int stateReceivePort) {
        this.port = picturePort;
        camera = new Axis211A();
        (new ServerStateSender(stateSendPort, serverStateMonitor)).start();
        (new ServerStateReceiver(stateReceiveAddress, stateReceivePort, serverStateMonitor)).start();
    }

    public CameraServer(int port, int stateSendPort, String stateReceiveAddress, int stateReceivePort, Axis211A camera) {
        this.camera = camera;
        this.port = port;
        (new ServerStateSender(stateSendPort, serverStateMonitor)).start();
        (new ServerStateReceiver(stateReceiveAddress, stateReceivePort, serverStateMonitor)).start();
    }

    public void run() {
        byte[] JPEGdata = new byte[Axis211A.IMAGE_BUFFER_SIZE];
        int length, currentMode, previousMode = serverStateMonitor.getMode();
        long waitTime;
        ServerSocket serverSocket = null;
        OutputStream outputStream;

        try {
            serverSocket = new ServerSocket();
            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress(port));
            System.out.println("[CameraServer] Started on port: " + port + ".");
        } catch (IOException e) {
            System.err.println("[CameraServer] Could not start ServerSocket on port: " + port + ".");
            System.exit(1);
        }

        while (true) {
            try {
                outputStream = serverSocket.accept().getOutputStream();

                while (true) {
                    currentMode = serverStateMonitor.getMode();
                    if (currentMode == ServerStateMonitor.IDLE || currentMode == ServerStateMonitor.IDLE_FORCED) {
                        waitTime = System.currentTimeMillis() + WAIT_TIME;
                        while ((currentMode == ServerStateMonitor.IDLE_FORCED && waitTime > System.currentTimeMillis()) || (currentMode == ServerStateMonitor.IDLE && waitTime > System.currentTimeMillis() && !motionDetector.detect())) {
                            try { sleep(100l); } catch (InterruptedException e) { e.printStackTrace(); }
                            currentMode = serverStateMonitor.getMode();
                        }
                    }

                    length = camera.getJPEG(JPEGdata, 0);
                    outputStream.write(
                        new byte[] {
                            (byte) (length >>> 24),
                            (byte) (length >>> 16),
                            (byte) (length >>> 8),
                            (byte) length
                        }
                    );

                    outputStream.write(JPEGdata, 0, length);

                    if (previousMode == ServerStateMonitor.IDLE && currentMode == ServerStateMonitor.IDLE && motionDetector.detect()) {
                        serverStateMonitor.setMode(ServerStateMonitor.MOVIE);
                    }

                    previousMode = currentMode;
                }
            } catch (IOException e) {
                System.out.println("[CameraServer] OutputStream closed. Reconnecting in 1 second.");
                try { sleep(1000); } catch (InterruptedException e1) { e1.printStackTrace(); }
            }
        }

    }

    public static void main(String[] args) {
        (new CameraServer(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]))).start();
    }
}