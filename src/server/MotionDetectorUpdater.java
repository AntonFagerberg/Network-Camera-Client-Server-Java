package server;

import se.lth.cs.fakecamera.MotionDetector;

public class MotionDetectorUpdater extends Thread {
    private MotionDetector motionDetector;
    private ServerMonitor serverMonitor;

    public MotionDetectorUpdater(ServerMonitor serverMonitor, MotionDetector motionDetector) {
        this.serverMonitor = serverMonitor;
        this.motionDetector = motionDetector;
    }

    public void run() {
        while (true) {
            System.out.println(motionDetector.detect() + " " + System.currentTimeMillis());
            if (motionDetector.detect()) {
                System.out.println("Calling setMovie");
                serverMonitor.setMovie();
                System.out.println("Call ended");
            } else  {
//                serverMonitor.setIdle2();
            }
        }
    }
}