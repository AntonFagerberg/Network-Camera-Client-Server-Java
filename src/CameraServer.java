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
import java.net.ServerSocket;

public class CameraServer extends Thread {
    private ServerStateMonitor serverStateMonitor = new ServerStateMonitor();
    private MotionDetector motionDetector = new MotionDetector();
    private final static int WAIT_TIME = 5000;
    private Axis211A camera;
    private int port;

    public CameraServer(int picturePort, int stateSendPort, String stateReceiveAddress, int stateReceivePort) {
        System.out.println("[" + currentThread().getId() + "] CameraServer: started.");
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
        OutputStream outputStream = null;

        while (true) {
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                System.err.println("[" + currentThread().getId() + "] CameraServer: failed to create ServerSocket on port :" + port + ". Will retry in 5 seconds.");
                try { sleep(5000); } catch (InterruptedException e1) { e1.printStackTrace(); }
            }

            if (serverSocket != null) {
                try {
                    outputStream = serverSocket.accept().getOutputStream();
                } catch (IOException e) {
                    System.err.println("[" + currentThread().getId() + "] CameraServer: failed to get OutputStream.");
                }

                if (outputStream != null) {
                    try {
                        while (true) {
                            System.out.println("[" + currentThread().getId() + "] CameraServer: waiting for picture.");
                            currentMode = serverStateMonitor.getMode();
                            if (currentMode == ServerStateMonitor.IDLE || currentMode == ServerStateMonitor.IDLE_FORCED) {
                                System.out.println("[" + currentThread().getId() + "] CameraServer: idle, waiting.");
                                waitTime = System.currentTimeMillis() + WAIT_TIME;
                                while ((currentMode == ServerStateMonitor.IDLE_FORCED && waitTime > System.currentTimeMillis()) || (currentMode == ServerStateMonitor.IDLE && waitTime > System.currentTimeMillis() && !motionDetector.detect())) {
                                    try {
                                        sleep(100l);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    currentMode = serverStateMonitor.getMode();
                                }
                            }

                            System.out.println("[" + currentThread().getId() + "] CameraServer: calling camera.");
                            length = camera.getJPEG(JPEGdata, 0);
                            System.out.println("[" + currentThread().getId() + "] CameraServer: got picture with length: " + length);
                            System.out.println("[" + currentThread().getId() + "] CameraServer: sending picture length.");
                            outputStream.write(
                                    new byte[] {
                                            (byte) (length >>> 24),
                                            (byte) (length >>> 16),
                                            (byte) (length >>> 8),
                                            (byte) length
                                    }
                            );
                            System.out.println("[" + currentThread().getId() + "] CameraServer: sending picture data.");
                            outputStream.write(JPEGdata, 0, length);
                            System.out.println("[" + currentThread().getId() + "] CameraServer: picture sent.");

                            System.out.println("[" + currentThread().getId() + "] CameraServer: looking for motion: starting.");
                            if (previousMode == ServerStateMonitor.IDLE && currentMode == ServerStateMonitor.IDLE && motionDetector.detect()) {
                                serverStateMonitor.setMode(ServerStateMonitor.MOVIE);
                            }
                            System.out.println("[" + currentThread().getId() + "] CameraServer: looking for motion: done.");

                            previousMode = currentMode;
                        }
                    } catch (IOException e) {
                        System.err.println("[" + currentThread().getId() + "] CameraServer: outputStream aborted.");
                    }

                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        System.err.println("[" + currentThread().getId() + "] CameraServer: failed to close OutputStream.");
                    }

                    outputStream = null;
                }

                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.err.println("[" + currentThread().getId() + "] CameraServer: failed to close ServerSocket on port :" + port + ".");
                }
                serverSocket = null;
            }
        }
    }

    public static void main(String[] args) {
        (new CameraServer(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]))).start();
    }
}