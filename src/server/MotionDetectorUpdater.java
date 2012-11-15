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
        boolean motionDetect,
                wasIdle = true;
        while (true) {
            motionDetect = motionDetector.detect();

            if (motionDetect && wasIdle) {
//                System.out.println(motionDetect + " -> " + wasIdle);
                serverMonitor.setMovie();
                wasIdle = false;
            } else if (!motionDetect && !wasIdle) {
//                System.out.println(motionDetect + " -> " + wasIdle);
                serverMonitor.setIdle();
                wasIdle = true;
            }
        }
    }
}