

import se.lth.cs.fakecamera.Axis211A;
import se.lth.cs.fakecamera.MotionDetector;

public class CameraServer extends Thread {
    private ServerMonitor serverMonitor;
    private Axis211A camera;
    private MotionDetector motionDetector;
    private byte[] JPEGData = new byte[Axis211A.IMAGE_BUFFER_SIZE];
    private static final boolean
        SET_MOVIE = true,
        UNSET_MOVIE = false;


    public CameraServer(ServerMonitor serverMonitor, Axis211A camera, MotionDetector motionDetector) {
        this.serverMonitor = serverMonitor;
        this.camera = camera;
        this.motionDetector = motionDetector;
    }

    public void run() {
        int length;
        boolean previousMode = UNSET_MOVIE;

        while (true) {
            length = camera.getJPEG(JPEGData, 0);
            serverMonitor.storeJPEGData(JPEGData, length);

            if (motionDetector.detect() && previousMode == UNSET_MOVIE) {
                serverMonitor.setMovie();
                previousMode = SET_MOVIE;
            } else if (!motionDetector.detect() && previousMode == SET_MOVIE) {
                serverMonitor.unsetMovie();
                previousMode = UNSET_MOVIE;
            }
        }
    }
}
